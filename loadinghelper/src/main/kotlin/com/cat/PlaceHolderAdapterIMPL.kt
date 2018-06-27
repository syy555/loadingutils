package com.cat

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.cat.LoadingFactory.HIDE
import com.cat.LoadingFactory.LOADFAIL
import com.cat.LoadingFactory.LOADING
import com.cat.loadinghelper.R


class PlaceHolderAdapterIMPL : PlaceHolderAdapter {

    var mReload: Runnable? = null
    lateinit var container: ViewGroup
    var loadingLayout: ILoadingView? = null
    var errorLayout: ILoadingView? = null
    var netErrorLayout: ILoadingView? = null
    lateinit var viewHolder: ViewIMPL

    fun init(view: ViewGroup, reload: Runnable?, loading: ILoadingView, error: ILoadingView, netError: ILoadingView) {
        this.mReload = reload
        this.container = view
        this.loadingLayout = loading
        this.errorLayout = error
        this.netErrorLayout = netError
        initviewHolder()
    }

    override fun updateReload(reload: Runnable) {
        this.mReload = reload
        viewHolder.reload = reload
    }

    override fun updateLodingLayout(loading: ILoadingView) {
        this.loadingLayout = loading
        initviewHolder()
    }

    override fun updateErrorLayout(error: ILoadingView) {
        this.errorLayout = error
        initviewHolder()
    }

    override fun updateNetErrorLayout(error: ILoadingView) {
        this.netErrorLayout = error
        initviewHolder()
    }


    fun initviewHolder() {
        try {
            var view =  container.findViewById(R.id.loading_utils_view_holder) as View
            container.removeView(view)
        } catch (e: Exception) {
        }
        viewHolder = ViewIMPL().init(container.context, this, loadingLayout, errorLayout, netErrorLayout)
        viewHolder.mView.id = R.id.loading_utils_view_holder
        container.addView(viewHolder.mView)
    }

    override fun showLoading() {
        viewHolder.mType = LOADING
        viewHolder.updateView()
    }

    override fun showLoaderr() {
        viewHolder.mType = LOADFAIL
        viewHolder.updateView()
    }




    override fun hide() {
        viewHolder.mType = HIDE
        viewHolder.updateView()
    }

    @SuppressLint("ValidviewHolder")
    class ViewIMPL() {
        var mLoadingView: View? = null
        var mErrorView: View? = null
        var mNetErrorView: View? = null
        var loadingView: ILoadingView? = null
        var errorView: ILoadingView? = null
        var netErrorView: ILoadingView? = null
        var mType: Int = HIDE
        var reload: Runnable? = null
        lateinit var mView: ViewGroup

        fun init(context: Context, adapterIMPL: PlaceHolderAdapterIMPL, loadingView: ILoadingView?, errorView: ILoadingView?, netErrorView: ILoadingView?): ViewIMPL {
            this.loadingView = loadingView
            this.errorView = errorView
            this.netErrorView = netErrorView
            this.reload = adapterIMPL.mReload
            var inflater = LayoutInflater.from(context)
            if (mLoadingView == null || mErrorView == null || mNetErrorView == null) {
                this.mLoadingView = loadingView?.onCreateView(inflater.context)
                this.mErrorView = errorView?.onCreateView(inflater.context)
                this.mNetErrorView = netErrorView?.onCreateView(inflater.context)
            }
            mView = FrameLayout(inflater.context)
            if (mLoadingView?.parent is ViewGroup) {
                (mLoadingView?.parent as ViewGroup).removeView(mLoadingView)
            }
            if (mErrorView?.parent is ViewGroup) {
                (mErrorView?.parent as ViewGroup).removeView(mErrorView)
            }
            if (mNetErrorView?.parent is ViewGroup) {
                (mNetErrorView?.parent as ViewGroup).removeView(mNetErrorView)
            }
            mLoadingView?.visibility = View.GONE
            mErrorView?.visibility = View.GONE
            mNetErrorView?.visibility = View.GONE
            mView.addView(mLoadingView)
            mView.addView(mErrorView)
            mView.addView(mNetErrorView)
            val metrics = context.resources.displayMetrics
            mView.minimumWidth = metrics.widthPixels
            mView.minimumHeight = metrics.heightPixels
            return this
        }

        fun updateView() {
            mErrorView?.setOnClickListener {  reload?.run() }
            mNetErrorView?.setOnClickListener {  reload?.run() }
            when (mType) {
                LOADING -> {
                    mLoadingView?.visibility = View.VISIBLE
                    mErrorView?.visibility = View.GONE
                    mNetErrorView?.visibility = View.GONE
                }
                LOADFAIL -> {
                    mLoadingView?.visibility = View.GONE
                    if (isNetworkValid(mView.context)) {
                        mErrorView?.visibility = View.VISIBLE
                        mNetErrorView?.visibility = View.GONE
                    } else {
                        mErrorView?.visibility = View.GONE
                        mNetErrorView?.visibility = View.VISIBLE
                    }

                }
                else -> {
                    mLoadingView?.visibility = View.GONE
                    mErrorView?.visibility = View.GONE
                    mNetErrorView?.visibility = View.GONE
                }
            }

        }
    }
}