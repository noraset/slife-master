package com.singhaestate.slife.ui.mainmenu.notification.detail

import android.os.Bundle
import com.singhaestate.slife.R
import com.singhaestate.slife.ui.base.BaseActivity

class NotificationDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_detail)

        if (savedInstanceState == null)
            supportFragmentManager.findFragmentById(R.id.contentContainer)
                    as NotificationDetailFragment?
                    ?: NotificationDetailFragment.newInstance("1", "2").also {
                        addFragmentToActivity(it, R.id.contentContainer, "notification_detail")
                    }
    }
}
