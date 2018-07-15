package com.singhaestate.slife.ui.mainmenu.notification

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.singhaestate.slife.ui.mainmenu.privilege.PrivilegeListFragment

class PrivilegePageAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment = if (position == 0) {
        PrivilegeListFragment.newInstance()
    } else {
        PrivilegeListFragment.newInstance()
    }

    override fun getCount(): Int = 2

}