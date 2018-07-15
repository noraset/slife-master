package com.singhaestate.slife.ui.mainmenu.shoplist

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class ShopListPageAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment = if (position == 0) {
        ShopFragment.newInstance(ListType.Shop)
    } else {
        ShopFragment.newInstance(ListType.Restaurant)
    }

    override fun getCount(): Int = 2

}