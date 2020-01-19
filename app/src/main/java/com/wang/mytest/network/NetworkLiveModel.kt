package com.wang.mytest.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class NetworkLiveModel : ViewModel() {

    fun getNetworkLiveData(): LiveData<Boolean> {
        return NetworkStateLiveData
    }
}