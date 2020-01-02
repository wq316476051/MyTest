package com.wang.soundrecorder.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.system.Os.remove
import android.util.Log
import com.wang.soundrecorder.App
import com.wang.soundrecorder.IRecordService
import com.wang.soundrecorder.RecordCallback
import com.wang.soundrecorder.service.RecordService
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

object RecordController {

    private const val TAG = "RecordController"
    private const val SERVICE_NOT_CONNECTED = "service_not_connected"

    private val serviceIntent = Intent(App.instance, RecordService::class.java)

    private var recorderService: IRecordService? = null

    private val clientCount = AtomicInteger(0)

    private val connectionListeners = CopyOnWriteArrayList<(Boolean) -> Unit>()
    private val recordStateListeners = ConcurrentHashMap<String, (Int) -> Unit>()

    private val recordCallback: RecordCallback = object : RecordCallback.Stub() {
        override fun onRecordStateChanged(recordId: String?, recordState: Int) {

        }
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            recorderService = IRecordService.Stub.asInterface(service).apply {
                addRecordCallback(recordCallback)
            }
            connectionListeners.forEach {
                it.invoke(recorderService != null)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            recorderService = null
            bindService()
        }
    }

    fun bindService() {
        if (clientCount.getAndIncrement() == 0) {
            App.instance.bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    fun unbindService() {
        if (clientCount.decrementAndGet() == 0) {
            App.instance.unbindService(serviceConnection)
        }
    }

    fun startRecord(filePath: String): String = recorderService?.startRecord(filePath)
            ?: notifyServiceNotConnected("startRecord")

    fun pauseResumeRecord(id: String) = recorderService?.pauseResumeRecord(id)
            ?: notifyServiceNotConnected("pauseResumeRecord")

    fun stopRecord(id: String) = recorderService?.stopRecord(id)
            ?: notifyServiceNotConnected("stopRecord")

    private fun notifyServiceNotConnected(methodName: String): String {
        return SERVICE_NOT_CONNECTED.apply {
            Log.e(TAG, "$methodName: $this")
        }
    }

    fun isIdle(recordId: String): Boolean {
        return !isRecording(recordId) && !isPaused(recordId)
    }

    fun isRecording(recordId: String): Boolean {
        return recorderService?.isRecordingById(recordId) ?: false
    }

    fun isPaused(recordId: String): Boolean {
        return recorderService?.isRecordingById(recordId) ?: false
    }

    fun addOnServiceConnectionListener(listener: (Boolean) -> Unit) {
        listener.invoke(recorderService != null)
        connectionListeners.add(listener)
    }

    fun removeOnServiceConnectionListener(listener: (Boolean) -> Unit) {
        connectionListeners.remove(listener)
    }

    fun addOnRecordStateChangedListener(id: String, recordStateListener: (Int) -> Unit) {
        recorderService?.apply {
            recordStateListener.invoke(getRecordState(id))
        }
        recordStateListeners[id] = recordStateListener
    }

    fun removeOnRecordStateChangedListener(recordStateListener: (Int) -> Unit) {
        val iterator = recordStateListeners.iterator()
        while (iterator.hasNext()) {
            if (iterator.next().value == recordStateListener) {
                iterator.remove()
            }
        }
    }
}