package com.wang.mytest.test;

import android.app.Application;
import android.os.Looper;

import com.wang.mytest.feature.ui.layout.CardLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TestViewModel extends AndroidViewModel {

    private MutableLiveData<TestBean> mTestBeanLiveData = new MutableLiveData<>();

    private MutableLiveData<Boolean> mNavigation = new MutableLiveData<>();

    private MutableLiveData<Integer> mUiMode = new MutableLiveData<>();

    public TestViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    public LiveData<Integer> getUiMode() {
        return mUiMode;
    }

    public void setUiMode(int uiMode) {
        mUiMode.postValue(uiMode);
    }

    public void chooseItem(TestBean testBean) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            mTestBeanLiveData.setValue(testBean);
        } else {
            mTestBeanLiveData.postValue(testBean);
        }
    }

    public MutableLiveData<TestBean> getTestBeanLiveData() {
        return mTestBeanLiveData;
    }

    public MutableLiveData<Boolean> getNavigationLiveDate() {
        return mNavigation;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

    }
}
