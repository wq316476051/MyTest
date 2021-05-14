package com.wang.mytest.test;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wang.mytest.R;
import com.wang.mytest.apt.annotation.Route;
import com.wang.mytest.ui.layout.CardLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

@Route(path = "/activity/app/dual_fragment", title = "dualFragment")
public class DualFragmentActivity extends AppCompatActivity {

    private static final String TAG = "DualFragmentActivity";

    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private CardLayout mCardLayout;
    private TestViewModel mViewModel;

    private View mLeftContainer;
    private View mRightContainer;

    private Fragment mLeftFragment;
    private Fragment mRightFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_dual_fragment);
        mCardLayout = findViewById(R.id.card_layout);
        mLeftContainer = findViewById(R.id.left_container);
        mRightContainer = findViewById(R.id.right_container);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mLeftFragment = fragmentManager.findFragmentByTag(LEFT);
        mRightFragment = fragmentManager.findFragmentByTag(RIGHT);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mLeftFragment == null) {
            mLeftFragment = new LeftFragment();
            transaction.replace(R.id.left_container, mLeftFragment, LEFT);
        }
        if (mRightFragment == null) {
            mRightFragment = new RightFragment();
            transaction.replace(R.id.right_container, mRightFragment, RIGHT);
        }
        transaction.commit();

        mViewModel = ViewModelProviders.of(this).get(TestViewModel.class);
        if (mViewModel.getUiMode().getValue() == null) {
            int initUiMode = getInitUiMode();
            mViewModel.setUiMode(initUiMode);
        }
        mViewModel.getTestBeanLiveData().observe(this, testBean -> {
            if (testBean == null) {
                return;
            }
            Log.d(TAG, "onCreate: getUiMode = " + mViewModel.getUiMode().getValue());
            if (mViewModel.getUiMode().getValue() == CardLayout.LEFT_ONLY) {
                mViewModel.setUiMode(CardLayout.RIGHT_ONLY);
            }
        });
        mViewModel.getUiMode().observe(this, uiMode -> {
            if (uiMode == null) {
                return;
            }
            mCardLayout.setDisplayMode(uiMode);

            float translationX = mRightContainer.getTranslationX();
            Log.d(TAG, "onCreate: translationX = " + translationX);
//            if (uiMode == CardLayout.LEFT_ONLY) {
//                mCardLayout.setRightAnimateStartListener(() -> {
//                    getSupportFragmentManager().beginTransaction()
//                            .show(mLeftFragment)
//                            .commitAllowingStateLoss();
//                    return Unit.INSTANCE;
//                });
//                mCardLayout.setRightAnimateEndListener(() -> {
//                    getSupportFragmentManager().beginTransaction()
//                            .hide(mRightFragment)
//                            .commitAllowingStateLoss();
//                    return Unit.INSTANCE;
//                });
//            } else if (uiMode == CardLayout.RIGHT_ONLY) {
//                mCardLayout.setRightAnimateStartListener(() -> {
//                    getSupportFragmentManager().beginTransaction()
//                            .show(mRightFragment)
//                            .commitAllowingStateLoss();
//                    return Unit.INSTANCE;
//                });
//
//                mCardLayout.setRightAnimateEndListener(() -> {
//                    getSupportFragmentManager().beginTransaction()
//                            .hide(mLeftFragment)
//                            .commitAllowingStateLoss();
//                    return Unit.INSTANCE;
//                });
//            } else if (uiMode == CardLayout.LEFT_RIGHT) {
//                getSupportFragmentManager().beginTransaction()
//                        .show(mLeftFragment)
//                        .show(mRightFragment)
//                        .commitAllowingStateLoss();
//            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Integer lastUiMode = mViewModel.getUiMode().getValue();
        boolean bigScreen = getResources().getConfiguration().screenWidthDp > 600
                && getResources().getConfiguration().screenHeightDp > 600;
        if (bigScreen && lastUiMode != CardLayout.LEFT_RIGHT) {
            mViewModel.setUiMode(CardLayout.LEFT_RIGHT);
        } else if (!bigScreen && lastUiMode == CardLayout.LEFT_RIGHT) {
            mViewModel.setUiMode(CardLayout.RIGHT_ONLY);
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: getUiMode = " + mViewModel.getUiMode().getValue());
        if (mViewModel.getUiMode().getValue() == CardLayout.RIGHT_ONLY) {
            mViewModel.setUiMode(CardLayout.LEFT_ONLY);
            return;
        }
        super.onBackPressed();
    }

    private int getInitUiMode() {
        return getResources().getConfiguration().screenWidthDp > 600
                ? CardLayout.LEFT_RIGHT
                : CardLayout.LEFT_ONLY;
    }
}
