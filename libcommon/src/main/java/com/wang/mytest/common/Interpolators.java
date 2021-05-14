package com.wang.mytest.common;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

public class Interpolators {

    public static final Interpolator FAST_OUT_SLOW_IN
            = new PathInterpolator(0.4f, 0f, 0.2f, 1f);
}
