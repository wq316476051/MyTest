package com.wang.mytest.ui.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

;
import com.wang.mytest.ui.R;
import com.wang.mytest.ui.view.views.DrawView;

import androidx.annotation.Nullable;

public class DrawActivity extends Activity {

    private static final String TAG = "DrawActivity";

    private static int[] SHAPES = {
            DrawView.SHAPE_FREE,
            DrawView.SHAPE_LINE,
            DrawView.SHAPE_RECT,
            DrawView.SHAPE_CIRCLE,
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        Spinner spinner = findViewById(R.id.spinner);
        Button btnList = findViewById(R.id.btn_list);
        Button btnReset = findViewById(R.id.btn_reset);
        Button btnSave = findViewById(R.id.btn_save);
        DrawView drawView = findViewById(R.id.draw_view);

        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new String[] {
                "Free",
                "Line",
                "Rect",
                "Circle",
        }));

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: ");
                drawView.setNextShape(SHAPES[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnList.setOnClickListener(button -> {
            startActivity(new Intent(this, DrawRecordActivity.class));
        });

        btnReset.setOnClickListener(button -> {
            drawView.reset();
        });

        btnSave.setOnClickListener(button -> {
            drawView.save();
        });
    }
}
