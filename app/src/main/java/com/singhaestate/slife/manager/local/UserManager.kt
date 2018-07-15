package com.singhaestate.slife.manager.local

object UserManager {

    var isUserLogin: Boolean = false
        get() = UserManager.user != null

    var user: UserModel?
        get() = SharePrefManager.getUserFromPref()
        set(user) = SharePrefManager.setUserToPref(user)

    var notif: Boolean
        get() = SharePrefManager.getBooleanFromPref("notif", default = true)
        set(notif) = SharePrefManager.saveBooleanToPref("notif", notif)
}