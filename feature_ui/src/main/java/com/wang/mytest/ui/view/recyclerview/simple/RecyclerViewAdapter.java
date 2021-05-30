package com.wang.mytest.ui.view.recyclerview.simple;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.mytest.common.util.LogUtils;
import com.wang.mytest.ui.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private final RecyclerView mRecyclerView;

    private final List<String> mDataList = new ArrayList<>();

    public RecyclerViewAdapter(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    public void setDataList(List<String> dataList) {
        mDataList.clear();
        mDataList.addAll(dataList);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycler_view, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        final String item = mDataList.get(position);
        LogUtils.debug(TAG, "onBindViewHolder: position = " + position + "; item = " + item);
        holder.tvTitle.setText(item);
        holder.btnDelete.setOnClickListener(view -> {
            int adapterPosition = mRecyclerView.getChildAdapterPosition(holder.itemView);
            mDataList.remove(adapterPosition);
            notifyItemRemoved(adapterPosition);
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
