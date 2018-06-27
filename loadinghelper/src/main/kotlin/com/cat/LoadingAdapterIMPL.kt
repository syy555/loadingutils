package com.cat

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.cat.LoadingFactory.LOADING


class LoadingAdapterIMPL : LoadingAdapter {

    var mReload: Runnable? = null
    var dialogLayout: ILoadingView? = null
    lateinit var manager: FragmentManager
    var dialog: DialogIMPL? = null

    fun init(context: Context, dialogLayout: ILoadingView) {
        this.manager = castToFragmentActivity(context).supportFragmentManager
        this.dialogLayout = dialogLayout
    }


    override fun updateDialogLayout(dialogLayout: ILoadingView) {
        this.dialogLayout = dialogLayout
    }


    override fun showLoading() {
        hide()
        if (dialogLayout != null) {
            dialog = DialogIMPL().init(this, dialogLayout, LOADING)
            dialog?.show(manager, "alert_loading")
        }
    }



    override fun hide() {
        dialog?.dismissAllowingStateLoss()
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

        override fun show(manager: FragmentManager?, tag: String?) {
            try {
                super.show(manager, tag)
            } catch (ignore: IllegalStateException) {
            }
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
                if (mType == LoadingFactory.LOADFAIL) {
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