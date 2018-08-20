package com.cat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cat.loadinghelper.R


object LoadingFactory {

    val HIDE = 0
    val LOADING = 1
    val LOADFAIL = 2
    var defaultLoadingId: ILoadingView = object : ILoadingView {

        override fun onCreateView(context: Context?): View {
            return LayoutInflater.from(context).inflate(R.layout.view_default_loading, null)
        }
    }

    var defaultDialogId: ILoadingView = object : ILoadingView {

        override fun onCreateView(context: Context?): View {
            return LayoutInflater.from(context).inflate(R.layout.view_default_loading, null)
        }


    }

    var defaultErrorId: ILoadingView = object : ILoadingView {

        override fun onCreateView(context: Context?): View {
            return LayoutInflater.from(context).inflate(R.layout.view_default_error, null)
        }
    }

    var defaultNetErrorId: ILoadingView = object : ILoadingView {

        override fun onCreateView(context: Context?): View {
            return LayoutInflater.from(context).inflate(R.layout.view_default_error, null)
        }
    }


    @JvmOverloads
    fun create(context:Context,dialogId:ILoadingView = defaultDialogId): LoadingAdapter {
        val adapter = LoadingAdapterIMPL()
        adapter.init(context,dialogId)
        return adapter
    }

    @JvmOverloads
    fun create(view: ViewGroup, reload: Runnable?,loadingid:ILoadingView = defaultLoadingId,errorId:ILoadingView = defaultErrorId,netErrorId:ILoadingView = defaultNetErrorId): PlaceHolderAdapter {
        val adapter = PlaceHolderAdapterIMPL()
        adapter.init(view, reload,loadingid,errorId,netErrorId)
        return adapter
    }
}