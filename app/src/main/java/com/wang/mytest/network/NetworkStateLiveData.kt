package com.wang.mytest.network

import android.net.*
import androidx.lifecycle.LiveData
import com.wang.mytest.library.common.AppUtils

object NetworkStateLiveData : LiveData<Boolean>() {

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onCapabilitiesChanged(network: Network?, networkCapabilities: NetworkCapabilities?) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            checkAndPost(hasValidatedCapabilities(networkCapabilities))
        }

        override fun onLost(network: Network?) {
            super.onLost(network)
            checkAndPost(isConnected())
        }
    }

    override fun onActive() {
        super.onActive()
        // 在子线程中回调
        getConnectivityManager()?.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        getConnectivityManager()?.unregisterNetworkCallback(networkCallback)
    }

    private fun getConnectivityManager(): ConnectivityManager? {
        return AppUtils.app?.getSystemService(ConnectivityManager::class.java)
    }

    private fun checkAndPost(isConnected: Boolean) {
        if (value == null || value != isConnected) {
            postValue(isConnected)
        }
    }

    fun isConnected(): Boolean {
        return getConnectivityManager()?.let {
            hasValidatedCapabilities(it.getNetworkCapabilities(it.activeNetwork))
        } ?: false
    }

    /**
     * 连接，且有网络数据
     */
    private fun hasValidatedCapabilities(capabilities: NetworkCapabilities?) : Boolean {
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) ?: false
    }
}