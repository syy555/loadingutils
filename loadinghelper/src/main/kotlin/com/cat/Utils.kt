package com.cat

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v4.app.FragmentActivity

/**
 * Created by lee on 2017/12/7.
 * 判断网络是否可用.
 *
 * @param context 上下文
 * @return true代表网络可用，false代表网络不可用.
 */
fun isNetworkValid(context: Context?): Boolean {
    var result = false

    if (context == null) {
        return false
    } else {
        val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var info: NetworkInfo? = null

        info = connectivity.activeNetworkInfo

        if (info == null) {
            result = false
        } else {
            if (info.isAvailable) {
                result = true
            }
        }
    }
    return result
}

fun castToFragmentActivity(context: Context): FragmentActivity {
    var context = context
    while (context is ContextWrapper) {
        if (context is FragmentActivity) {
            return context
        }
        context = context.baseContext
    }
    throw IllegalArgumentException("Not an FragmentActivity or a wrapper with Activity.")
}