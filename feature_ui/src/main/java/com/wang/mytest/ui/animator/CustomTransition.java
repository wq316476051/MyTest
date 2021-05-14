package com.wang.mytest.ui.animator;

import android.animation.Animator;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.transition.Transition;
import androidx.transition.TransitionValues;

public class CustomTransition extends Transition {
    @Override
    public void captureStartValues(@NonNull TransitionValues transitionValues) {

    }

    @Override
    public void captureEndValues(@NonNull TransitionValues transitionValues) {

    }

    @Nullable
    @Override
    public Animator createAnimator(@NonNull ViewGroup sceneRoot, @Nullable TransitionValues startValues, @Nullable TransitionValues endValues) {
        return super.createAnimator(sceneRoot, startValues, endValues);
    }
}
