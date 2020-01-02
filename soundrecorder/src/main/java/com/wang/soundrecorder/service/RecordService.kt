package com.wang.soundrecorder.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.wang.soundrecorder.IRecordService
import com.wang.soundrecorder.RecordCallback
import com.wang.soundrecorder.config.RecordState
import com.wang.soundrecorder.utils.NotificationHelper
import java.util.UUID
import java.util.concurrent.CopyOnWriteArrayList

/**
 * 多路音频
 */
class RecordService : Service() {

    companion object {
        const val TAG = "RecordService"
    }

    private val binderService: BinderService by lazy { BinderService() }
    private val recorderList = CopyOnWriteArrayList<Recorder>()
    private val recorderStateCallbackList = CopyOnWriteArrayList<RecordCallback>()

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.prepareChannels()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binderService
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun startRecordInner(filePath: String): String {
        Log.d(TAG, "startRecordInner: filePath = $filePath");
        recorderList.filter { it.isRecording }.forEach { it.pauseResumeRecordings() }
        return Recorder(UUID.randomUUID().toString()).apply {
            startRecordings(filePath)
            NotificationHelper.notifyRecording()
            recorderList.add(this)
        }.uuid
    }

    private fun pauseResumeRecordInner(id: String) {
        Log.d(TAG, "pauseResumeRecordInner: id = $id");
        recorderList.filter { it.uuid == id }.getOrNull(0)
                ?.pauseResumeRecordings()
    }

    private fun stopRecordInner(id: String) {
        Log.d(TAG, "stopRecordInner: id = $id");
        recorderList.filter { it.uuid == id }.getOrNull(0)
                ?.stopRecordings()
    }

    private fun getRecordStateInner(id: String): Int {
        Log.d(TAG, "getRecordStateInner: id = $id");
        return recorderList.filter { it.uuid == id }.getOrNull(0)?.recordingState ?: RecordState.IDLE
    }

    private fun isRecordingInner(): Boolean {
        Log.d(TAG, "isRecordingInner: ");
        return recorderList.filter { it.isRecording }.getOrNull(0) != null
    }

    private fun isRecordingByIdInner(id: String): Boolean {
        Log.d(TAG, "isRecordingInner: ");
        return recorderList.filter { it.uuid == id && it.isRecording }.getOrNull(0) != null
    }

    private fun isPausedByIdInner(id: String): Boolean {
        Log.d(TAG, "isRecordingInner: ");
        return recorderList.filter { it.uuid == id && it.isPaused }.getOrNull(0) != null
    }

    private inner class BinderService : IRecordService.Stub() {
        override fun startRecord(filePath: String): String = startRecordInner(filePath)
        override fun pauseResumeRecord(id: String) = pauseResumeRecordInner(id)
        override fun stopRecord(id: String) = stopRecordInner(id)
        override fun getRecordState(id: String): Int = getRecordStateInner(id)
        override fun isRecording(): Boolean = isRecordingInner()
        override fun isRecordingById(id: String): Boolean =  isRecordingByIdInner(id)
        override fun isPausedById(id: String): Boolean  =  isPausedByIdInner(id)
        override fun addRecordCallback(callback: RecordCallback) {
            recorderList.forEach {
                callback.onRecordStateChanged(it.uuid, it.recordingState)
            }
            recorderStateCallbackList.add(callback)
        }
        override fun removeRecordCallback(callback: RecordCallback) {
            recorderStateCallbackList.remove(callback)
        }
    }
}