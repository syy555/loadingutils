package com.cat

import android.content.Context
import android.view.View

/**
 * Created by lee on 2017/12/8.
 */
open interface ILoadingView {
    fun onCreateView(context: Context?): View
}