package com.cat

/**
 * Created by lee on 2017/12/7.
 */
interface LoadingAadpter {

    // 更新网络失败的点击事件
    fun updateReload(reload: Runnable)

    // 更新ui
    fun updateLodingLayout(loading: ILoadingView)

    fun updateDialogLayout(dialog: ILoadingView)

    fun updateErrorLayout(error: ILoadingView)

    fun showLoading()

    fun showDialogLoading()

    fun showLoaderr()

    fun hide()

}