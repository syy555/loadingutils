package com.cat


interface LoadingAadpter {

    fun updateDialogLayout(dialog: ILoadingView)

    fun showLoading()

    fun hide()

}