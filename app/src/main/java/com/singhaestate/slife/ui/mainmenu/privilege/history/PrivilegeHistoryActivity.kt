package com.singhaestate.slife.ui.mainmenu.privilege.history

import android.os.Bundle
import com.singhaestate.slife.R
import com.singhaestate.slife.ui.base.BaseActivity

class PrivilegeHistoryActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privilege_history)

        if (savedInstanceState == null)
            supportFragmentManager.findFragmentById(R.id.contentContainer)
                    as PrivilegeHistoryFragment? ?: PrivilegeHistoryFragment.newInstance().also {
                addFragmentToActivity(it, R.id.contentContainer, "privilege_history")
            }
    }
}
