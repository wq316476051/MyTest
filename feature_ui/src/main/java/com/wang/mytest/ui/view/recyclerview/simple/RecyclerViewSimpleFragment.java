package com.wang.mytest.ui.view.recyclerview.simple;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.mytest.ui.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RecyclerViewSimpleFragment extends Fragment {

    private RecyclerViewAdapter mAdapter;

    private List<String> mDataList;

    private final AtomicInteger mCount = new AtomicInteger();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Context context = requireContext();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        Button btnAddItem = view.findViewById(R.id.btn_add_item);
        Button btnRemoveItem = view.findViewById(R.id.btn_remove_item);
        Button btnReset = view.findViewById(R.id.btn_reset);

        mDataList = createDataList();
        mCount.set(mDataList.size());

        mAdapter = new RecyclerViewAdapter(recyclerView);
        mAdapter.setDataList(mDataList);
        recyclerView.setAdapter(mAdapter);

        btnAddItem.setOnClickListener(this::onAddItemClicked);
        btnRemoveItem.setOnClickListener(this::onRemoveItemClicked);
        btnReset.setOnClickListener(this::onResetClicked);
    }


    private void onAddItemClicked(@NonNull View view) {
        mDataList.add(0, "Item-" + mCount.getAndIncrement());
        mAdapter.setDataList(mDataList);
        mAdapter.notifyItemInserted(0);
    }

    private void onRemoveItemClicked(@NonNull View view) {
        if (mCount.get() > 0) {
            mDataList.remove(0);
            mAdapter.setDataList(mDataList);
            mAdapter.notifyItemRemoved(0);
            mCount.decrementAndGet();
        }
    }

    private void onResetClicked(@NonNull View view) {
        mDataList = createDataList();
        mAdapter.setDataList(mDataList);
        mAdapter.notifyDataSetChanged();
    }

    @NonNull
    private List<String> createDataList() {
        List<String> dataList = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            dataList.add("Item-" + index);
        }
        return dataList;
    }
}
