package com.wang.mytest.feature.ui.layout;

import android.os.Bundle;
import android.widget.Button;

import com.wang.mytest.apt.annotation.Route;
import com.wang.mytest.feature.ui.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@Route(path = "/activity/ui/layout/card", title = "CardLayout")
public class CardLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_layout);

        Button mode1 = findViewById(R.id.mode1);
        Button mode2 = findViewById(R.id.mode2);
        Button mode3 = findViewById(R.id.mode3);

        CardLayout cardLayout = findViewById(R.id.card_layout);

        mode1.setOnClickListener(view -> {
            cardLayout.setDisplayMode(CardLayout.LEFT_ONLY);
        });

        mode2.setOnClickListener(view -> {
            cardLayout.setDisplayMode(CardLayout.RIGHT_ONLY);
        });

        mode3.setOnClickListener(view -> {
            cardLayout.setDisplayMode(CardLayout.LEFT_RIGHT);
        });
    }
}
