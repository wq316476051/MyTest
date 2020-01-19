package com.wang.soundrecorder.ui

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.wang.soundrecorder.R
import com.wang.soundrecorder.config.RecordState
import com.wang.soundrecorder.utils.Permissions
import com.wang.soundrecorder.utils.formatDate
import com.wang.soundrecorder.viewModel.RecordViewModel

class SoundRecorderActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SoundRecorderActivity"
    }

    private lateinit var tvWaveform: TextView
    private lateinit var tvDuration: TextView
    private lateinit var extraContainer: FrameLayout
    private lateinit var btnTag: AppCompatButton
    private lateinit var btnStartRecord: AppCompatButton
    private lateinit var btnStopRecord: AppCompatButton

    private lateinit var viewModel: RecordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sound_recorder)

        initViews();
        bindViews();
    }

    private fun initViews() {
        tvWaveform = findViewById(R.id.tv_waveform)
        tvDuration = findViewById(R.id.tv_duration)
        extraContainer = findViewById(R.id.extra_container)
        btnTag = findViewById(R.id.btn_tag)
        btnStartRecord = findViewById(R.id.btn_start_pause_record)
        btnStopRecord = findViewById(R.id.btn_stop_record)
    }

    private fun bindViews() {
        viewModel = ViewModelProviders.of(this).get(RecordViewModel::class.java)
        viewModel.getServiceConnectionLiveData.observe(this, Observer {
            Log.d(TAG, "observe: isServiceConnected = $it");
            updateUi(it, viewModel.getRecordState)
        })
        viewModel.getRecordStateLiveData.observe(this, Observer {
            Log.d(TAG, "observe: recordState = $it");
            updateUi(viewModel.isServiceConnected, it)
        })
        viewModel.getMaxAmplitudeLiveData.observe(this, Observer {
            tvWaveform.text = it.toString()
        })
        viewModel.getDurationLiveData.observe(this, Observer {
            tvDuration.text = formatDate(it)
        })

        btnTag.setOnClickListener {
            viewModel.tryAddTag()
        }

        btnStartRecord.setOnClickListener {
            if (viewModel.isRecording || viewModel.isPaused) {
                viewModel.pauseOrResume()
            } else {
                if (Permissions.requestRecordPermissions(this)) {
                    viewModel.start()
                }
            }
        }

        btnStopRecord.setOnClickListener {
            viewModel.stop()
        }
    }

    private fun updateUi(isServiceConnected: Boolean, recordState:Int) {
        if (!isServiceConnected) {
            btnTag.isEnabled = false
            btnStartRecord.isEnabled = false
            btnStopRecord.isEnabled = false
        } else {
            when (recordState) {
                RecordState.IDLE -> {
                    Log.d(TAG, "observe: recordState = IDLE");
                    btnTag.isEnabled = false
                    btnStartRecord.isEnabled = true
                    btnStopRecord.isEnabled = false
                }
                RecordState.RECORDING -> {
                    Log.d(TAG, "observe: recordState = RECORDING");
                    btnTag.isEnabled = true
                    btnStartRecord.isEnabled = true
                    btnStopRecord.isEnabled = true
                }
                RecordState.PAUSED -> {
                    Log.d(TAG, "observe: recordState = PAUSED");
                    btnTag.isEnabled = true
                    btnStartRecord.isEnabled = true
                    btnStopRecord.isEnabled = true
                }
            }
        }
    }
}