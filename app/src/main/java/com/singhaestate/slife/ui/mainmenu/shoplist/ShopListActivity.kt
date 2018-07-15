package com.singhaestate.slife.ui.mainmenu.shoplist

import android.os.Bundle
import com.singhaestate.slife.R
import com.singhaestate.slife.ui.base.BaseActivity

class ShopListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_list)

        if (savedInstanceState == null)
            supportFragmentManager.findFragmentById(R.id.contentContainer)
                    as ShopListFragment? ?: ShopListFragment.newInstance().also {
                addFragmentToActivity(it, R.id.contentContainer, "privilege_favorite")
            }
    }
}
