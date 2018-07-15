package com.singhaestate.slife.util

import android.annotation.SuppressLint
import android.app.Activity
import android.support.design.widget.Snackbar
import android.widget.TextView
import org.jetbrains.anko.contentView


@SuppressLint("ShowToast")
object ToastDialog {
    fun showToast(string: String, activity: Activity) {
        val snackBar = Snackbar.make(activity.contentView!!, string, Snackbar.LENGTH_SHORT)
//        val tv = snackBar.view.findViewById(android.support.design.R.id.snackbar_text) as TextView
        snackBar.show()
    }

    fun showLongToast(string: String, activity: Activity) {
        val snackBar = Snackbar.make(activity.contentView!!, string, Snackbar.LENGTH_LONG)
//        val tv = snackBar.view.findViewById(android.support.design.R.id.snackbar_text) as TextView
        snackBar.show()
    }
}