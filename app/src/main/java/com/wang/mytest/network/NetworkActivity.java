package com.wang.mytest.network;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class NetworkActivity extends AppCompatActivity {

    private NetworkLiveModel mNetworkLiveModel;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNetworkLiveModel = ViewModelProviders.of(this).get(NetworkLiveModel.class);
        mNetworkLiveModel.getNetworkLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isConnected) {

            }
        });
    }
}
