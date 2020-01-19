package com.wang.mytest.network

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wang.mytest.apt.annotation.Route

@Route(path = "/activity/app/network", title = "network")
class NetworkActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "NetworkActivity"
    }

    private lateinit var liveModel: NetworkLiveModel

    private val networkObserver = Observer<Boolean> { isConnected ->
        Log.d(TAG, "isConnected = $isConnected")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        liveModel = ViewModelProviders.of(this).get(NetworkLiveModel::class.java)
        liveModel.getNetworkLiveData().observeForever(networkObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
        liveModel.getNetworkLiveData().removeObserver(networkObserver)
    }
}