package com.wang.mytest.feature.ui.view_pager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentStateAdapterNormal(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        TODO("not implemented")
    }

    override fun createFragment(position: Int): Fragment {
        TODO("not implemented")
    }
}