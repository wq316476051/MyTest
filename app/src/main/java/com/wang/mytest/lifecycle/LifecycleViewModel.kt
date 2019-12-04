package com.wang.mytest.lifecycle

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.*

class LifecycleViewModel(val app: Application) : AndroidViewModel(app), DefaultLifecycleObserver {

    companion object {
        const val TAG = "LifecycleViewModel"
    }

    private val mServiceState: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    private var mService: ILifecycleService? = null

    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mService = ILifecycleService.Stub.asInterface(service)
            mServiceState.postValue(mService != null)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mServiceState.postValue(false)
            app.bindService(Intent(app, LifecycleService::class.java), this, Context.BIND_AUTO_CREATE)
        }
    }

    init {
        Log.d(TAG, "init: ");
        app.bindService(Intent(app, LifecycleService::class.java), mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onCreate(owner: LifecycleOwner) {
        Log.d(TAG, ": onCreate: ");
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.d(TAG, ": onDestroy: ");
    }

    public fun call() {
        mService?.call("do", null, null)
    }

    public fun getServiceState(): LiveData<Boolean> {
        return mServiceState;
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, ": onCleared: ");
        app.unbindService(mServiceConnection)
    }
}