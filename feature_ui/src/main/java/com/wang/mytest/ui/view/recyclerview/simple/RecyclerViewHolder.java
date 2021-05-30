package com.wang.mytest.ui.view.recyclerview.simple;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.mytest.ui.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public final TextView tvTitle;

    public final View btnDelete;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.tv_title);
        btnDelete = itemView.findViewById(R.id.iv_delete);
    }
}
