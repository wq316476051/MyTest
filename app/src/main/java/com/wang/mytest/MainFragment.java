package com.wang.mytest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.mytest.apt.annotation.RouteBean;
import com.wang.mytest.ui.ScreenActivity;
import com.wang.mytest.ui.layout.ViewPager2Activity;
import com.wang.mytest.common.acitivity.ActivityFilter;
import com.wang.mytest.common.acitivity.TestBean;
import com.wang.mytest.lifecycle.LifecycleActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private RecyclerView mPathIndicator;
    private RecyclerView mRecyclerView;
    private Button mBtnTest;

    private AtomicBoolean mClickable = new AtomicBoolean(true);

    private LinkedList<String> mSegments = new LinkedList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mSegments.clear();
        mSegments.add("activity");
        ActivityFilter.init(context.getPackageManager());
    }

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
        mBtnTest = view.findViewById(R.id.btn_test);
        mPathIndicator = view.findViewById(R.id.path_indicator);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        List<TestBean> beanList = ActivityFilter.getActivities(mSegments);
        mRecyclerView.setAdapter(new NormalAdapter2(beanList));

//        mRecyclerView.setAdapter(new NormalAdapter(RouteStore.getAll()));

        mBtnTest.setOnClickListener(v -> {
            startActivity(new Intent().setClass(context, LifecycleActivity.class));
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.clickable, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options_clickable: {
                mClickable.compareAndSet(false, true);
                return true;
            }
            case R.id.options_unclickable: {
                mClickable.compareAndSet(true, false);
                return true;
            }
            case R.id.options_screen_details: {
                startActivity(new Intent(getContext(), ScreenActivity.class));
                return true;
            }
            case R.id.options_viewpager2: {
                startActivity(new Intent(getContext(), ViewPager2Activity.class));
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.ViewHolder> {
        List<RouteBean> mDataList;

        private NormalAdapter(List<RouteBean> dataList) {
            mDataList = dataList;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvTitle;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tv_title);
            }
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final RouteBean routeBean = mDataList.get(position);
            holder.tvTitle.setText(routeBean.title);
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent().setClassName(getContext(), routeBean.className);
                startActivity(intent);
            });
        }
    }

    private class NormalAdapter2 extends RecyclerView.Adapter<NormalAdapter2.ViewHolder> {
        List<TestBean> mDataList;

        private NormalAdapter2(List<TestBean> dataList) {
            mDataList = dataList;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvTitle;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tv_title);
            }
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final TestBean routeBean = mDataList.get(position);
            holder.tvTitle.setText(routeBean.getSummary());
            holder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent().setComponent(routeBean.getComponentName());
                startActivity(intent);
            });
        }
    }
}
