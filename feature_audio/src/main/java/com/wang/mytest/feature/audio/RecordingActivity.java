package com.wang.mytest.feature.audio;

import android.os.Bundle;
import android.view.View;

import com.wang.mytest.apt.annotation.Route;
import com.wang.mytest.feature.audio.list.RecordingsFragment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

@Route(path = "/activity/audio/recordings", title = "Recordings")
public class RecordingActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG = "record_list_fragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);
        setTitle("Recordings");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RecordingsFragment.Companion.newInstance(), FRAGMENT_TAG)
                    .commit();
        }
    }
}
