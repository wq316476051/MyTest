package com.wang.mytest.ui.animator;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wang.mytest.ui.R;

/**
 * 方式一：XML
 * 方式二：代码
 */
public class StateListAnimatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_list_animator);

        StateListAnimator stateListAnimator = AnimatorInflater.loadStateListAnimator(this, R.animator.state_list_animator);
        View view = new View(this);
        view.setStateListAnimator(stateListAnimator);
    }
}
