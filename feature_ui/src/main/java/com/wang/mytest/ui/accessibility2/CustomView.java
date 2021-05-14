package com.wang.mytest.ui.accessibility2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomView extends View {

    private static final String TAG = "CustomView";

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private VirtualView mVirtualView = new VirtualView(100,
            new Rect(300, 300, 600, 600),
            "Content for test");

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.BLUE);
        setAccessibilityDelegate(new MyDelegate() {

            @Override
            protected void collectVirtualViewIds(@NonNull List<Integer> virtualViewIds) {
                // TODO: 2020/7/5 填充 virtualViewId 集合
                Log.d(TAG, "collectVirtualViewIds: ");
                virtualViewIds.add(mVirtualView.id);
            }

            @Override
            protected void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfo info) {
                // TODO: 2020/7/5 填充 AccessibilityNodeInfo
                Log.d(TAG, "onPopulateNodeForVirtualView: ");
                if (info == null) {
                    return;
                }
                info.setText("Text for test");
                info.setContentDescription("ContentDescription for test");

                Rect boundsInParent = mVirtualView.bounds;
                Log.d(TAG, "onPopulateNodeForVirtualView: boundsInParent = " + boundsInParent);
                info.setBoundsInParent(boundsInParent);

                int[] screenLocation = new int[2];
                getLocationOnScreen(screenLocation);
                int parentLeft = screenLocation[0];
                int parentTop = screenLocation[1];
                Log.d(TAG, "onPopulateNodeForVirtualView: screenLocation = " + parentLeft + ", " + parentTop);

                Rect boundsInScreen = new Rect(boundsInParent);
                boundsInScreen.offset(parentLeft, parentTop);
                info.setBoundsInScreen(boundsInScreen);
                Log.d(TAG, "onPopulateNodeForVirtualView: boundsInScreen = " + boundsInScreen);
            }

            @Override
            protected void onPopulateEventForVirtualView(int virtualViewId, AccessibilityEvent event) {
                Log.d(TAG, "onPopulateEventForVirtualView: ");
                if (event == null) {
                    return;
                }
                event.getText().clear();
                event.getText().add("Text for test");
                event.setContentDescription("Content description for test");
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mVirtualView.bounds, mPaint);
    }
}
