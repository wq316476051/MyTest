package com.wang.mytest.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wang.mytest.R;
import com.wang.mytest.common.util.ToastUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.IntDef;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LeftFragment extends Fragment {

    private static final String TAG = "LeftFragment";

    private static final int MENU_POLICY_NON = 0;
    private static final int MENU_POLICY_CONTAINS = 1;
    private static final int MENU_POLICY_SELECTION = 2;

    @IntDef({MENU_POLICY_NON, MENU_POLICY_CONTAINS, MENU_POLICY_SELECTION})
    @Retention(RetentionPolicy.SOURCE)
    private @interface MenuPolicy {
    }

    private Toolbar mToolbar;

    private RecyclerView mRecyclerView;
    private LeftAdapter mLeftAdapter;
    private TestViewModel mViewModel;
    private MenuHolder mMenuHolder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(TestViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_left, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar = view.findViewById(R.id.toolbar);
        mRecyclerView = view.findViewById(R.id.recycler_view);

        mToolbar.setTitle("Left");
        mToolbar.inflateMenu(R.menu.left);
        mToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.look:
                    ToastUtils.INSTANCE.showShort("Look for you");
                    return true;
            }
            return true;
        });
        updateMenu(MENU_POLICY_NON);

        mLeftAdapter = new LeftAdapter();
        mLeftAdapter.setDate(new ArrayList<TestBean>() {{
            for (int i = 0; i < 50; i++) {
                add(new TestBean("title-" + i, "content-" + i));
            }
        }});

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mLeftAdapter);
        mLeftAdapter.setOnItemClickedListener((position, testBean) -> {
            mViewModel.chooseItem(testBean);
        });
    }

    @MainThread
    private void updateMenu(@MenuPolicy int policy) {
        if (mMenuHolder == null) {
            mMenuHolder = new MenuHolder(mToolbar.getMenu());
        }
        switch (policy) {
            case MENU_POLICY_NON:
                mMenuHolder.getLookMenu().setVisible(true);
                break;
            case MENU_POLICY_CONTAINS:
                break;
            case MENU_POLICY_SELECTION:
                break;
        }
    }

    private class LeftViewHolder extends RecyclerView.ViewHolder {

        private LeftViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    interface OnItemClickedListener<T> {
        void onItemClick(int position, T t);
    }

    private class LeftAdapter extends RecyclerView.Adapter<LeftViewHolder> {

        private List<TestBean> mDataList;

        private OnItemClickedListener<TestBean> mItemClickedListener;

        private void setDate(List<TestBean> dataList) {
            mDataList = dataList;
        }

        private void setOnItemClickedListener(OnItemClickedListener<TestBean> listener) {
            mItemClickedListener = listener;
        }

        @Override
        public int getItemCount() {
            return mDataList != null ? mDataList.size() : 0;
        }

        @NonNull
        @Override
        public LeftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setTextSize(45);
            return new LeftViewHolder(textView);
        }

        @Override
        public void onBindViewHolder(@NonNull LeftViewHolder holder, int position) {
            ((TextView)holder.itemView).setText(mDataList.get(position).title);
            holder.itemView.setOnClickListener(view -> {
                if (mItemClickedListener != null) {
                    mItemClickedListener.onItemClick(position, mDataList.get(position));
                }
            });
        }
    }

    private class MenuHolder {
        Menu menu;
        MenuItem lookMenu;
        private MenuHolder(Menu menu) {
            this.menu = menu;
        }

        private MenuItem getLookMenu() {
            return lookMenu != null ? lookMenu : (lookMenu = menu.findItem(R.id.look));
        }
    }
}
