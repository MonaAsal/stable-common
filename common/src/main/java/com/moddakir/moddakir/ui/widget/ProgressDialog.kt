package com.moddakir.moddakir.ui.widget

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import com.example.moddakirapps.R
import com.wang.avi.AVLoadingIndicatorView

class ProgressDialog {
    private var dialog: Dialog? = null
    var mInstance: ProgressDialog? = null
    var isCancel: Boolean = false
    private var spin_kit: AVLoadingIndicatorView? = null
    fun getCancelable(): Boolean {
        return isCancel
    }

    fun setCancelable(cancelable: Boolean) {
        isCancel = cancelable

    }
    fun getInstance(): ProgressDialog? {
        if (mInstance == null) {
            mInstance =
                ProgressDialog()
        }
        return mInstance
    }

    fun show(context: Context?) {
        dialog = Dialog(context!!, R.style.CustomDialog)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.loading_dialog_progress)
        spin_kit = dialog?.findViewById<AVLoadingIndicatorView>(R.id.spin_kit)
        spin_kit?.visibility = View.VISIBLE
        dialog?.setCancelable(false)
        dialog?.show()
    }

    fun dismiss() {
        if (dialog != null && dialog!!.isShowing) {
            spin_kit?.visibility = View.GONE
            dialog?.dismiss()
        }
    }
}