package com.wang.mytest.feature.storage.database

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.wang.mytest.feature.storage.R
import com.wang.mytest.feature.storage.database.table.Label

class DatabaseInsertFragment : Fragment() {

    private lateinit var appBarLayout: AppBarLayout
    private lateinit var toolbar: Toolbar
    private lateinit var etFilePath: EditText
    private lateinit var etContent: EditText
    private lateinit var etTime: EditText
    private lateinit var btnSubmit: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_database_insert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appBarLayout = view.findViewById<AppBarLayout>(R.id.app_bar_layout).apply {
            this.setExpanded(true, true)
        }
        toolbar = view.findViewById<Toolbar>(R.id.toolbar).apply {
            title = "Insert into database"
        }
        etFilePath = view.findViewById(R.id.et_file_path)
        etContent = view.findViewById(R.id.et_content)
        etTime = view.findViewById(R.id.et_time)
        btnSubmit = view.findViewById<Button>(R.id.btn_submit).apply {
            setOnClickListener {
                val filePath = etFilePath.text?.toString()
                if (filePath.isNullOrEmpty()) {
                    Snackbar.make(view, "file path is null or empty", Snackbar.LENGTH_SHORT)
                            .setAction("OK", View.OnClickListener { /* do nothing */ })
                            .show()
                    return@setOnClickListener
                }
                val content = etContent.text?.toString()
                if (content.isNullOrEmpty()) {
                    Snackbar.make(view, "content is null or empty", Snackbar.LENGTH_SHORT)
                            .setAction("OK", View.OnClickListener { /* do nothing */ })
                            .show()
                    return@setOnClickListener
                }
                val time = etTime.text?.toString()
                if (time.isNullOrEmpty()) {
                    Snackbar.make(view, "time is null or empty", Snackbar.LENGTH_SHORT)
                            .setAction("OK", View.OnClickListener { /* do nothing */ })
                            .show()
                    return@setOnClickListener
                }

                context.contentResolver.insert(LABEL_URI, ContentValues().apply {
                    put(Label.FILE_PATH, filePath)
                    put(Label.CONTENT, content)
                    put(Label.TIME, time)
                })
            }
        }
    }
}