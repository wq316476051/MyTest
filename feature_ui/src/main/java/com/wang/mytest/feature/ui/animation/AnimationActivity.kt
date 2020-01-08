package com.wang.mytest.feature.ui.animation

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.AnimationUtilsCompat

class AnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        val interpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in)
        val interpolatorCompat = AnimationUtilsCompat.loadInterpolator(this, android.R.interpolator.fast_out_slow_in)
    }
}