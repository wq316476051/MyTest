package com.wang.mytest.network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class NetworkLiveModel extends ViewModel {

    public LiveData<Boolean> getNetworkLiveData() {
        return new NetworkLiveData();
    }
}
