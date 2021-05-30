package com.wang.mytest.ui.view.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.wang.mytest.ui.R;
import com.wang.mytest.common.util.LogUtils;

public class MatrixView extends View {
    private static final String TAG = "MatrixView";

    private final Bitmap mBitmap;

    private final Matrix mMatrix = new Matrix();
    private final Paint mPaint = new Paint();

    public MatrixView(Context context) {
        this(context, null);
    }

    public MatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_small, options);

        LogUtils.debug(TAG, "MatrixView: " + options.outWidth);
        LogUtils.debug(TAG, "MatrixView:2 " + mBitmap.getWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, mMatrix, mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
        }
    }

    public void setTransition(float dx, float dy) {
        mMatrix.setTranslate(dx, dy);
        invalidate();
    }

    public void setScale(float sx, float sy) {
        mMatrix.setScale(sx, sy);
        invalidate();
    }

    public void reset() {
        mMatrix.reset();
        invalidate();
    }
}
