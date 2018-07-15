package com.singhaestate.slife.util

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.singhaestate.slife.R

/**
 * Created by Nut on 03/26/18.
 */
@SuppressLint("StaticFieldLeak")
object DialogHelper {

    private var alertDialog: MaterialDialog? = null
    private var loadingDialog: MaterialDialog? = null

    fun showProgressDialog(context: Context) {
        if (loadingDialog != null)
            loadingDialog!!.dismiss()
        loadingDialog = MaterialDialog.Builder(context)
                .content(context.getString(R.string.waiting))
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .cancelable(false)
                .show()
    }

    fun hideProgressDialog() {
        if (loadingDialog != null && loadingDialog!!.isShowing) {
            loadingDialog!!.dismiss()
        }
    }

    fun openBasicDialog(context: Context, detail: String, callback: () -> Unit) {
        if (alertDialog != null)
            alertDialog!!.dismiss()
        alertDialog = MaterialDialog.Builder(context)
                .content(detail)
                .positiveText(R.string.btn_ok)
                .cancelable(false)
                .onPositive { _, _ ->
                    callback.invoke()
                }
                .build()
        alertDialog!!.show()
    }

    fun openTwoWayDialog(context: Context, detail: String, callback: DialogCallback?) {
        if (alertDialog != null)
            alertDialog!!.dismiss()
        alertDialog = MaterialDialog.Builder(context)
                .content(detail)
                .positiveText(R.string.btn_ok)
                .negativeText(R.string.btn_cancel)
                .positiveColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .negativeColor(ContextCompat.getColor(context, R.color.colorAccent))
                .cancelable(false)
                .onPositive { _, _ ->
                    callback?.onClickOK()
                }
                .build()
        alertDialog!!.show()
    }

    fun openListDialog(context: Context, listRes: Int, callback: DialogCallbackItemSelected?) {
        if (alertDialog != null)
            alertDialog?.dismiss()

        alertDialog = MaterialDialog.Builder(context)
                .items(listRes)
                .itemsCallback { _, _, position, text ->
                    callback?.onItemSelected(position, text)
                }
                .show()
    }


    interface DialogCallback {
        fun onClickOK()
    }

    interface DialogCallbackItemSelected {
        fun onItemSelected(which: Int, text: CharSequence)
    }
}