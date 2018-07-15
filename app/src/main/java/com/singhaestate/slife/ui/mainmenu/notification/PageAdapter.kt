package com.singhaestate.slife.ui.mainmenu.notification

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class PageAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment = if (position == 0) {
        YourNotificationFragment.newInstance()
    } else {
        NewsFragment.newInstance()
    }

    override fun getCount(): Int = 2

}