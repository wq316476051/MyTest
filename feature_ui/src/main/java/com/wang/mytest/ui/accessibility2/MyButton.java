package com.wang.mytest.ui.accessibility2;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;

public class MyButton extends Button {

    private static final String TAG = "MyButton";

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        int eventType = event.getEventType();
        int contentChangeTypes = event.getContentChangeTypes();
        Log.d(TAG, "onInitializeAccessibilityEvent: eventType = " + eventType);
        Log.d(TAG, "onInitializeAccessibilityEvent: contentChangeTypes = " + contentChangeTypes);
        if ((contentChangeTypes & AccessibilityEvent.CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION) != 0) {
            Log.d(TAG, "onInitializeAccessibilityEvent: " + Log.getStackTraceString(new Throwable()));
            // TODO: 2020/7/5 可以不播报 contentDescription 变更
            event.getText().clear();
            event.setContentDescription(null);
        }
    }

    @Override
    public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
        super.onPopulateAccessibilityEvent(event);
        // TODO: 2020/7/5 只有部分 type 会走这里
        int eventType = event.getEventType();
        int contentChangeTypes = event.getContentChangeTypes();
        Log.d(TAG, "onPopulateAccessibilityEvent: eventType = " + eventType);
        Log.d(TAG, "onPopulateAccessibilityEvent: contentChangeTypes = " + contentChangeTypes);
    }
}
