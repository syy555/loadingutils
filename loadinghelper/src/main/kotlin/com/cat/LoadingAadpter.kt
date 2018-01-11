package com.cat


interface LoadingAadpter {

    // 更新网络失败的点击事件
    fun updateReload(reload: Runnable)

    // 更新ui
    fun updateLodingLayout(loading: ILoadingView)

    fun updateDialogLayout(dialog: ILoadingView)

    fun updateErrorLayout(error: ILoadingView)

    fun updateNetErrorLayout(error: ILoadingView)

    fun showLoading()

    fun showDialogLoading()

    fun showLoaderr()

    fun hide()

}