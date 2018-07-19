package com.singhaestate.slife

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.facebook.appevents.AppEventsLogger
import com.singhaestate.slife.util.Contextor
import com.singhaestate.slife.util.Foreground
import timber.log.Timber


class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        else
            Timber.plant(NotLoggingTree())


        AppEventsLogger.activateApp(this)
        Contextor.init(this)
        Foreground.init(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}