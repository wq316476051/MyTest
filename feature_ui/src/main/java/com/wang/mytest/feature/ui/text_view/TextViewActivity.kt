package com.wang.mytest.feature.ui.text_view

import android.os.Bundle
import android.text.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * 1、间距 setLineSpacing(2F, 1.0F)
 * 2、Layout -> StaticLayout, DynamicLayout, BoringLayout
 * 3、SpannableString
 */
class TextViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TextView(this).apply {
            setLineSpacing(2F, 1.0F)
        }.text = SpannableStringBuilder.valueOf("")



        val textPaint = TextPaint().apply {
            isUnderlineText = true
            fontMetrics
        }

        val staticLayout = StaticLayout.Builder.obtain("source", 0, 0, textPaint, 1)
                .build()
        val dynamicLayout = DynamicLayout("base", textPaint, 1, Layout.Alignment.ALIGN_CENTER, 1.0F, 1.0F, true)
    }
}