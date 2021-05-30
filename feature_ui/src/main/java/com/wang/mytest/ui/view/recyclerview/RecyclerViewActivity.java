package com.wang.mytest.ui.view.recyclerview;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.wang.mytest.ui.R;
import com.wang.mytest.ui.view.recyclerview.icon.RecyclerViewIconFragment;
import com.wang.mytest.ui.view.recyclerview.simple.RecyclerViewSimpleFragment;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        Toolbar toolbar = findViewById(R.id.recycler_view_toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recycler_view_container, new RecyclerViewSimpleFragment())
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycler_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == R.id.options_simple) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.recycler_view_container, new RecyclerViewSimpleFragment(), "simple")
//                    .commitAllowingStateLoss();

            while (getSupportFragmentManager().popBackStackImmediate());
        } else if (itemId == R.id.options_icon) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recycler_view_container, new RecyclerViewIconFragment(), "icon")
                    .addToBackStack("icon")
                    .commitAllowingStateLoss();
        }
        return super.onOptionsItemSelected(item);
    }
}
