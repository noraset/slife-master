package com.singhaestate.slife.util

import android.annotation.SuppressLint
import android.content.Context

/**
 * Created by Nut on 03/26/18.
 */
@SuppressLint("StaticFieldLeak")
object Contextor {
    lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }
}