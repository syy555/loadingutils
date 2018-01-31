package com.cat


interface PlaceHolderAadpter {

    // 更新网络失败的点击事件
    fun updateReload(reload: Runnable)

    // 更新ui
    fun updateLodingLayout(loading: ILoadingView)


    fun updateErrorLayout(error: ILoadingView)

    fun updateNetErrorLayout(error: ILoadingView)

    fun showLoading()

    fun showLoaderr()

    fun hide()

}