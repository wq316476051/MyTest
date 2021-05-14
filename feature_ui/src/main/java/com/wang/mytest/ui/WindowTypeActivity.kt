package com.wang.mytest.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.atomic.AtomicBoolean

/*
type = WindowManager.LayoutParams.TYPE_BASE_APPLICATION
type = WindowManager.LayoutParams.TYPE_APPLICATION
type = WindowManager.LayoutParams.TYPE_APPLICATION_STARTING
// type = WindowManager.LayoutParams.TYPE_DRAWN_APPLICATION
type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
type = WindowManager.LayoutParams.TYPE_APPLICATION_MEDIA
type = WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL
// type = WindowManager.LayoutParams.TYPE_APPLICATION_ABOVE_SUB_PANEL
type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG
// type = WindowManager.LayoutParams.TYPE_APPLICATION_MEDIA_OVERLAY
type = WindowManager.LayoutParams.TYPE_STATUS_BAR
type = WindowManager.LayoutParams.TYPE_SEARCH_BAR
 */
class WindowTypeActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "WindowTypeActivity"
    }

    private lateinit var btnAddView: Button
    private lateinit var btnRemoveView: Button
    private val isAdded = AtomicBoolean(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window_type)

        btnAddView = findViewById(R.id.btn_add_view)
        btnRemoveView = findViewById(R.id.btn_remove_view)

        val view = View(this).apply { background = ColorDrawable(Color.RED) }
        val layoutParams = WindowManager.LayoutParams().apply {
            type = WindowManager.LayoutParams.TYPE_BASE_APPLICATION
        }

        view.setOnTouchListener { v, event ->
            Log.d(TAG, "onTouchEvent: ${event.action}")
            val pointerIndex = event.findPointerIndex(0)
            if (pointerIndex < 0 || pointerIndex >= event.pointerCount) {
                return@setOnTouchListener super.onTouchEvent(event)
            }
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {

                }
                MotionEvent.ACTION_MOVE -> {

                }
                MotionEvent.ACTION_UP -> {

                }
            }
            true
        }

        btnAddView.setOnClickListener {
            if (isAdded.compareAndSet(false, true)) {
                windowManager.addView(view, layoutParams)
            }
        }

        btnRemoveView.setOnClickListener {
            if (isAdded.compareAndSet(true, false)) {
                windowManager.removeView(view)
            }
        }
    }
}