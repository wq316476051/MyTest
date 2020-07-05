package com.wang.mytest.feature.ui.accessibility2;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 自己实现
 *
 * public class CustomView extends View {
 *     private static final String TAG = "CustomView";
 *     private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
 *     private VirtualView mVirtualView = new VirtualView(100,
 *             new Rect(300, 300, 600, 600),
 *             "Content for test");
 *     public CustomView(Context context) {
 *         this(context, null);
 *     }
 *     public CustomView(Context context, @Nullable AttributeSet attrs) {
 *         super(context, attrs);
 *         mPaint.setColor(Color.BLUE);
 *         setAccessibilityDelegate(new MyDelegate() {
 *             @Override
 *             protected void collectVirtualViewIds(@NonNull List<Integer> virtualViewIds) {
 *                 // TODO: 2020/7/5 填充 virtualViewId 集合
 *                 Log.d(TAG, "collectVirtualViewIds: ");
 *                 virtualViewIds.add(mVirtualView.id);
 *             }
 *             @Override
 *             protected void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfo info) {
 *                 // TODO: 2020/7/5 填充 AccessibilityNodeInfo
 *                 Log.d(TAG, "onPopulateNodeForVirtualView: ");
 *                 if (info == null) {
 *                     return;
 *                 }
 *                 info.setText("Text for test");
 *                 info.setContentDescription("ContentDescription for test");
 *                 Rect boundsInParent = mVirtualView.bounds;
 *                 Log.d(TAG, "onPopulateNodeForVirtualView: boundsInParent = " + boundsInParent);
 *                 info.setBoundsInParent(boundsInParent);
 *                 int[] screenLocation = new int[2];
 *                 getLocationOnScreen(screenLocation);
 *                 int parentLeft = screenLocation[0];
 *                 int parentTop = screenLocation[1];
 *                 Log.d(TAG, "onPopulateNodeForVirtualView: screenLocation = " + parentLeft + ", " + parentTop);
 *                 Rect boundsInScreen = new Rect(boundsInParent);
 *                 boundsInScreen.offset(parentLeft, parentTop);
 *                 info.setBoundsInScreen(boundsInScreen);
 *                 Log.d(TAG, "onPopulateNodeForVirtualView: boundsInScreen = " + boundsInScreen);
 *             }
 *             @Override
 *             protected void onPopulateEventForVirtualView(int virtualViewId, AccessibilityEvent event) {
 *                 Log.d(TAG, "onPopulateEventForVirtualView: ");
 *                 if (event == null) {
 *                     return;
 *                 }
 *                 event.getText().clear();
 *                 event.getText().add("Text for test");
 *                 event.setContentDescription("Content description for test");
 *             }
 *         });
 *     }
 *     @Override
 *     protected void onDraw(Canvas canvas) {
 *         super.onDraw(canvas);
 *         canvas.drawRect(mVirtualView.bounds, mPaint);
 *     }
 * }
 */
public abstract class MyDelegate extends View.AccessibilityDelegate {

    private static final String TAG = "MyDelegate";

    private AccessibilityNodeProvider mAccessibilityNodeProvider;

    @Override
    public AccessibilityNodeProvider getAccessibilityNodeProvider(View host) {
        Log.d(TAG, "getAccessibilityNodeProvider: ");
        if (mAccessibilityNodeProvider == null) {
            mAccessibilityNodeProvider = new MyNodeProvider(host);
        }
        return mAccessibilityNodeProvider;
    }

    protected abstract void collectVirtualViewIds(@NonNull List<Integer> virtualViewIds);

