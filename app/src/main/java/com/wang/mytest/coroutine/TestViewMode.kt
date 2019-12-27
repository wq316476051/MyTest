package com.wang.mytest.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch

class TestViewMode : ViewModel() {

    init {
        viewModelScope.launch {

        }
    }
}