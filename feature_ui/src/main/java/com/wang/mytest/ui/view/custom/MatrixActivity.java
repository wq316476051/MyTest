package com.wang.mytest.ui.view.custom;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wang.mytest.ui.R;

public class MatrixActivity extends AppCompatActivity {

    private MatrixView mMatrixView;
    private Button mBtnTranslate;
    private Button mBtnScale;
    private Button mBtnRotate;
    private Button mBtnReset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);

        mMatrixView = findViewById(R.id.matrix_view);

        mBtnTranslate = findViewById(R.id.btn_translate);
        mBtnScale = findViewById(R.id.btn_scale);
        mBtnRotate = findViewById(R.id.btn_rotate);
        mBtnReset = findViewById(R.id.btn_reset);

        mBtnTranslate.setOnClickListener(this::onTranslate);
        mBtnScale.setOnClickListener(this::onScale);
        mBtnRotate.setOnClickListener(this::onRotate);
        mBtnReset.setOnClickListener(this::onReset);
    }

    private void onTranslate(View view) {
        mMatrixView.setTransition(300, 300);
    }

    private void onScale(View view) {
        mMatrixView.setScale(1.5F, 1.5F);
    }

    private void onRotate(View view) {
    }


    private void onReset(View view) {
        mMatrixView.reset();
    }
}
