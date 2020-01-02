package com.wang.soundrecorder.service

import android.media.MediaRecorder
import android.util.Log
import com.wang.soundrecorder.config.RecordAttribute
import com.wang.soundrecorder.config.RecordState
import java.io.File

class Recorder(val uuid: String) {

    companion object {
        private const val TAG = "Recorder"
    }

    private val recorder: MediaRecorder by lazy { MediaRecorder() }

    var outputFile: File? = null

    var recordingState: Int = RecordState.IDLE
        private set
    val isIdle get() = recordingState == RecordState.IDLE
    val isRecording get() = recordingState == RecordState.RECORDING
    val isPaused get() = recordingState == RecordState.PAUSED

    private fun prepareRecorder(): Boolean {
        val result = outputFile?.runCatching {
            recorder.reset()
            recorder.setAudioSource(RecordAttribute.SOURCE)
            recorder.setOutputFormat(RecordAttribute.OUTPUT_FORMAT)
            recorder.setAudioChannels(RecordAttribute.AUDIO_CHANNEL)
            recorder.setAudioEncoder(RecordAttribute.ENCODER) // 必须在 setOutputFormat() 之后调用
            recorder.setAudioSamplingRate(RecordAttribute.SAMPLE_RATE)
            recorder.setOutputFile(canonicalPath)

            recorder.prepare()
        }
        return result != null && result.isSuccess
    }

    fun startRecordings(filePath: String) {
        Log.d(TAG, "startRecordings: filePath = $filePath");
        outputFile = File(filePath)
        if (prepareRecorder()) {
            recorder.start()
            Log.d(TAG, "startRecordings: started");
            recordingState = RecordState.RECORDING
        }
    }

    fun pauseResumeRecordings() {
        Log.d(TAG, "pauseResumeRecordings: ");
        when {
            isRecording -> {
                recorder.pause()
                Log.d(TAG, "startRecordings: paused");
                recordingState = RecordState.PAUSED
            }
            isPaused -> {
                recorder.resume()
                Log.d(TAG, "startRecordings: resumed");
                recordingState = RecordState.RECORDING
            }
        }
    }

    fun stopRecordings() {
        if (!isIdle) {
            recorder.stop()
            Log.d(TAG, "startRecordings: stopped");
            recorder.reset()
            recordingState = RecordState.IDLE
        }
    }

    fun getMaxAmplitude(): Int {
        return if (isRecording) {
            recorder.maxAmplitude
        } else {
            -1
        }
    }
}