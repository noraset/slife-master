package com.singhaestate.slife.ui.register

import android.os.Bundle
import com.singhaestate.slife.R
import com.singhaestate.slife.ui.base.BaseActivity

class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        if (savedInstanceState == null)
            supportFragmentManager.findFragmentById(R.id.contentContainer)
                    as RegisterFragment? ?: RegisterFragment.newInstance().also {
                addFragmentToActivity(it, R.id.contentContainer, "login")
            }
    }
}
