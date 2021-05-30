package com.wang.mytest.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.fragment.app.FragmentActivity

import com.wang.mytest.ui.R

class AnimFragmentActivity : FragmentActivity(), View.OnClickListener {

    companion object {
        const val TAG = "AnimFragmentActivity"
    }

    private var mCurrentContainerIdIfShowSingle = R.id.container
    private var mLeftCard = Card()
    private var mRightCard = Card()

    private val mAnimOneFragment = AnimOneFragment.newInstance()
    private val mAnimTwoFragment = AnimTwoFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_OPTIONS_PANEL)
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setContentView(R.layout.activity_anim_fragment)

        findViewById<Button>(R.id.btn_1).setOnClickListener(this)
        findViewById<Button>(R.id.btn_2).setOnClickListener(this)
        findViewById<Button>(R.id.btn_3).setOnClickListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, mAnimOneFragment)
                    .commit()
            supportFragmentManager.executePendingTransactions()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "onCreateOptionsMenu: ${Log.getStackTraceString(Throwable())}")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onClick(v: View?) {
        Log.d(TAG, "onClick: ${v == null ?: "null"}")
        when (v?.id) {
            R.id.btn_1 -> {
                var changed = false
                val transaction = supportFragmentManager.beginTransaction()
                if (!mAnimTwoFragment.isAdded) {
                    transaction.add(R.id.container, mAnimTwoFragment)
                    changed = true
                } else if (mAnimTwoFragment.isHidden) {
                    transaction.show(mAnimTwoFragment)
                    changed = true
                }
                if (changed) {
                    transaction.hide(mAnimOneFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                    supportFragmentManager.executePendingTransactions()
                }
                Log.d(TAG, "onClick: ${mAnimOneFragment.isVisible}")
            }
            R.id.btn_2 -> {

            }
            R.id.btn_3 -> {

            }
        }
    }

    class Card {
        companion object {
            const val LEFT = 1
            const val RIGHT = 2
        }

    }
}