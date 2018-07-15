package com.singhaestate.slife.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import java.util.concurrent.CopyOnWriteArrayList


/**
 * Usage:
 *
 * 1. Get the Foreground Singleton, passing a Context or Application object unless you
 * are sure that the Singleton has definitely already been initialised elsewhere.
 *
 * 2.a) Perform a direct, synchronous check: Foreground.isForeground() / .isBackground()
 *
 * or
 *
 * 2.b) Register to be notified (useful in Service or other non-UI components):
 *
 *   Foreground.Listener myListener = new Foreground.Listener(){
 *       public void onBecameForeground(){
 *           // ... whatever you want to do
 *       }
 *       public void onBecameBackground(){
 *           // ... whatever you want to do
 *       }
 *   }
 *
 *   public void onCreate(){
 *      super.onCreate();
 *      Foreground.get(this).addListener(listener);
 *   }
 *
 *   public void onDestroy(){
 *      super.onCreate();
 *      Foreground.get(this).removeListener(listener);
 *   }
 */
class Foreground : Application.ActivityLifecycleCallbacks {

    companion object {
        val CHECK_DELAY: Long = 500
        val TAG = Foreground::class.java.name
        private var instance: Foreground? = null

        fun init(application: Application): Foreground {
            if (instance == null) {
                instance = Foreground()
                application.registerActivityLifecycleCallbacks(instance)
            }
            return instance as Foreground
        }

        fun get(application: Application): Foreground {
            if (instance == null) {
                init(application)
            }
            return instance as Foreground
        }

        fun get(context: Context): Foreground {
            if (instance == null) {
                val appCtx = context.applicationContext
                if (appCtx is Application) {
                    init(appCtx)
                }
                throw IllegalStateException(
                        "Foreground is not initialised and " + "cannot obtain the Application object")
            }
            return instance as Foreground
        }

        fun get(): Foreground {
            if (instance == null) {
                throw IllegalStateException(
                        "Foreground is not initialised - invoke " +
                                "at least once with parameterised init/get");
            }
            return instance as Foreground
        }
    }

    interface Listener {
        fun onBecameForeground()
        fun onBecameBackground()
    }

    private var foreground = false
    private var paused = true
    private val handler = Handler()
    private val listeners = CopyOnWriteArrayList<Listener>()
    private val check: Runnable? = null

    fun isForeground(): Boolean {
        return foreground
    }

    fun isBackground(): Boolean {
        return !foreground
    }

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }


    override fun onActivityPaused(p0: Activity?) {
        paused = true

        if (check != null)
            handler.removeCallbacks(check)

        handler.postDelayed({
            if (foreground && paused) {
                foreground = false
                Log.i(TAG, "went background")
                for (l in listeners) {
                    try {
                        l.onBecameBackground()
                    } catch (exc: Exception) {
                        Log.e(TAG, "Listener threw exception!", exc)
                    }

                }
            } else {
                Log.i(TAG, "still foreground")
            }
        }, CHECK_DELAY)
    }

    override fun onActivityResumed(p0: Activity?) {
        paused = false
        val wasBackground = !foreground
        foreground = true

        if (check != null)
            handler.removeCallbacks(check)

        if (wasBackground) {
            Log.i(TAG, "went foreground")
            for (l in listeners) {
                try {
                    l.onBecameForeground()
                } catch (exc: Exception) {
                    Log.e(TAG, "Listener threw exception!", exc)
                }

            }
        } else {
            Log.i(TAG, "still foreground")
        }
    }

    override fun onActivityStarted(p0: Activity?) {
    }

    override fun onActivityDestroyed(p0: Activity?) {
    }

    override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
    }

    override fun onActivityStopped(p0: Activity?) {
    }

    override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
    }

}