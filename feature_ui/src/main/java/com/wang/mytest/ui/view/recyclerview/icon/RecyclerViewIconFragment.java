package com.wang.mytest.ui.view.recyclerview.icon;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wang.mytest.ui.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewIconFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view_icon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Context context = requireContext();
        RecyclerView recyclerView = view.findViewById(R.id.icon_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        List<Integer> dataList = new ArrayList<>();
        for (int index = 0; index < 10; index++) {
            dataList.add(index);
        }

        IconAdapter adapter = new IconAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }

    private class IconViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        public IconViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView;
        }
    }

    private class IconAdapter extends RecyclerView.Adapter<IconViewHolder> {

        private final List<Integer> mDataList = new ArrayList<>();

        public IconAdapter(List<Integer> dataList) {
            mDataList.clear();
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public IconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item_icon_recycler_view, parent, false);
            return new IconViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull IconViewHolder holder, int position) {
            Integer integer = mDataList.get(position);
            holder.tvTitle.setText(integer.toString());
            holder.tvTitle.setClickable(true);
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }
    }
}
