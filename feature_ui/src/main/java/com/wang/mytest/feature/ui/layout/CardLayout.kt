package com.wang.mytest.feature.ui.layout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import com.wang.mytest.feature.ui.R
import kotlinx.coroutines.runBlocking
import java.util.*


class CardLayout : ViewGroup {

    companion object {
        const val TAG = "CardLayout"

        const val ANIM_SHORT = 400L
        const val ANIM_LONG = 500L

        const val LEFT_ONLY = 1
        const val RIGHT_ONLY = 2
        const val LEFT_RIGHT = 3
    }

    private lateinit var mLeft: View
    private lateinit var mRight: View

    private var mMode = LEFT_ONLY

    private var mInterval: Int = 0
    private var mIntervalColor: Int = Color.WHITE
    private val mIntervalRect: Rect = Rect()
    private val mIntervalPaint: Paint = Paint()
    private var mRightAnimateStartListener: (() -> Unit)? = null
    private var mRightAnimateEndListener: (() -> Unit)? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        val array = context.obtainStyledAttributes(attributeSet, R.styleable.CardLayout)

        for (i in 0 until array.indexCount) {
            when (val attr = array.getIndex(i)) {
                R.styleable.CardLayout_mode -> {
                    mMode = array.getInt(attr, LEFT_ONLY)
                }
                R.styleable.CardLayout_interval -> {
                    mInterval = array.getDimensionPixelSize(attr, 0)
                }
                R.styleable.CardLayout_intervalColor -> {
                    mIntervalColor = array.getColor(attr, Color.WHITE)
                }
            }
        }

        mIntervalPaint.isAntiAlias = true
        mIntervalPaint.color = mIntervalColor

        array.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mLeft = getChildAt(0)
        mRight = getChildAt(1)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = MeasureSpec.getSize(widthMeasureSpec);
        val one2threeSize = size / 3
        val two2threeSize = size - one2threeSize
        when (mMode) {
            LEFT_ONLY -> {
                val measureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY)
                mLeft.measure(measureSpec, heightMeasureSpec)
                mRight.measure(measureSpec, heightMeasureSpec)
            }
            RIGHT_ONLY -> {
                val measureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY)
                mLeft.measure(measureSpec, heightMeasureSpec)
                mRight.measure(measureSpec, heightMeasureSpec)
            }
            LEFT_RIGHT -> {
                mLeft.measure(MeasureSpec.makeMeasureSpec(one2threeSize, MeasureSpec.EXACTLY), heightMeasureSpec)
                mRight.measure(MeasureSpec.makeMeasureSpec(two2threeSize - mInterval, MeasureSpec.EXACTLY), heightMeasureSpec)
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
//        super.onLayout(changed, left, top, right, bottom)
        Log.d(TAG, "onLayout: top = $top")
        val width = right - left
        val height = bottom - top
        val one2threeWidth = width / 3
        val two2threeWidth = width - one2threeWidth
        when (mMode) {
            LEFT_ONLY -> {
                mLeft.layout(0, 0, width, height)
                mRight.layout(0, 0, width, height)

                mLeft.translationX = 0F
                mRight.translationX = width.toFloat()
            }
            RIGHT_ONLY -> {
                mLeft.layout(0, 0, width, height)
                mRight.layout(0, 0, width, height)

                mLeft.translationX = -width.toFloat()
                mRight.translationX = 0F
            }
            LEFT_RIGHT -> {
                mLeft.layout(0, 0, one2threeWidth, height)
                mRight.layout(0, 0, two2threeWidth - mInterval, height)

                if (isRtl()) {
                    mLeft.translationX = two2threeWidth.toFloat() + mInterval
                    mRight.translationX = 0F
                    mIntervalRect.set(two2threeWidth, 0, two2threeWidth + mInterval, height)
                } else {
                    mLeft.translationX = 0F
                    mRight.translationX = one2threeWidth.toFloat() + mInterval
                    mIntervalRect.set(one2threeWidth, 0, one2threeWidth + mInterval, height)
                }
            }
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mMode == LEFT_RIGHT) {
            canvas?.drawRect(mIntervalRect, mIntervalPaint)
        }
    }

    fun setRightAnimateStartListener(listener: () -> Unit) {
        mRightAnimateStartListener = listener
    }

    fun setRightAnimateEndListener(listener: () -> Unit) {
        mRightAnimateEndListener = listener
    }

    fun setDisplayMode(newMode: Int) {
        if (mMode == newMode) {
            return
        }
        val lastMode = mMode;
        mMode = newMode;
        when {
            lastMode == LEFT_ONLY && newMode == RIGHT_ONLY -> {
                left2Right()
            }
            lastMode == LEFT_ONLY && newMode == LEFT_RIGHT -> {
                left2LeftRight()
            }
            lastMode == RIGHT_ONLY && newMode == LEFT_ONLY -> {
                right2Left()
            }
            lastMode == RIGHT_ONLY && newMode == LEFT_RIGHT -> {
                right2LeftRight()
            }
            lastMode == LEFT_RIGHT && newMode == LEFT_ONLY -> {
                leftRight2Left()
            }
            lastMode == LEFT_RIGHT && newMode == RIGHT_ONLY -> {
                leftRight2Right()
            }
        }
    }

    private fun leftRight2Left() {
        mLeft.animate().cancel()
        mRight.animate().cancel()
        requestLayout()
    }

    private fun leftRight2Right() {
        mLeft.animate().cancel()
        mRight.animate().cancel()
        requestLayout()
    }

    private fun right2LeftRight() {
        mLeft.animate().cancel()
        mRight.animate().cancel()
        requestLayout()
    }

    private fun right2Left() {
        mLeft.animate().cancel()
        mLeft.translationX = 0F
        mLeft.invalidate()
        mRight.animate().translationX(width.toFloat())
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setDuration(ANIM_SHORT)
                .withStartAction {
                    mRightAnimateStartListener?.invoke()
                }
                .withEndAction {
                    mRightAnimateEndListener?.invoke()
                }
    }

    private fun left2Right() {
        mLeft.translationX = 0F
        mLeft.invalidate()

        mLeft.animate().translationX(-width.toFloat())
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setDuration(ANIM_LONG)

        mRight.translationX = width.toFloat()
        mRight.invalidate()
        mRight.animate().translationX(0F)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setDuration(ANIM_SHORT)
                .withStartAction {
                    mRightAnimateStartListener?.invoke()
                }
                .withEndAction {
                    mRightAnimateEndListener?.invoke()
                }
    }

    private fun left2LeftRight() {
        mLeft.animate().cancel()
        mRight.animate().cancel()
        requestLayout()
    }

    private fun isRtl() = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == View.LAYOUT_DIRECTION_RTL
}