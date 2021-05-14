package com.wang.mytest.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

import androidx.annotation.IntRange;

public class PagedView extends ViewGroup {

    private static final int ALIGNMENT_START = 0;
    private static final int ALIGNMENT_END = 1;
    private static final int ALIGNMENT_CENTER = 2;

    private static final int INVALID_PAGE = -1;

    protected final Rect mInsets = new Rect();

    private final OverScroller mScroller;

    private final int mTouchSlop;
    private final int mPagingTouchSlop;
    private final int mMaximumVelocity;
    private final float mDensity;

    @IntRange(from = 1)
    private int mRowCount;

    private int mAlignment;

    private int mPageSpacingHorizontal;
    private int mPageSpacingVertical;

    private int[] mPageScrolls; // 滚动量
    private Rect[] mPageRects; // 布局位置

    private int mCurrentPage;
    private int mNextPage = INVALID_PAGE;

    public PagedView(Context context) {
        this(context, null);
    }

    public PagedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(context);

        mCurrentPage = 0;

        final ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mTouchSlop = configuration.getScaledPagingTouchSlop();
        mPagingTouchSlop = configuration.getScaledPagingTouchSlop();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mDensity = getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.UNSPECIFIED) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        if (widthSize <= 0 || heightSize <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        final int verticalPadding = getPaddingTop() + getPaddingBottom();
        final int horizontalPadding = getPaddingLeft() + getPaddingRight();

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getPageAt(i);

            if (child.getVisibility() != GONE) {
                final LayoutParams lp = child.getLayoutParams();
                int childWidthMode = lp.width == LayoutParams.WRAP_CONTENT ? MeasureSpec.AT_MOST : MeasureSpec.EXACTLY;
                int childHeightMode = lp.height == LayoutParams.WRAP_CONTENT ? MeasureSpec.AT_MOST : MeasureSpec.EXACTLY;

                int childWidth = widthSize - horizontalPadding - mInsets.left - mInsets.right;
                int childHeight = heightSize - verticalPadding - mInsets.top - mInsets.bottom;

                final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, childWidthMode);
                final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, childHeightMode);
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() == 0) {
            return;
        }

        final int childCount = getChildCount();
        if (mPageScrolls == null || mPageScrolls.length != childCount) {
            mPageScrolls = new int[childCount];
        }
        if (mPageRects == null || mPageRects.length != childCount) {
            mPageRects = new Rect[childCount];
        }

        boolean isPageScrollChanged = false;
        if (layoutChild(mPageRects, mPageScrolls, true)) {
            isPageScrollChanged = true;
        }

        if (isPageScrollChanged) {
            // TODO: 2021/5/14 滚动
        }
    }

    private boolean layoutChild(Rect[] pageRects, int[] pageScrolls, boolean isLayout) {
        final int childCount = getChildCount();

        int childLeft = getPaddingLeft();
        for (int i = 0; i < childCount; i += mRowCount) {
            final View temp = getPageAt(i);

            final int end = Math.min(i + mRowCount, childCount);
            int childTop = getPaddingTop();
            for (int j = i; j < end; j++) {
                final View child = getPageAt(j);

                final int childRight = childLeft + child.getMeasuredWidth();
                final int childBottom = childTop + child.getMeasuredHeight();

                mPageRects[j] = new Rect(childLeft, childTop, childRight, childBottom);
                mPageScrolls[j] = calculatePageScroll(childLeft, child.getMeasuredWidth());

                if (isLayout) {
                    child.layout(childLeft, childTop, childRight, childBottom);
                }
                childTop += child.getMeasuredHeight() + mPageSpacingVertical;
            }
            childLeft += temp.getMeasuredWidth() + mPageSpacingHorizontal;
        }
        return false;
    }

    private int calculatePageScroll(int childLeft, int childWidth) {
        final int defPageScroll = childLeft - getPaddingLeft();

        switch (mAlignment) {
            case ALIGNMENT_CENTER:
                return defPageScroll + (getMeasuredWidth() - childWidth) / 2;
            case ALIGNMENT_END:
                return defPageScroll + getMeasuredWidth() - childWidth;
            case ALIGNMENT_START:
            default:
                return defPageScroll;

        }
    }

    private int getPageCount() {
        return getChildCount();
    }

    private View getPageAt(int i) {
        return getChildAt(i);
    }
}
