package com.wang.mytest.feature.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.wang.mytest.apt.annotation.Route;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

@Route(path = "/activity/sensor/orientation", title = "Orientation Sensor")
public class OrientationActivity extends AppCompatActivity {

    private static final String TAG = "OrientationActivity";

    private TextView mTvOrientation;
    private TextView mTvZ, mTvX, mTvY;

    private SensorManager mSensorManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_orientation);

        mTvOrientation = findViewById(R.id.tv_orientation);
        mTvZ = findViewById(R.id.tv_z);
        mTvX = findViewById(R.id.tv_x);
        mTvY = findViewById(R.id.tv_y);

        mSensorManager = ContextCompat.getSystemService(this, SensorManager.class);
        if (mSensorManager == null) {
            finish();
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSensorManager.registerListener(mSensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListener);
    }

    private SensorEventListener mSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float[] values = event.values;
            Log.d(TAG, "onSensorChanged: " + System.currentTimeMillis() + "; " + values[0] + "," + values[1] + "," + values[2]);

            mTvZ.setText(String.valueOf(values[0]));
            mTvX.setText(String.valueOf(values[1]));
            mTvY.setText(String.valueOf(values[2]));

            float z = values[0];
            if (z > 350 || z < 10) {
                mTvOrientation.setText("正北");
            } else if (z >= 10 && z <= 80) {
                mTvOrientation.setText("东北");
            } else if (z > 80 && z < 100) {
                mTvOrientation.setText("正东");
            } else if (z >= 100 && z <= 170) {
                mTvOrientation.setText("东南");
            } else if (z > 170 && z < 190) {
                mTvOrientation.setText("正南");
            } else if (z >= 190 && z <= 260) {
                mTvOrientation.setText("西南");
            } else if (z > 260 && z < 270) {
                mTvOrientation.setText("正西");
            } else if (z >= 270 && z <= 350) {
                mTvOrientation.setText("西北");
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.d(TAG, "onAccuracyChanged: " + accuracy);
        }
    };
}