    /* 填充 text & content description & bounds in parent & bounds in screen */
    protected abstract void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfo info);

    /* 填充 text & content description */
    protected abstract void onPopulateEventForVirtualView(int virtualViewId, AccessibilityEvent event);

    private class MyNodeProvider extends AccessibilityNodeProvider {

        private static final int INVALID_ID = Integer.MIN_VALUE;
        private final Rect INVALID_PARENT_BOUNDS = new Rect(
                Integer.MAX_VALUE, Integer.MAX_VALUE,
                Integer.MIN_VALUE, Integer.MIN_VALUE);

        private View mHost;
        private final AccessibilityManager mManager;
        private int mAccessibilityFocusedVirtualViewId = INVALID_ID;
        private final Rect mTempScreenRect = new Rect();
        private final Rect mTempVisibleRect = new Rect();
        private final int[] mTempGlobalRect = new int[2];

        public MyNodeProvider(@NonNull View host) {
            mHost = host;
            mManager = host.getContext().getSystemService(AccessibilityManager.class);
            mHost.setFocusable(true);
            if (ViewCompat.getImportantForAccessibility(host) == ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_AUTO) {
                ViewCompat.setImportantForAccessibility(host, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES);
            }
        }

        @NonNull
        @Override
        public AccessibilityNodeInfo createAccessibilityNodeInfo(int virtualViewId) {
            Log.d(TAG, "createAccessibilityNodeInfo: virtualViewId = " + virtualViewId);
            if (virtualViewId == AccessibilityNodeProvider.HOST_VIEW_ID) {
                return createNodeForHost();
            }
            return createNodeForChild(virtualViewId);
        }

        @Override
        public boolean performAction(int virtualViewId, int action, Bundle arguments) {
            Log.d(TAG, "performAction: virtualViewId = " + virtualViewId + "; action = " + action);
            if (virtualViewId == AccessibilityNodeProvider.HOST_VIEW_ID) {
                return performActionForHost(action, arguments);
            }
            return performActionForChild(virtualViewId, action, arguments);
        }

        @Nullable
        @Override
        public AccessibilityNodeInfo findFocus(int focus) {
            Log.d(TAG, "findFocus: focus = " + focus);
            int focusedId = (focus == AccessibilityNodeInfo.FOCUS_ACCESSIBILITY)
                    ? mAccessibilityFocusedVirtualViewId : INVALID_ID;
            if (focusedId == INVALID_ID) {
                return null;
            }
            return createAccessibilityNodeInfo(focusedId);
        }

        private AccessibilityNodeInfo createNodeForHost() {
            AccessibilityNodeInfo info = AccessibilityNodeInfo.obtain(mHost);
            List<Integer> virtualViewIds = new ArrayList<>();
            collectVirtualViewIds(virtualViewIds);
            virtualViewIds.removeIf(Objects::isNull);
            for (Integer viewId : virtualViewIds) {
                info.addChild(mHost, viewId);
            }
            return info;
        }

        private AccessibilityNodeInfo createNodeForChild(int virtualViewId) {
            AccessibilityNodeInfo info = AccessibilityNodeInfo.obtain();
            // Ensure the client has good defaults.
            info.setPackageName(mHost.getContext().getPackageName());
            info.setClassName("android.view.View");
            info.setBoundsInParent(INVALID_PARENT_BOUNDS);
            info.setBoundsInScreen(INVALID_PARENT_BOUNDS);
            info.setParent(mHost);
            info.setSource(mHost, virtualViewId);
            info.setFocusable(true);
            info.setEnabled(true);

            // Let client populate the node.
            onPopulateNodeForVirtualView(virtualViewId, info);
            // TODO: 2020/7/5 参数检查

            Log.d(TAG, "createNodeForChild: current id = " + mAccessibilityFocusedVirtualViewId);
            if (mAccessibilityFocusedVirtualViewId == virtualViewId) {
                info.setAccessibilityFocused(true);
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
            } else {
                info.setAccessibilityFocused(false);
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_ACCESSIBILITY_FOCUS);
            }

            // 可见性
//            mHost.getLocationOnScreen(mTempGlobalRect);
//            info.getBoundsInScreen(mTempScreenRect);
//            if (mHost.getLocalVisibleRect(mTempVisibleRect)) {
//                if (mTempScreenRect.intersect(mTempVisibleRect)) {
//                    info.setBoundsInScreen(mTempScreenRect);
//                    if (isVisibleToUser(mTempScreenRect)) {
                        Log.d(TAG, "createNodeForChild: isVisible");
                        info.setVisibleToUser(true);
//                    }
//                }
//            }
            return info;
        }

        private boolean isVisibleToUser(Rect visibleRect) {
            if (visibleRect == null || visibleRect.isEmpty()) {
                return false;
            }
            if (mHost.getWindowVisibility() != View.VISIBLE) {
                return false;
            }
            ViewParent viewParent = mHost.getParent();
            while (viewParent instanceof View) {
                final View view = (View) viewParent;
                if (view.getAlpha() <= 0 || view.getVisibility() != View.VISIBLE) {
                    return false;
                }
                viewParent = view.getParent();
            }
            return viewParent != null;
        }

        private boolean performActionForHost(int action, Bundle arguments) {
            return mHost.performAccessibilityAction(action, arguments);
        }

        private boolean performActionForChild(int virtualViewId, int action, Bundle arguments) {
            switch (action) {
                case AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS:
                    return requestAccessibilityFocus(virtualViewId);
                case AccessibilityNodeInfo.ACTION_CLEAR_ACCESSIBILITY_FOCUS:
                    return clearAccessibilityFocus(virtualViewId);
                default:
                    return false;
            }
        }

        private boolean requestAccessibilityFocus(int virtualViewId) {
            if (!mManager.isEnabled() || !mManager.isTouchExplorationEnabled()) {
                return false;
            }
            if (mAccessibilityFocusedVirtualViewId != virtualViewId) {
                if (mAccessibilityFocusedVirtualViewId != INVALID_ID) {
                    clearAccessibilityFocus(mAccessibilityFocusedVirtualViewId);
                }
                mAccessibilityFocusedVirtualViewId = virtualViewId;
                sendEventForVirtualView(virtualViewId, AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED);
                return true;
            }
            return false;
        }

        private boolean clearAccessibilityFocus(int virtualViewId) {
            if (mAccessibilityFocusedVirtualViewId == virtualViewId) {
                mAccessibilityFocusedVirtualViewId = INVALID_ID;
                sendEventForVirtualView(virtualViewId, AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED);
                return true;
            }
            return false;
        }

        private void sendEventForVirtualView(int virtualViewId, int eventType) {
            if (virtualViewId == INVALID_ID || !mManager.isEnabled()) {
                return;
            }
            ViewParent parent = mHost.getParent();
            if (parent == null) {
                return;
            }
            AccessibilityEvent event = createEvent(virtualViewId, eventType);
            parent.requestSendAccessibilityEvent(mHost, event);
        }

        private AccessibilityEvent createEvent(int virtualViewId, int eventType) {
            if (virtualViewId == AccessibilityNodeProvider.HOST_VIEW_ID) {
                return createEventForHost(eventType);
            }
            return createEventForChild(virtualViewId, eventType);
        }

        private AccessibilityEvent createEventForHost(int eventType) {
            AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
            mHost.onInitializeAccessibilityEvent(event);
            return event;
        }

        private AccessibilityEvent createEventForChild(int virtualViewId, int eventType) {
            AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
            AccessibilityNodeInfo node = createAccessibilityNodeInfo(virtualViewId);

            event.setClassName(node.getClassName());
            event.setPackageName(node.getPackageName());
            event.setSource(mHost, virtualViewId);
            event.getText().add(node.getText());
            event.setContentDescription(node.getContentDescription());
            event.setScrollable(node.isScrollable());
            event.setPassword(node.isPassword());
            event.setEnabled(node.isEnabled());
            event.setChecked(node.isChecked());

            onPopulateEventForVirtualView(virtualViewId, event);
            // TODO: 2020/7/5 参数检查，检查 text & content description
            return event;
        }
    }
}
