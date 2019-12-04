package com.wang.mytest.big_screen;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.wang.mytest.apt.annotation.Route;

import androidx.appcompat.app.AppCompatActivity;

@Route(path = "/activity/bigscreen/main", title = "BigScreen")
public class BigScreenActivity extends AppCompatActivity {

    private static final String TAG = "BigScreenActivity";

    private FrameLayout mRecordingsContainer;
    private FrameLayout mPlaybackContainer;

    private RecordingsFragment mRecordingFragment;
    private PlaybackFragment mPlaybackFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_big_screen);
        Log.d(TAG, "onCreate: ");

        mRecordingsContainer = findViewById(R.id.recordings_container);
        mPlaybackContainer = findViewById(R.id.playback_container);
        if (savedInstanceState == null) {
            mRecordingFragment = new RecordingsFragment();
            mPlaybackFragment = new PlaybackFragment();

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recordings_container, mRecordingFragment)
                    .replace(R.id.playback_container, mPlaybackFragment)
                    .commit();
        }

        adjustFragments(getResources().getConfiguration());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "onConfigurationChanged: ");
        adjustFragments(newConfig);
    }

    private void adjustFragments(Configuration configuration) {
        Log.d(TAG, "adjustFragments: " + configuration.orientation);
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mRecordingsContainer.setVisibility(View.VISIBLE);
            mPlaybackContainer.setVisibility(View.VISIBLE);
        } else {
            mRecordingsContainer.setVisibility(mPlaybackFragment.isEmpty() ? View.VISIBLE : View.GONE);
            mPlaybackContainer.setVisibility(mPlaybackFragment.isEmpty() ? View.GONE : View.VISIBLE);
        }
    }
}
