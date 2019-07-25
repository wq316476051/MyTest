package com.wang.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wang.mytest.apt.api.RouteStore;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        layoutManager.setOrientation(RecyclerView.VERTICAL); //设置为垂直布局，这也是默认的
        recyclerView.setLayoutManager(layoutManager); //设置布局管理器
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false)); //设置布局管理器

//        recyclerView.addItemDecoration( new DividerGridItemDecoration(this )); //设置分隔线
        recyclerView.setItemAnimator( new DefaultItemAnimator()); //设置增加或删除条目的动画

        List<String> result = new ArrayList<>(RouteStore.getAll());
        recyclerView.setAdapter(new NormalAdapter(result)); //设置Adapter
    }

    // ① 创建Adapter
    public class NormalAdapter extends RecyclerView.Adapter<NormalAdapter.VH>{
        //② 创建ViewHolder
        public class VH extends RecyclerView.ViewHolder{
            public final TextView title;
            public VH(View v) {
                super(v);
                title = v.findViewById(R.id.title);
            }
        }

        private List<String> mDatas;
        public NormalAdapter(List<String> data) {
            this.mDatas = data;
        }

        //③ 在Adapter中实现3个方法
        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.title.setText(mDatas.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //item 点击事件
                    String className = RouteStore.get(mDatas.get(position));
                    Intent intent = new Intent();
                    intent.setClassName(MainActivity.this, className);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            //LayoutInflater.from指定写法
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_1, parent, false);
            return new VH(v);
        }
    }
}
