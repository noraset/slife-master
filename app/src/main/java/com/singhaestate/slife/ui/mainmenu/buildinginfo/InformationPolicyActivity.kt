package com.singhaestate.slife.ui.mainmenu.buildinginfo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.singhaestate.slife.R
import kotlinx.android.synthetic.main.activity_information_policy.*

class InformationPolicyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_policy)

        init()
    }

    private fun init() {
        rootLayout.requestFocus()
        toolbar.setNavigationIcon(R.drawable.ic_close)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar_title.text = "Information"
    }
}
