package com.wang.mytest.feature.ui.layout;

import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.wang.mytest.apt.annotation.Route;
import com.wang.mytest.feature.ui.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

@Route(path = "/activity/ui/layout/constraint2", title = "ConstraintLayout2")
public class ConstraintLayoutActivity2 extends AppCompatActivity {

    private ConstraintLayout mConstraintLayout;
    private ConstraintSet mConstraintSetLeft;
    private ConstraintSet mConstraintSetCenter;
    private ConstraintSet mConstraintSetRight;
    private FrameLayout mLeft;
    private FrameLayout mRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout2_left);

        mConstraintLayout = findViewById(R.id.constraint_layout);
        mLeft = findViewById(R.id.frameLayout);
        mRight = findViewById(R.id.frameLayout2);

        mConstraintSetLeft = new ConstraintSet();
        mConstraintSetCenter = new ConstraintSet();
        mConstraintSetRight = new ConstraintSet();
        mConstraintSetLeft.clone(this, R.layout.activity_constraint_layout2_left);
        mConstraintSetCenter.clone(this, R.layout.activity_constraint_layout2_center);
        mConstraintSetRight.clone(this, R.layout.activity_constraint_layout2_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, "left");
        menu.add(0, 2, 1, "center");
        menu.add(0, 3, 1, "right");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TransitionManager.beginDelayedTransition(mConstraintLayout, new ChangeBounds());
        switch (item.getItemId()) {
            case 1:
                mConstraintSetLeft.applyTo(mConstraintLayout);
                break;
            case 2:
                mConstraintSetCenter.applyTo(mConstraintLayout);
                break;
            case 3:
                mConstraintSetRight.applyTo(mConstraintLayout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
