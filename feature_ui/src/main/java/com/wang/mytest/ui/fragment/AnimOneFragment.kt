package com.wang.mytest.ui.fragment

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.widget.Button
import androidx.fragment.app.Fragment
import com.wang.mytest.ui.R
import android.view.animation.TranslateAnimation
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentTransaction
import com.wang.mytest.common.util.ToastUtils


class AnimOneFragment : Fragment() {

    companion object {
        const val TAG = "AnimOneFragment"
        fun newInstance() : AnimOneFragment = AnimOneFragment()
    }

    private lateinit var mToolbar: Toolbar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView: ")
        return inflater.inflate(R.layout.fragment_anim_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        val activity = requireActivity()

        mToolbar = view.findViewById(R.id.toolbar)
        mToolbar.setOnMenuItemClickListener(this::onOptionsItemSelected)
        setHasOptionsMenu(true)
        view.findViewById<Button>(R.id.btn_anim_one).setOnClickListener {
            ToastUtils.showShort(activity, "one")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        Log.d(TAG, "onCreateOptionsMenu: ${Log.getStackTraceString(Throwable())}")
        val selfMenu = mToolbar.menu
        inflater.inflate(R.menu.search_view, selfMenu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val selfMenu = mToolbar.menu
        val item = selfMenu.findItem(R.id.options_search)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
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