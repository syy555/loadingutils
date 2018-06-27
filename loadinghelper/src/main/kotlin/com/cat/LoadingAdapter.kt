package com.cat


interface LoadingAdapter {

    fun updateDialogLayout(dialog: ILoadingView)

    fun showLoading()

    fun hide()

}