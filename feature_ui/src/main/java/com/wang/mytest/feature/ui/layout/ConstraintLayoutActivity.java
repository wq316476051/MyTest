package com.wang.mytest.feature.ui.layout;

import android.os.Build;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;

import com.wang.mytest.apt.annotation.Route;
import com.wang.mytest.feature.ui.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

@Route(path = "/activity/ui/layout/constraint", title = "ConstraintLayout")
public class ConstraintLayoutActivity extends AppCompatActivity {

    private ConstraintLayout mConstraintLayout;
    private ConstraintSet mConstraintSet;
    private ConstraintSet mTarget;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint_layout);

        mConstraintLayout = findViewById(R.id.constraint_layout);

        mConstraintSet = new ConstraintSet();
        mConstraintSet.clone(mConstraintLayout);

        mTarget = new ConstraintSet();
        mTarget.clone(this, R.layout.activity_constraint_layout_new);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 1, "old");
        menu.add(0, 2, 1, "new");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(mConstraintLayout, new ChangeBounds());
        }
        switch (item.getItemId()) {
            case 1:
                mConstraintSet.applyTo(mConstraintLayout);
                break;
            case 2:
                mTarget.applyTo(mConstraintLayout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
