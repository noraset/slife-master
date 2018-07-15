package com.singhaestate.slife.ui.mainmenu.privilege.favorite

import android.os.Bundle
import com.singhaestate.slife.R
import com.singhaestate.slife.ui.base.BaseActivity

class PrivilegeFavoriteActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privilege_favorite)

        if (savedInstanceState == null)
            supportFragmentManager.findFragmentById(R.id.contentContainer)
                    as PrivilegeFavoriteFragment? ?: PrivilegeFavoriteFragment.newInstance().also {
                addFragmentToActivity(it, R.id.contentContainer, "privilege_favorite")
            }
    }
}
