package com.wang.mytest.ui.layout;

import android.os.Bundle;

;
import com.wang.mytest.ui.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CoordinateLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinate_layout);

        findViewById(R.id.text_view).setOnClickListener(view -> {
            view.setLeft(view.getLeft() + 30);
            view.setRight(view.getRight() + 30);
        });
    }
}
