package com.wang.mytest.ui.view.search_view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.wang.mytest.apt.annotation.Route
import com.wang.mytest.R

/**
 * SearchView 的基本属性
 * SearchView 的基本使用
 * SearchView 结合Menu的使用
 */
@Route(path = "/activity/ui/search_view", title = "SearchView")
class SearchViewActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SearchViewActivity"
    }

    private lateinit var toolbar: Toolbar
    private lateinit var searchView: SearchView
    private lateinit var btnCloseSearch: ImageButton
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_search_view)

        toolbar = findViewById<Toolbar>(R.id.toolbar).apply {
            setSupportActionBar(this)
        }
        searchView = findViewById<SearchView>(R.id.search_view).apply {
            // isIconified = false // 设置搜索框直接展开显示。左侧有放大镜(在搜索框中) 右侧有叉叉 可以关闭搜索框
            setIconifiedByDefault(false) // 设置搜索框直接展开显示。左侧有放大镜(在搜索框外) 右侧无叉叉 有输入内容后有叉叉 不能关闭搜索框
            // onActionViewExpanded() // 设置搜索框直接展开显示。左侧有放大镜(在搜索框中) 右侧无叉叉 有输入内容后有叉叉 不能关闭搜索框

            imeOptions = EditorInfo.IME_ACTION_SEARCH
            inputType = EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
            maxWidth = 2000
            // findViewById<TextView>(R.id.search_src_text).clearFocus()
            queryHint = "Search"

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean { // 当搜索内容改变时触发该方法
                    Log.d(TAG, "onQueryTextChange: $newText");
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean { // 当点击搜索按钮时触发该方法
                    Log.d(TAG, "onQueryTextSubmit: $query");
                    return true
                }
            })
        }
        btnCloseSearch = findViewById<ImageButton>(R.id.btn_close_search).apply {
            setOnClickListener {
                if (searchView.hasFocus()) {
                    searchView.clearFocus()
                }
            }
        }
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.apply { menuInflater.inflate(R.menu.search_view, this) }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.options_search -> {
                true
            }
            R.id.options_scan_local_music -> {
                true
            }
            R.id.options_select_sort_way -> {
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}