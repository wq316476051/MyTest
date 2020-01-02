package com.wang.soundrecorder.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.wang.soundrecorder.bean.Recording
import com.wang.soundrecorder.config.RecordState
import com.wang.soundrecorder.utils.RecordController
import com.wang.soundrecorder.utils.Storage
import com.wang.soundrecorder.utils.formatDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

class RecordViewModel : ViewModel() {

    companion object {
        private const val TAG = "RecordViewModel"
    }

    private val serviceConnection = MutableLiveData<Boolean>()
    val getServiceConnectionLiveData = Transformations.distinctUntilChanged(serviceConnection)
    val isServiceConnected = serviceConnection.value == true
    private val serviceConnectionListener: (Boolean) -> Unit = {
        serviceConnection.postValue(it)
    }

    private var recording: Recording? = null
    private var recordId: String? = null

    private val recordState = MutableLiveData<Int>(RecordState.IDLE)
    val getRecordStateLiveData = Transformations.distinctUntilChanged(recordState)
    val getRecordState = recordState.value ?: RecordState.IDLE
    val isIdle: Boolean get() = recordId?.let { RecordController.isIdle(it) } ?: false
    val isRecording: Boolean get() = recordId?.let { RecordController.isRecording(it) } ?: false
    val isPaused: Boolean get() = recordId?.let { RecordController.isPaused(it) } ?: false

    private val recordLock = Any()
    private var recordDurationBase: Long = 0
    private var recordDurationPause: Long = 0
    private var recordDurationLiveData = MutableLiveData<Long>()
    private var recordDurationJob: Job? = null
    private val recordDuration: Long get() = if (isRecording) {
        (System.currentTimeMillis() - recordDurationBase)
    } else if (isPaused) {
        (System.currentTimeMillis() - recordDurationBase + (recordDurationPause - recordDurationBase))
    } else 0L

    private val recordStateListener: (Int) -> Unit = {
        recordState.postValue(it)
        synchronized(recordLock) {
            when (it) {
                RecordState.RECORDING -> {
                    if (recordDurationBase == 0L) {
                        recordDurationBase = System.currentTimeMillis()
                    } else if (recordDurationPause != 0L) {
                        val pausedTime = (System.currentTimeMillis() - recordDurationPause)
                        recordDurationBase += pausedTime
                        recordDurationPause = 0L
                    }
                    recordDurationJob = viewModelScope.launch(Dispatchers.Default) {
                        repeat(Int.MAX_VALUE) {
                            recordDurationLiveData.postValue(recordDuration)
                            delay(1000)
                        }
                    }
                }
                RecordState.PAUSED -> {
                    recordDurationPause = System.currentTimeMillis()

                    recordDurationJob?.cancel()
                    recordDurationLiveData.postValue(recordDuration)
                }
                RecordState.IDLE -> {
                    if (recordDurationPause != 0L) {
                        val pausedTime = (System.currentTimeMillis() - recordDurationPause)
                        recordDurationBase += pausedTime
                        recordDurationPause = 0L
                    }
                    recordDurationBase = 0
                    recordDurationPause = 0
                }
            }
        }
    }

    val getMaxAmplitudeLiveData = MutableLiveData<Int>()
    val getDurationLiveData = MutableLiveData<Long>()

    init {
        RecordController.addOnServiceConnectionListener(serviceConnectionListener)
        RecordController.bindService()
    }

    override fun onCleared() {
        super.onCleared()
        RecordController.removeOnRecordStateChangedListener(recordStateListener)
        RecordController.removeOnServiceConnectionListener(serviceConnectionListener)
        RecordController.unbindService()
    }

    fun tryAddTag() {
        viewModelScope.launch {

        }
    }

    fun start() {
        recording = Recording(File(Storage.getRecordingsDir(), formatDate(System.currentTimeMillis()) + ".m4a")).apply {
            recordId = RecordController.startRecord(this.filePath).also {
                RecordController.addOnRecordStateChangedListener(it, recordStateListener)
            }
            Log.d(TAG, "start: recordId = $recordId");
        }
    }

    fun pauseOrResume() {
        recordId?.let {
            RecordController.pauseResumeRecord(it)
        }
    }

    fun stop() {
        recordId?.let {
            RecordController.stopRecord(it)
            recordStateListener.invoke(RecordState.IDLE)
            RecordController.addOnRecordStateChangedListener(it, recordStateListener)
            recordId = null
        }
    }
}