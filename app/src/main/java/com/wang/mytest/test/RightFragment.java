package com.wang.mytest.test;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wang.mytest.R;
import com.wang.mytest.common.util.ToastUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class RightFragment extends Fragment {

    private static final String TAG = "RightFragment";

    private Toolbar mToolbar;
    private TextView mTvContent;

    private TestViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_right, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar = view.findViewById(R.id.toolbar);
        mTvContent = view.findViewById(R.id.tv_content);

        mViewModel = ViewModelProviders.of(getActivity()).get(TestViewModel.class);
        mViewModel.getTestBeanLiveData().observeForever(this::onItemChanged);

        mToolbar.setTitle("Right");
    }

    private void onItemChanged(TestBean testBean) {
        mToolbar.setTitle(testBean.title);
        mTvContent.setText(testBean.content);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mToolbar.setNavigationIcon(android.R.drawable.arrow_up_float);
            mToolbar.setNavigationOnClickListener(v -> {
                ToastUtils.INSTANCE.showShort("navigation");
                mViewModel.getNavigationLiveDate().setValue(true);
            });
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mToolbar.setNavigationIcon(null);
        }
    }
}
