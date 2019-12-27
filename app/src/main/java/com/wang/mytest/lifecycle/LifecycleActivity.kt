package com.wang.mytest.lifecycle

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.wang.mytest.R
import com.wang.mytest.apt.annotation.Route

/**
 * 启动时调用顺序：
 *      LifecycleActivity: onCreate:
 *      LifecycleActivity: onStart:
 *      LifecycleActivity: onPostCreate:
 *      LifecycleActivity: onResume:
 *      LifecycleActivity: onResumeFragments:
 *      LifecycleActivity: onPostResume:
 *
 *      LifecycleActivity: onAttachedToWindow:
 *
 *      LifecycleActivity: onCreateOptionsMenu:
 *      LifecycleActivity: onPrepareOptionsMenu:
 */

/**
 * 启动 -- 修改字体大小 -- 回来：Activity销毁了，但是 ViewModel 没有销毁
 *
 * 1.启动
 * LifecycleActivity: onCreate:
 * LifecycleViewModel: init:
 * LifecycleActivity: onCreate: 1575469311834
 * LifecycleActivity: onCreate: 84726545
 * LifecycleViewModel: : onCreate:
 * LifecycleActivity: onStart:
 * LifecycleActivity: onPostCreate:
 * LifecycleActivity: onResume:
 * LifecycleActivity: onResumeFragments:
 * LifecycleActivity: onPostResume:
 * LifecycleActivity: onAttachedToWindow:
 * LifecycleActivity: onCreateOptionsMenu:
 * LifecycleActivity: onPrepareOptionsMenu:
 * LifecycleActivity: onUserInteraction:
 * LifecycleActivity: onUserLeaveHint:
 * LifecycleActivity: onPause:
 * LifecycleActivity: onStop:
 * LifecycleActivity: onSaveInstanceState:
 *
 * 2. 去修改字体后返回
 * LifecycleViewModel: : onDestroy:
 * LifecycleActivity: onDestroy:
 * LifecycleActivity: onDetachedFromWindow:
 * LifecycleActivity: onCreate:
 * LifecycleActivity: onCreate: 1575469311834
 * LifecycleActivity: onCreate: 84726545
 * LifecycleViewModel: : onCreate:
 * LifecycleActivity: onStart:
 * LifecycleActivity: onRestoreInstanceState:
 * LifecycleActivity: onPostCreate:
 * LifecycleActivity: onResume:
 * LifecycleActivity: onResumeFragments:
 * LifecycleActivity: onPostResume:
 * LifecycleActivity: onAttachedToWindow:
 * LifecycleActivity: onCreateOptionsMenu:
 * LifecycleActivity: onPrepareOptionsMenu:
 *
 * 3. 返回键，退出Activity
 * LifecycleActivity: onUserInteraction:
 * LifecycleActivity: onUserInteraction:
 * LifecycleActivity: onBackPressed:
 * LifecycleActivity: onPause:
 * LifecycleActivity: onStop:
 * LifecycleViewModel: : onDestroy:
 * LifecycleViewModel: : onCleared:         // ViewModel 销毁时回调
 * LifecycleActivity: onDestroy:
 * LifecycleActivity: onDetachedFromWindow:
 */
@Route(path = "/activity/app/lifecycle", title = "lifecycle")
class LifecycleActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LifecycleActivity"
    }

    private lateinit var mViewModel: LifecycleViewModel;

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(TAG, "onAttachedToWindow: ");
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d(TAG, "onDetachedFromWindow: ");
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        Log.d(TAG, "onUserInteraction: ");
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        Log.d(TAG, "onUserLeaveHint: ");
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)
        Log.d(TAG, "onCreate: ");

        mViewModel = ViewModelProviders.of(this).get(LifecycleViewModel::class.java)
        lifecycle.addObserver(mViewModel)

        mViewModel.getServiceState().observe(this, Observer {
            Log.d(TAG, "onCreate: service state = $it");
        })

        Log.d(TAG, "onCreate: " + mViewModel.hashCode())
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.d(TAG, "onPostCreate: ");
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ");
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: ");
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ");
    }

    override fun onPostResume() {
        super.onPostResume()
        Log.d(TAG, "onPostResume: ");
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ");
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ");
        lifecycle.removeObserver(mViewModel)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: ");
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState: ");
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "onCreateOptionsMenu: ");
        menu?.let {
            menuInflater.inflate(R.menu.lifecycle, it)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "onPrepareOptionsMenu: ");
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        Log.d(TAG, "onPrepareOptionsMenu: ");
        item?.apply {
            return when (itemId) {
                R.id.options_test -> {
                    Log.d(TAG, "onPrepareOptionsMenu: clicked");
                    true
                }
                else -> {
                    false
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, "onBackPressed: ");
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        Log.d(TAG, "onAttachFragment: ");
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        Log.d(TAG, "onResumeFragments: ");
    }
}