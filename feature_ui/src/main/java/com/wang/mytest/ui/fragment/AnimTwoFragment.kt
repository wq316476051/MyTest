package com.wang.mytest.ui.fragment

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Button
import androidx.fragment.app.Fragment
import com.wang.mytest.ui.R
import android.view.animation.TranslateAnimation
import androidx.fragment.app.FragmentTransaction
import com.wang.mytest.common.util.ToastUtils


class AnimTwoFragment : Fragment() {

    companion object {
        const val TAG = "AnimTwoFragment"
        fun newInstance() : AnimTwoFragment = AnimTwoFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_anim_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        view.findViewById<Button>(R.id.btn_anim_two).setOnClickListener {
            ToastUtils.showShort(activity, "two")
        }
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        var animation: TranslateAnimation? = null
        if (transit == FragmentTransaction.TRANSIT_FRAGMENT_OPEN) {
            animation = if (enter) {
                TranslateAnimation(Animation.RELATIVE_TO_SELF, 1F, Animation.RELATIVE_TO_SELF, 0F,
                        Animation.RELATIVE_TO_SELF, 0F, Animation.RELATIVE_TO_SELF, 0F)
            } else {
                TranslateAnimation(Animation.RELATIVE_TO_SELF, 0F, Animation.RELATIVE_TO_SELF, -1F,
                        Animation.RELATIVE_TO_SELF, 0F, Animation.RELATIVE_TO_SELF, 0F)
            }
        } else if (transit == FragmentTransaction.TRANSIT_FRAGMENT_CLOSE) {
            animation = if (enter) {
                TranslateAnimation(Animation.RELATIVE_TO_SELF, -1F, Animation.RELATIVE_TO_SELF, 0F,
                        Animation.RELATIVE_TO_SELF, 0F, Animation.RELATIVE_TO_SELF, 0F)
            } else {
                TranslateAnimation(Animation.RELATIVE_TO_SELF, 0F, Animation.RELATIVE_TO_SELF, 1F,
                        Animation.RELATIVE_TO_SELF, 0F, Animation.RELATIVE_TO_SELF, 0F)
            }
        }
        if (animation == null) {
            animation = TranslateAnimation(0F, 0F, 0F, 0F)
        }
        animation.duration = 300
        return animation
    }

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? {
        AnimatorInflater.loadAnimator(context, R.animator.fade_in_a)
        return super.onCreateAnimator(transit, enter, nextAnim)
    }
}