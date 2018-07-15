package com.singhaestate.slife.ui.login

import android.os.Bundle
import com.facebook.AccessToken
import com.singhaestate.slife.R
import com.singhaestate.slife.ui.base.BaseActivity


class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired

        if (savedInstanceState == null)
            supportFragmentManager.findFragmentById(R.id.contentContainer)
                    as LoginFragment? ?: LoginFragment.newInstance().also {
                addFragmentToActivity(it, R.id.contentContainer, "login")
            }
    }
}
