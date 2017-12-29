package com.cat

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.cat.LoadingHelper.HIDE
import com.cat.LoadingHelper.LOADFAIL
import com.cat.LoadingHelper.LOADING


class LoadingAdapterIMPL : LoadingAadpter {

    val tag = "loading_fragment"
    var mReload: Runnable? = null
    var containerId = 0
    var loadingLayout: ILoadingView? = null
    var dialogLayout: ILoadingView? = null
    var errorLayout: ILoadingView? = null
    var manager: FragmentManager? = null
    lateinit var fragment: FragmentIMPL
    var dialog: DialogIMPL? = null
    private var id: Long = System.currentTimeMillis()

    fun init(manager: FragmentManager, containerId: Int, reload: Runnable) {
        this.mReload = reload
        this.manager = manager
        this.containerId = containerId;
    }


    override fun updateReload(reload: Runnable) {
        this.mReload = reload
    }

    override fun updateLodingLayout(loading: ILoadingView) {
        this.loadingLayout = loading
    }

    override fun updateDialogLayout(dialogLayout: ILoadingView) {
        this.dialogLayout = dialogLayout
    }

    override fun updateErrorLayout(error: ILoadingView) {
        this.errorLayout = error
    }


    fun initFragment() {
        try {
            fragment = manager?.findFragmentByTag(tag) as FragmentIMPL
            if (fragment.mId != id) {
                manager?.beginTransaction()?.remove(fragment)?.commitAllowingStateLoss()
            }
        } catch (e: TypeCastException) {
            fragment = FragmentIMPL().init(this, loadingLayout, errorLayout, id)
            manager?.beginTransaction()?.add(containerId, fragment, tag)?.commitAllowingStateLoss()
        }

    }

    override fun showLoading() {
        initFragment()
        fragment?.mType = LOADING
        show()
    }

    override fun showLoaderr() {
        initFragment()
        fragment?.mType = LOADFAIL
        show()
    }


    override fun showDialogLoading() {
        if (dialogLayout != null) {
            dialog = DialogIMPL().init(this, dialogLayout, LOADING)
            dialog?.show(manager, "alert_loading")
        }
    }


    private fun show() {
        manager?.beginTransaction()?.show(fragment)?.commitAllowingStateLoss()
        fragment?.updateView()
    }

    override fun hide() {
        initFragment()
        manager?.beginTransaction()?.hide(fragment)?.commitAllowingStateLoss()
        dialog?.dismissAllowingStateLoss()
    }

    @SuppressLint("ValidFragment")
    class FragmentIMPL : Fragment() {
        var mLoadingView: View? = null
        var mErrorView: View? = null
        var loadingView: ILoadingView? = null
        var errorView: ILoadingView? = null
        var mType: Int = HIDE
        var reload: Runnable? = null
        var mId: Long = 0

        fun init(adapterIMPL: LoadingAdapterIMPL, loadingView: ILoadingView?, errorView: ILoadingView?, mId: Long): FragmentIMPL {
            this.loadingView = loadingView
            this.errorView = errorView
            this.reload = adapterIMPL.mReload
            this.mId = mId
            return this
        }


        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            var mView: ViewGroup
            if (inflater != null) {
                if (mLoadingView == null || mErrorView == null) {
                    this.mLoadingView = loadingView?.onCreateView(inflater.context)
                    this.mErrorView = errorView?.onCreateView(inflater.context)
                }
                mView = FrameLayout(inflater.context)
                if (mLoadingView?.parent is ViewGroup) {
                    (mLoadingView?.parent as ViewGroup).removeView(mLoadingView)
                }
                if (mErrorView?.parent is ViewGroup) {
                    (mErrorView?.parent as ViewGroup).removeView(mErrorView)
                }
                mLoadingView?.visibility = View.GONE
                mErrorView?.visibility = View.GONE
                mView.addView(mLoadingView)
                mView.addView(mErrorView)
                val metrics = resources.displayMetrics
                mView.minimumWidth = metrics.widthPixels
                mView.minimumHeight = metrics.heightPixels
                return mView
            }
            return null
        }

        override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            updateView()
            mErrorView?.setOnTouchListener { _, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_UP) {
                    reload?.run()
                }
                true
            }
        }

        fun updateView() {
            if (view !is View) {
                return
            }
            when (mType) {
                LOADING -> {
                    mLoadingView?.visibility = View.VISIBLE
                    mErrorView?.visibility = View.GONE
                }
                LOADFAIL -> {
                    mLoadingView?.visibility = View.GONE
                    mErrorView?.visibility = View.VISIBLE
                }
                else -> {
                    mLoadingView?.visibility = View.GONE
                    mErrorView?.visibility = View.GONE
                }
            }

        }
    }

    @SuppressLint("ValidFragment")
    class DialogIMPL : DialogFragment() {

        var mLayoutId: ILoadingView? = null
        var mType: Int = 0
        var reload: Runnable? = null
        fun init(adapterIMPL: LoadingAdapterIMPL, layout: ILoadingView?, type: Int): DialogIMPL {
            this.mLayoutId = layout
            this.reload = adapterIMPL.mReload
            this.mType = type
            return this
        }


        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            super.setCancelable(false)
            super.setStyle(DialogFragment.STYLE_NO_FRAME, 0)
            return super.onCreateDialog(savedInstanceState)
        }

        override fun onDestroy() {
            dismissAllowingStateLoss()
            super.onDestroy()
        }


        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            if (inflater != null && mLayoutId != null) {
                var view = mLayoutId?.onCreateView(inflater.context)
                val metrics = resources.displayMetrics
                view?.minimumWidth = metrics.widthPixels
                view?.minimumHeight = metrics.heightPixels
                if (mType == LoadingHelper.LOADFAIL) {
                    view?.setOnTouchListener { _, motionEvent ->
                        if (motionEvent.action == MotionEvent.ACTION_UP) {
                            if (reload != null) {
                                reload!!.run()
                            } else {
                                dismissAllowingStateLoss()
                            }
                        }
                        true
                    }
                }
                return view
            }
            return null
        }

    }
}