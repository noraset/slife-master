package com.singhaestate.slife

import timber.log.Timber


class NotLoggingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {}
}