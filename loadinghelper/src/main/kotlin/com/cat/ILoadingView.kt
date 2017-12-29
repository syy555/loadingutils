package com.cat

import android.content.Context
import android.view.View


interface ILoadingView {
    fun onCreateView(context: Context?): View
}