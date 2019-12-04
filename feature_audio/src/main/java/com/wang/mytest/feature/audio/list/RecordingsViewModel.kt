package com.wang.mytest.feature.audio.list

import android.app.Application
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.wang.mytest.feature.audio.RecordStorage
import com.wang.mytest.feature.audio.bean.Recording
import java.util.concurrent.CompletableFuture

class RecordingsViewModel(val app: Application) : AndroidViewModel(app) {

    val mRecordings = MutableLiveData<List<Recording>>()

    fun loadRecordings(isAsync: Boolean) {
        if (isAsync) {
            CompletableFuture.runAsync {
                loadRecordings();
            }
        } else {
            loadRecordings()
        }
    }

    private fun loadRecordings() {
        val files = RecordStorage.getStorageDirectory(app).listFiles()
        val recordings = mutableListOf<Recording>()
        files?.let { it ->
            for (file in it) {
                Recording(file.name, file.absolutePath).takeIf { it.duration > 0 }?.let {
                    recordings.add(it)
                }
            }
        }

        if (isMainThread()) {
            mRecordings.value = recordings
        } else {
            mRecordings.postValue(recordings)
        }
    }
    private fun isMainThread() = Looper.getMainLooper() == Looper.myLooper()
}