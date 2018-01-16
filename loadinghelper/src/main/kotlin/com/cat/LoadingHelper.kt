package com.cat

import android.content.Context
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cat.loadinghelper.R


object LoadingHelper {

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


    fun LoadingHelper(manager: FragmentManager, view: ViewGroup, reload: Runnable?): LoadingAadpter {
        val adapter = LoadingAdapterIMPL()
        adapter.init(manager, view, reload,defaultLoadingId,defaultDialogId,defaultErrorId,defaultNetErrorId)
        return adapter
    }
}