package com.cat

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

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