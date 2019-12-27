package com.wang.mytest;

import android.app.ActivityManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;

import com.wang.mytest.apt.annotation.Route;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

@Route(path = "/activity/app/test", title = "test")
public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    private Button mBtnSend;
    private TestBinding mBinding;

    private State mState = new State();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_test);

        if (isInPictureInPictureMode()) {
            mState.pip = true;
            mBinding.setState(mState);
        }

        MutableLiveData<String> liveData = new MutableLiveData<>();
        liveData.observe(this, s -> {
            Log.d(TAG, "onCreate: s = " + s);
        });

        Transformations.map(liveData, input -> "prefix:" + input).observe(this, s -> {
            Log.d(TAG, "onCreate: s2 = " + s);
        });

        Transformations.switchMap(liveData, input -> new MutableLiveData<String>()).observe(this, o -> {
            Log.d(TAG, "onCreate: s3 = " + o);
        });

        Transformations.distinctUntilChanged(liveData).observe(this, s -> {
            Log.d(TAG, "onCreate: s4 = " + s);
        });

        mBtnSend = findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(view -> {
            liveData.setValue("Hello!");

            enterPictureInPictureMode();
        });

        ActivityManager activityManager = ContextCompat.getSystemService(this, ActivityManager.class);
        if (activityManager != null) {
            int memoryClass = activityManager.getMemoryClass();
            Log.d(TAG, "onCreate: memoryClass = " + memoryClass * 1024 * 1024);
            int largeMemoryClass = activityManager.getLargeMemoryClass();
            Log.d(TAG, "onCreate: largeMemoryClass = " + largeMemoryClass);

            long totalMemory = Runtime.getRuntime().totalMemory();
            Log.d(TAG, "onCreate: totalMemory = " + totalMemory);
        }
    }

    @Override
    public void onPictureInPictureModeChanged (boolean isInPictureInPictureMode, Configuration newConfig) {
        mState.pip = isInPictureInPictureMode;
        mBinding.setState(mState);
        if (isInPictureInPictureMode) {
            ViewGroup.LayoutParams layoutParams = mBinding.rootView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mBinding.rootView.setLayoutParams(layoutParams);
        } else {
            // Restore the full-screen UI.
            ViewGroup.LayoutParams layoutParams = mBinding.rootView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mBinding.rootView.setLayoutParams(layoutParams);
        }
    }
}
