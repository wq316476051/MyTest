package com.wang.mytest.test;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.wang.mytest.R;
import com.wang.mytest.apt.annotation.Route;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.DialogTitle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

@Route(path = "/activity/app/dual_fragment", title = "dualFragment")
public class DualFragmentActivity extends AppCompatActivity {

    private static final String TAG = "DualFragmentActivity";

    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private View mLeftContainer;
    private View mRightContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_dual_fragment);
        mLeftContainer = findViewById(R.id.left_container);
        mRightContainer = findViewById(R.id.right_container);

        int densityDpi = getResources().getConfiguration().densityDpi;
        int screenHeightDp = getResources().getConfiguration().screenHeightDp;
        int screenWidthDp = getResources().getConfiguration().screenWidthDp;
        Log.d(TAG, "onCreate: densityDpi = " + densityDpi);
        Log.d(TAG, "onCreate: screenWidthDp = " + screenWidthDp);
        Log.d(TAG, "onCreate: screenHeightDp = " + screenHeightDp);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment leftFragment = fragmentManager.findFragmentByTag(LEFT);
        Fragment rightFragment = fragmentManager.findFragmentByTag(RIGHT);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (leftFragment == null) {
            transaction.replace(R.id.left_container, new LeftFragment(), LEFT);
        }
        if (rightFragment == null && mRightContainer != null) {
            transaction.replace(R.id.right_container, new RightFragment(), RIGHT);
        }
        transaction.commit();

//        updateOrientation(getResources().getConfiguration());

//        TestViewModel viewModel = ViewModelProviders.of(this).get(TestViewModel.class);
//        viewModel.getTestBeanLiveData().observeForever(this::onItemChanged);
//        viewModel.getNavigationLiveDate().observeForever(aBoolean -> {
//            if (aBoolean) {
//                mLeftContainer.getLayoutParams().width = getResources().getDisplayMetrics().widthPixels;
//                mLeftContainer.setLayoutParams(mLeftContainer.getLayoutParams());
//
//                if (mRightContainer != null) {
//                    mRightContainer.getLayoutParams().width = 0;
//                    mRightContainer.setLayoutParams(mRightContainer.getLayoutParams());
//                }
//            }
//        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: " + newConfig);
        int width = mLeftContainer.getWidth();
        Log.d(TAG, "onConfigurationChanged: width = " + width);
//        updateOrientation(newConfig);
        mLeftContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = mLeftContainer.getWidth();
                Log.d(TAG, "onGlobalLayout: width = " + width);
                mLeftContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void updateOrientation(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLeftContainer.getLayoutParams().width = getResources().getDisplayMetrics().widthPixels;
            mLeftContainer.setLayoutParams(mLeftContainer.getLayoutParams());

            if (mRightContainer != null) {
                mRightContainer.getLayoutParams().width = 0;
                mRightContainer.setLayoutParams(mRightContainer.getLayoutParams());
            }
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLeftContainer.getLayoutParams().width = getResources().getDisplayMetrics().widthPixels / 3;
            mLeftContainer.setLayoutParams(mLeftContainer.getLayoutParams());

            if (mRightContainer != null) {
                mRightContainer.getLayoutParams().width = getResources().getDisplayMetrics().widthPixels * 2 / 3;
                mRightContainer.setLayoutParams(mRightContainer.getLayoutParams());
            }
        } else {
            Log.d(TAG, "updateOrientation: do nothing.");
        }
    }

    private void onItemChanged(TestBean testBean) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLeftContainer.getLayoutParams().width = 0;
            mLeftContainer.setLayoutParams(mLeftContainer.getLayoutParams());

            if (mRightContainer != null) {
                mRightContainer.getLayoutParams().width = getResources().getDisplayMetrics().widthPixels;
                mRightContainer.setLayoutParams(mRightContainer.getLayoutParams());
            }
        }
    }
}
