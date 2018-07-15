package com.singhaestate.slife.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.singhaestate.slife.R
import com.singhaestate.slife.ui.base.BaseActivity
import com.singhaestate.slife.ui.login.LoginActivity

class SplashScreenActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({


            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }, 2000)
    }

}
