package com.wang.mytest.ui.view.surfaceview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.text.SimpleDateFormat

class MySurfaceView : SurfaceView {

    companion object {
        private const val TAG = "MySurfaceView"
        private const val FPS = 30
        private const val RATE = 1000 / FPS
    }

    private val dateFormat = SimpleDateFormat("hh:mm:ss.SSS")
    private val average = Average()
    private val paint = Paint().apply { color = Color.BLUE }
    private var mBitmapCache: Bitmap? = null
    private var mCanvasCache: Canvas? = null

    private val asyncHandler: Handler by lazy {
        object : Handler(HandlerThread("async").apply { start() }.looper) {
            override fun handleMessage(msg: Message?) {
                drawSimple()
                asyncHandler.sendEmptyMessage(0)
            }
        }
    }

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)

    init {
        holder.addCallback(object : SurfaceHolder.Callback2 {
            override fun surfaceCreated(holder: SurfaceHolder?) {
                Log.d(TAG, "surfaceCreated: ")
                asyncHandler.sendEmptyMessage(0)
            }

            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
                Log.d(TAG, "surfaceChanged: ")
                mBitmapCache = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)?.apply {
                    mCanvasCache = Canvas(this)
                }
            }

            override fun surfaceRedrawNeeded(holder: SurfaceHolder?) {
                Log.d(TAG, "surfaceRedrawNeeded: ")
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                Log.d(TAG, "surfaceDestroyed: ")
                asyncHandler.removeCallbacksAndMessages(null)
                if (mBitmapCache?.isRecycled == false) {
                    mBitmapCache?.recycle()
                }
            }
        })
    }

    private var lastDrawnTime: Long = 0

    fun drawSimple() {
        holder.lockCanvas()?.apply {
            onDrawSimple(this)
            val delta = System.currentTimeMillis() - lastDrawnTime
            val sleepTime = RATE - average.get() - delta
            if (sleepTime > 0) {
                Thread.sleep(sleepTime)
            }
            Log.d(TAG, "drawSimple: current = " + dateFormat.format(System.currentTimeMillis()))
            val startMs = System.currentTimeMillis()
            holder.unlockCanvasAndPost(this)
            average.update(System.currentTimeMillis() - startMs)
            lastDrawnTime = System.currentTimeMillis();
        }
    }

    private fun onDrawSimple(canvas: Canvas) {
        mCanvasCache?.drawColor(Color.RED)
        mCanvasCache?.drawRect(0F, 0F, width.toFloat(), height.toFloat(), paint)
        mBitmapCache?.apply {
            canvas.drawBitmap(this, 0F, 0F, null)
        }
    }
}