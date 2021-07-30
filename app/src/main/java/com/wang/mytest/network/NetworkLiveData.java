package com.wang.mytest.network;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import com.wang.mytest.common.util.AppUtils;

import androidx.lifecycle.LiveData;

public class NetworkLiveData extends LiveData<Boolean> {

    private final ConnectivityManager mConnectivityManager;

    private final ConnectivityManager.NetworkCallback mCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            checkAndPost(hasValidatedCapabilities(networkCapabilities));
        }

        @Override
        public void onLost(Network network) {
            super.onLost(network);
            checkAndPost(isConnected());
        }
    };

    public NetworkLiveData() {
        mConnectivityManager = AppUtils.getConnectivityManager();
    }

    @Override
    protected void onActive() {
        super.onActive();
        NetworkRequest request = new NetworkRequest.Builder().build();
        mConnectivityManager.registerNetworkCallback(request, mCallback);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        mConnectivityManager.unregisterNetworkCallback(mCallback);
    }

    private void checkAndPost(boolean isConnected) {
        Boolean lastValue = getValue();
        if (lastValue == null || lastValue != isConnected) {
            setValue(isConnected);
        }
    }

    private boolean isConnected() {
        NetworkCapabilities capabilities = mConnectivityManager.getNetworkCapabilities(mConnectivityManager.getActiveNetwork());
        return hasValidatedCapabilities(capabilities);
    }

    private boolean hasValidatedCapabilities(NetworkCapabilities capabilities) {
        if (capabilities == null) {
            return false;
        }
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
    }
}
