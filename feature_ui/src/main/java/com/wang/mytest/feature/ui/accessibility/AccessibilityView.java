package com.wang.mytest.feature.ui.accessibility;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import androidx.annotation.Nullable;

public class AccessibilityView extends View {
    private static final String TAG = "AccessibilityView";
    private AccessibilityManager accessibilityManager;
    private AccessibilityNodeProvider accessibilityNodeProvider;
    private List<VirtualView> mChildren = new ArrayList<>();

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public AccessibilityView(Context context) {
        this(context, null);
    }

    public AccessibilityView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        accessibilityManager = context.getSystemService(AccessibilityManager.class);
        mChildren.add(new VirtualView(100, new Rect(300, 300, 600, 600)));
        mChildren.add(new VirtualView(101, new Rect(300, 600, 600, 900)));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow: ");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow: ");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure: ");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: ");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: ");
        for (VirtualView child : mChildren) {
            if (child.id == 100)
                mPaint.setColor(Color.RED);
            if (child.id == 101)
                mPaint.setColor(Color.BLUE);

            canvas.drawRect(child.bounds, mPaint);
        }
    }

    @Override
    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        Log.d(TAG, "getAccessibilityNodeProvider: " + Log.getStackTraceString(new Throwable()));
        if (accessibilityNodeProvider == null) {
            accessibilityNodeProvider = new MyAcc();
        }
        return super.getAccessibilityNodeProvider();
    }

    private class VirtualView {
        private int id;
        private Rect bounds;

        private VirtualView(int id, Rect bounds) {
            this.id = id;
            this.bounds = bounds;
        }
    }

    private class MyAcc extends AccessibilityNodeProvider {

        private static final int INVALID_ID = Integer.MIN_VALUE;
        private static final int HOST_ID = View.NO_ID;
        private int mAccessibilityFocusedVirtualViewId;

        @Override
        public AccessibilityNodeInfo createAccessibilityNodeInfo(int virtualViewId) {
            Log.d(TAG, "createAccessibilityNodeInfo: virtualViewId = " + virtualViewId);
            AccessibilityNodeInfo info;
            if (virtualViewId == HOST_ID) {
                info = AccessibilityNodeInfo.obtain(AccessibilityView.this);
                info.setClassName(AccessibilityView.class.getName());
                info.setPackageName(getContext().getPackageName());
                for (VirtualView child : mChildren) {
                    info.addChild(AccessibilityView.this, child.id);
                }
            } else {
                info = AccessibilityNodeInfo.obtain();
                info.setEnabled(true);
                info.setFocusable(true);
                info.setClassName(View.class.getName());
                Optional<VirtualView> first = mChildren.stream().filter(it -> it.id == virtualViewId).findFirst();
                first.ifPresent(it -> {
                    info.setBoundsInParent(it.bounds);
                });
                info.setParent(AccessibilityView.this);

                info.setPackageName(getContext().getPackageName());
                info.setSource(AccessibilityView.this, virtualViewId);

                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_ACCESSIBILITY_FOCUS);
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_SELECTION);
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_SELECTION);
            }
            return info;
        }

        @Override
        public boolean performAction(int virtualViewId, int action, Bundle arguments) {
            Log.d(TAG, "performAction: virtualViewId = " + virtualViewId + "; action = " + action);
            return super.performAction(virtualViewId, action, arguments);
        }
    }
}
