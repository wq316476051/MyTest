package com.wang.mytest.feature.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScreenActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ScreenActivity"
    }

    private lateinit var mTvDetails: TextView
    private lateinit var mTvExtra: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_screen_details)
        mTvDetails = findViewById(R.id.tv_details)
        mTvExtra = findViewById(R.id.tv_extra)

        printScreenDetails()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        printScreenDetails()
    }

    private fun printScreenDetails() {
        val metric = DisplayMetrics()
        windowManager.defaultDisplay.getRealMetrics(metric) // 整个屏幕参数

        val details = """
            density: ${metric.density}
            densityDpi: ${metric.densityDpi}
            
            real:
            screen width(px): ${metric.widthPixels}
            screen height(px): ${metric.heightPixels}
            screen width(dp): ${metric.widthPixels / metric.density}
            screen height(dp): ${metric.heightPixels / metric.density}
            
            available:
            screen width(px): ${resources.displayMetrics.widthPixels}
            screen height(px): ${resources.displayMetrics.heightPixels}
            screen width(dp): ${resources.configuration.screenWidthDp}
            screen height(dp): ${resources.configuration.screenHeightDp}
        """.trimIndent()

        mTvDetails.text = details
        mTvExtra.text = resources.getDimensionPixelSize(R.dimen.test_dimen).toString()

        Log.d(TAG, "printScreenDetails: statusBarHeight = ${getStatusBarHeight()}px, ${getStatusBarHeight() / resources.displayMetrics.density}dp")
        Log.d(TAG, "printScreenDetails: navigationBarHeight = ${getNavigationBarHeight()}px, ${getNavigationBarHeight() / resources.displayMetrics.density}dp")
    }

    private fun getStatusBarHeight(): Int {
        val resId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resId)
    }

    private fun getNavigationBarHeight(): Int {
        val resId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resId)
    }

//    private fun getDeviceDensity(): Int {
//        //   qemu.sf.lcd_density can be used to override ro.sf.lcd_density
//        //   when running in the emulator, allowing for dynamic configurations.
//        //   The reason for this is that ro.sf.lcd_density is write-once and is
//        //   set by the init process when it parses build.prop before anything else.
//        return SystemProperties.getInt("qemu.sf.lcd_density",
//                SystemProperties.getInt("ro.sf.lcd_density", 160));
//    }
}