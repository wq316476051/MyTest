package com.wang.mytest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.wang.mytest.common.util.ActivityUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class CommonTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_test);

        AppCompatButton button = findViewById(R.id.btn_common_test);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"))
                    .addCategory(Intent.CATEGORY_BROWSABLE);
            ActivityUtils.startActivity(this, intent);
        });
    }
}
