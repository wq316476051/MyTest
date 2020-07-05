package com.wang.mytest.feature.ui.accessibility;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.core.view.accessibility.AccessibilityRecordCompat;

/**
 * 模仿 /frameworks/support/customview/src/main/java/androidx/customview/widget/ExploreByTouchHelper.java
 *
 * 另外：/frameworks/base/core/java/android/widget/NumberPicker.java
 */
public /*abstract*/ class MyAccessibilityDelegate extends AccessibilityDelegateCompat {

    private static final String TAG = "MyAccessibilityDelegate";
    private static final int INVALID_ID = Integer.MIN_VALUE;
    private static final int HOST_ID = View.NO_ID;

    /**
     * Default class name used for virtual views.
     */
    private static final String DEFAULT_CLASS_NAME = "android.view.View";

    /**
     * Default bounds used to determine if the client didn't set any.
     */
    private static final Rect INVALID_PARENT_BOUNDS = new Rect(
            Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

    // Temporary, reusable data structures.
    private final Rect mTempScreenRect = new Rect();
    private final Rect mTempParentRect = new Rect();
    private final Rect mTempVisibleRect = new Rect();
    private final int[] mTempGlobalRect = new int[2];

    private final View mHost;
    private final AccessibilityManager mManager;
    private AccessibilityNodeProviderCompat mAccessibilityNodeProviderCompat;
    private int mAccessibilityFocusedVirtualViewId = INVALID_ID;
    private int mKeyboardFocusedVirtualViewId = INVALID_ID;

    public MyAccessibilityDelegate(@NonNull View host) {
        mHost = host;
        mManager = host.getContext().getSystemService(AccessibilityManager.class);
        // Host view must be focusable so that we can delegate to virtual views.
        mHost.setFocusable(true);
        if (ViewCompat.getImportantForAccessibility(host) == ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_AUTO) {
            ViewCompat.setImportantForAccessibility(host, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES);
        }
    }

    @Override
    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View host) {
        Log.d(TAG, "getAccessibilityNodeProvider: ");
        if (mAccessibilityNodeProviderCompat == null) {
            mAccessibilityNodeProviderCompat = new MyNodeProvider();
        }
        return mAccessibilityNodeProviderCompat;
    }


    private AccessibilityNodeInfoCompat obtainAccessibilityNodeInfo(int virtualViewId) {
        if (virtualViewId == HOST_ID) {
            return createNodeForHost();
        }
        return createNodeForChild(virtualViewId);
    }

    private AccessibilityNodeInfoCompat createNodeForHost() {
        final AccessibilityNodeInfoCompat info = AccessibilityNodeInfoCompat.obtain(mHost);
        ViewCompat.onInitializeAccessibilityNodeInfo(mHost, info);

        // Add the virtual descendants.
        final ArrayList<Integer> virtualViewIds = new ArrayList<>();
//        getVisibleVirtualViews(virtualViewIds);
        virtualViewIds.add(100);

        final int realNodeCount = info.getChildCount();
        if (realNodeCount > 0 && virtualViewIds.size() > 0) {
            throw new RuntimeException("Views cannot have both real and virtual children");
        }
        for (int i = 0, count = virtualViewIds.size(); i < count; i++) {
            info.addChild(mHost, virtualViewIds.get(i));
        }
        return info;
    }

//    protected abstract void getVisibleVirtualViews(ArrayList<Integer> virtualViewIds);

    private AccessibilityNodeInfoCompat createNodeForChild(int virtualViewId) {
        final AccessibilityNodeInfoCompat node = AccessibilityNodeInfoCompat.obtain();

        // Ensure the client has good defaults.
        node.setEnabled(true);
        node.setFocusable(true);
        node.setClassName(DEFAULT_CLASS_NAME);
        node.setBoundsInParent(INVALID_PARENT_BOUNDS);
        node.setBoundsInScreen(INVALID_PARENT_BOUNDS);
        node.setParent(mHost);

        // Allow the client to populate the node.
//         onPopulateNodeForVirtualView(virtualViewId, node);
        node.setText("Text for test");
        node.setContentDescription("ContentDescription for test");
        node.setBoundsInParent(new Rect(0, 0, 300, 300));

        // Make sure the developer is following the rules.
        if ((node.getText() == null) && (node.getContentDescription() == null)) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }

        node.getBoundsInParent(mTempParentRect);
        if (mTempParentRect.equals(INVALID_PARENT_BOUNDS)) {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }

        final int actions = node.getActions();
        if ((actions & AccessibilityNodeInfoCompat.ACTION_ACCESSIBILITY_FOCUS) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }
        if ((actions & AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS) != 0) {
            throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
        }

        // Don't allow the client to override these properties.
        node.setPackageName(mHost.getContext().getPackageName());
        node.setSource(mHost, virtualViewId);

        // Manage internal accessibility focus state.
        if (mAccessibilityFocusedVirtualViewId == virtualViewId) {
            node.setAccessibilityFocused(true);
            node.addAction(AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
        } else {
            node.setAccessibilityFocused(false);
            node.addAction(AccessibilityNodeInfoCompat.ACTION_ACCESSIBILITY_FOCUS);
        }

        // Manage internal keyboard focus state.
        final boolean isFocused = mKeyboardFocusedVirtualViewId == virtualViewId;
        if (isFocused) {
            node.addAction(AccessibilityNodeInfoCompat.ACTION_CLEAR_FOCUS);
        } else if (node.isFocusable()) {
            node.addAction(AccessibilityNodeInfoCompat.ACTION_FOCUS);
        }
        node.setFocused(isFocused);

        mHost.getLocationOnScreen(mTempGlobalRect);

        // If not explicitly specified, calculate screen-relative bounds and
        // offset for scroll position based on bounds in parent.
        node.getBoundsInScreen(mTempScreenRect);
        if (mTempScreenRect.equals(INVALID_PARENT_BOUNDS)) {
            node.getBoundsInParent(mTempScreenRect);

            // If there is a parent node, adjust bounds based on the parent node.
            if (node.mParentVirtualDescendantId != HOST_ID) {
                AccessibilityNodeInfoCompat parentNode = AccessibilityNodeInfoCompat.obtain();
                // Walk up the node tree to adjust the screen rect.
                for (int virtualDescendantId = node.mParentVirtualDescendantId;
                     virtualDescendantId != HOST_ID;
                     virtualDescendantId = parentNode.mParentVirtualDescendantId) {
                    // Reset the values in the parent node we'll be using.
                    parentNode.setParent(mHost, HOST_ID);
                    parentNode.setBoundsInParent(INVALID_PARENT_BOUNDS);
                    // Adjust the bounds for the parent node.
                    // TODO: 2020/7/5
                    // onPopulateNodeForVirtualView(virtualDescendantId, parentNode);
                    parentNode.getBoundsInParent(mTempParentRect);
                    mTempScreenRect.offset(mTempParentRect.left, mTempParentRect.top);
                }
                parentNode.recycle();
            }
            // Adjust the rect for the host view's location.
            mTempScreenRect.offset(mTempGlobalRect[0] - mHost.getScrollX(),
                    mTempGlobalRect[1] - mHost.getScrollY());
        }

        if (mHost.getLocalVisibleRect(mTempVisibleRect)) {
            mTempVisibleRect.offset(mTempGlobalRect[0] - mHost.getScrollX(),
                    mTempGlobalRect[1] - mHost.getScrollY());
            final boolean intersects = mTempScreenRect.intersect(mTempVisibleRect);
            if (intersects) {
                node.setBoundsInScreen(mTempScreenRect);

                if (isVisibleToUser(mTempScreenRect)) {
                    node.setVisibleToUser(true);
                }
            }
        }
        return node;
    }

//    protected abstract void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfoCompat node);

    private boolean isVisibleToUser(Rect localRect) {
        // Missing or empty bounds mean this view is not visible.
        if ((localRect == null) || localRect.isEmpty()) {
            return false;
        }

        // Attached to invisible window means this view is not visible.
        if (mHost.getWindowVisibility() != View.VISIBLE) {
            return false;
        }

        // An invisible predecessor means that this view is not visible.
        ViewParent viewParent = mHost.getParent();
        while (viewParent instanceof View) {
            final View view = (View) viewParent;
            if ((view.getAlpha() <= 0) || (view.getVisibility() != View.VISIBLE)) {
                return false;
            }
            viewParent = view.getParent();
        }

        // A null parent implies the view is not visible.
        return viewParent != null;
    }

    private boolean performAction(int virtualViewId, int action, Bundle arguments) {
        switch (virtualViewId) {
            case HOST_ID:
                return performActionForHost(action, arguments);
            default:
                return performActionForChild(virtualViewId, action, arguments);
        }
    }

    private boolean performActionForHost(int action, Bundle arguments) {
        Log.d(TAG, "performActionForHost: ");
        return ViewCompat.performAccessibilityAction(mHost, action, arguments);
    }

    private boolean performActionForChild(int virtualViewId, int action, Bundle arguments) {
        Log.d(TAG, "performActionForChild: ");
        switch (action) {
            case AccessibilityNodeInfoCompat.ACTION_ACCESSIBILITY_FOCUS:
                return requestAccessibilityFocus(virtualViewId);
            case AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS:
                return clearAccessibilityFocus(virtualViewId);
//            case AccessibilityNodeInfoCompat.ACTION_FOCUS:
//                return requestKeyboardFocusForVirtualView(virtualViewId);
//            case AccessibilityNodeInfoCompat.ACTION_CLEAR_FOCUS:
//                return clearKeyboardFocusForVirtualView(virtualViewId);
//            default:
//                return onPerformActionForVirtualView(virtualViewId, action, arguments);
        }
        return false;
    }

    private boolean requestAccessibilityFocus(int virtualViewId) {
        if (!mManager.isEnabled() || !mManager.isTouchExplorationEnabled()) {
            return false;
        }
        // TODO: Check virtual view visibility.
        if (mAccessibilityFocusedVirtualViewId != virtualViewId) {
            // Clear focus from the previously focused view, if applicable.
            if (mAccessibilityFocusedVirtualViewId != INVALID_ID) {
                clearAccessibilityFocus(mAccessibilityFocusedVirtualViewId);
            }

            // Set focus on the new view.
            mAccessibilityFocusedVirtualViewId = virtualViewId;

            // TODO: Only invalidate virtual view bounds.
            mHost.invalidate();
            sendEventForVirtualView(virtualViewId,
                    AccessibilityEventCompat.TYPE_VIEW_ACCESSIBILITY_FOCUSED);
            return true;
        }
        return false;
    }

    private boolean clearAccessibilityFocus(int virtualViewId) {
        if (mAccessibilityFocusedVirtualViewId == virtualViewId) {
            mAccessibilityFocusedVirtualViewId = INVALID_ID;
            mHost.invalidate();
            sendEventForVirtualView(virtualViewId,
                    AccessibilityEventCompat.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED);
            return true;
        }
        return false;
    }

    private final boolean sendEventForVirtualView(int virtualViewId, int eventType) {
        if ((virtualViewId == INVALID_ID) || !mManager.isEnabled()) {
            return false;
        }

        final ViewParent parent = mHost.getParent();
        if (parent == null) {
            return false;
        }
        Log.d(TAG, "sendEventForVirtualView: virtualViewId = " + virtualViewId);
        final AccessibilityEvent event = createEvent(virtualViewId, eventType);
        return parent.requestSendAccessibilityEvent(mHost, event);
    }

    private AccessibilityEvent createEvent(int virtualViewId, int eventType) {
        switch (virtualViewId) {
            case HOST_ID:
                return createEventForHost(eventType);
            default:
                return createEventForChild(virtualViewId, eventType);
        }
    }

    private AccessibilityEvent createEventForHost(int eventType) {
        final AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
        mHost.onInitializeAccessibilityEvent(event);
        return event;
    }

    private AccessibilityEvent createEventForChild(int virtualViewId, int eventType) {
        final AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
        final AccessibilityNodeInfoCompat node = obtainAccessibilityNodeInfo(virtualViewId);

        // Allow the client to override these properties,
        event.getText().add(node.getText());
        event.setContentDescription(node.getContentDescription());
        event.setScrollable(node.isScrollable());
        event.setPassword(node.isPassword());
        event.setEnabled(node.isEnabled());
        event.setChecked(node.isChecked());

        // Allow the client to populate the event.
//        onPopulateEventForVirtualView(virtualViewId, event);
        event.getText().add("Text for test");
        event.setContentDescription("ContentDescription for test");

        // Make sure the developer is following the rules.
        if (event.getText().isEmpty() && (event.getContentDescription() == null)) {
            throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
        }

        // Don't allow the client to override these properties.
        event.setClassName(node.getClassName());
        AccessibilityRecordCompat.setSource(event, mHost, virtualViewId);
        event.setPackageName(mHost.getContext().getPackageName());

        return event;
    }

//    protected abstract void onPopulateEventForVirtualView(int virtualViewId, AccessibilityEvent event);

    private class MyNodeProvider extends AccessibilityNodeProviderCompat {
        @Nullable
        @Override
        public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int virtualViewId) {
            Log.d(TAG, "createAccessibilityNodeInfo: virtualViewId = " + virtualViewId + "; " + Log.getStackTraceString(new Throwable()));
            final AccessibilityNodeInfoCompat node =
                    MyAccessibilityDelegate.this.obtainAccessibilityNodeInfo(virtualViewId);
            return AccessibilityNodeInfoCompat.obtain(node);
        }

        @Override
        public boolean performAction(int virtualViewId, int action, Bundle arguments) {
            Log.d(TAG, "performAction: virtualViewId = " + virtualViewId + "; action = " + getActionString(action) + "; " + Log.getStackTraceString(new Throwable()));
            return MyAccessibilityDelegate.this.performAction(virtualViewId, action, arguments);
        }

        @Nullable
        @Override
        public AccessibilityNodeInfoCompat findFocus(int focusType) {
            Log.d(TAG, "findFocus: focusType = " + focusType + "; " + Log.getStackTraceString(new Throwable()));
            int focusedId = (focusType == AccessibilityNodeInfoCompat.FOCUS_ACCESSIBILITY)
                    ? mAccessibilityFocusedVirtualViewId : mKeyboardFocusedVirtualViewId;
            if (focusedId == INVALID_ID) {
                return null;
            }
            return createAccessibilityNodeInfo(focusedId);
        }
    }

    @NonNull
    private static String getActionString(int action) {
        switch (action) {
            case AccessibilityNodeInfoCompat.ACTION_ACCESSIBILITY_FOCUS:
                return "accessibility_focus";
            case AccessibilityNodeInfoCompat.ACTION_FOCUS:
                return "focus";
        }
        return "unknown";
    }
}
