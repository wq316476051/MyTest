package com.wang.mytest.feature.ui.view.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

public class DrawView extends View {

    private static final String TAG = "DrawView";

    public static final int SHAPE_FREE = 0;
    public static final int SHAPE_LINE = 1;
    public static final int SHAPE_RECT = 2;
    public static final int SHAPE_CIRCLE = 3;

    @IntDef({SHAPE_FREE, SHAPE_LINE, SHAPE_RECT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DrawShape {}

    private Bitmap mBitmap;
    private Canvas mCanvas;

    private Path mPath;
    private Paint mPaint;

    private int mDownX;
    private int mDownY;

    private int mLastX;
    private int mLastY;

    private int mCurrentShape = SHAPE_FREE;

    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.RED);

        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            if (mBitmap == null) {
                mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                mCanvas = new Canvas(mBitmap);
            } else {
                Bitmap old = mBitmap;
                mBitmap = Bitmap.createBitmap(old, 0, 0, w, h);
                mCanvas = new Canvas(mBitmap);
                if (!old.isRecycled()) {
                    old.recycle();
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentX = (int) event.getX();
        int currentY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mDownX = currentX;
                mDownY = currentY;
                mLastX = currentX;
                mLastY = currentY;

                if (mCurrentShape == SHAPE_FREE) {
                    mPath.moveTo(mDownX, mDownY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mCurrentShape == SHAPE_FREE) {
                    int controlX = (mLastX + currentX) / 2;
                    int controlY = (mLastY + currentY) / 2;
                    mPath.quadTo(controlX, controlY, currentX, currentY);
                } else if (mCurrentShape == SHAPE_LINE) {
                    mPath.reset();
                    mPath.moveTo(mDownX, mDownY);
                    mPath.lineTo(currentX, currentY);
                } else if (mCurrentShape == SHAPE_RECT) {
                    mPath.reset();
                    float left = Math.min(mDownX, currentX);
                    float top = Math.min(mDownY, currentY);
                    float right = Math.max(mDownX, currentX);
                    float bottom = Math.max(mDownY, currentY);
                    mPath.addRect(left, top, right, bottom, Path.Direction.CCW);
                } else if (mCurrentShape == SHAPE_CIRCLE) {
                    mPath.reset();
                    float centerX = (mDownX + currentX) / 2;
                    float centerY = (mDownY + currentY) / 2;
                    float radius = (float) Math.sqrt(Math.pow(currentX - mDownX, 2) + Math.pow(currentY - mDownY, 2)) / 2;
                    mPath.addCircle(centerX, centerY, radius, Path.Direction.CCW);
                }
                invalidate();
                mLastX = currentX;
                mLastY = currentY;
                break;
            case MotionEvent.ACTION_UP:
                mCanvas.drawPath(mPath, mPaint);
                invalidate();
                break;
        }
        return true;
    }

    public void setNextShape(@DrawShape int shape) {
        mCurrentShape = shape;
    }

    public void save() {
        if (mBitmap != null && !mBitmap.isRecycled()) {
            File drawDirectory = getContext().getExternalFilesDir("draws");
            if (!drawDirectory.exists()) {
                drawDirectory.mkdirs();
            }
            String filename = "draw_" + System.currentTimeMillis() + ".png";
            try (FileOutputStream fos = new FileOutputStream(new File(drawDirectory, filename))) {
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void reset() {
        if (mCanvas != null) {
            mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            mPath.reset();
            invalidate();
        }
    }
}
