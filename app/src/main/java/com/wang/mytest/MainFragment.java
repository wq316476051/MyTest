package com.wang.mytest;

import android.animation.AnimatorInflater;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.wang.mytest.common.acitivity.ActivityFilter;
import com.wang.mytest.common.util.ActivityUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private final AtomicBoolean mClickable = new AtomicBoolean(true);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FrameLayout rootView = new FrameLayout(requireContext()) {
            @Override
            public boolean onInterceptTouchEvent(MotionEvent ev) {
                if (!mClickable.get()) {
                    return true;
                } else {
                    return super.onInterceptTouchEvent(ev);
                }
            }
        };
        inflater.inflate(R.layout.fragment_main, rootView, true);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Context context = requireContext();
        Toolbar toolbar = view.findViewById(R.id.main_toolbar);
        ViewGroup container = view.findViewById(R.id.action_container);

        toolbar.setTitle("MyTest");
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(this::onMenuItemSelected);

        List<ActivityFilter.Entity> activities = ActivityFilter.getActivities();
        for (ActivityFilter.Entity entity : activities) {
            final TextView child = createChild(context);
            child.setText(entity.getSummary());
            child.setOnClickListener(v -> {
                ComponentName componentName = new ComponentName(entity.getPackageName(), entity.getClassName());
                Intent intent = new Intent().setComponent(componentName);
                ActivityUtils.startActivity(context, intent);
            });
            container.addView(child);
        }
    }

    @NonNull
    private TextView createChild(@NonNull Context context) {
        TextView view = new TextView(context);
        view.setTextSize(24);
        view.setGravity(Gravity.CENTER);
        view.setBackgroundColor(Color.parseColor("#88888888"));
        view.setPadding(24, 12, 24, 12);
        view.setStateListAnimator(AnimatorInflater.loadStateListAnimator(context, R.animator.state_list_animator));

        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 24;
        params.rightMargin = 12;
        params.topMargin = 24;
        params.bottomMargin = 12;
        view.setLayoutParams(params);
        return view;
    }

    private boolean onMenuItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options_clickable: {
                mClickable.compareAndSet(false, true);
                return true;
            }
            case R.id.options_unclickable: {
                mClickable.compareAndSet(true, false);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
