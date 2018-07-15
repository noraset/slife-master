package com.singhaestate.slife.ui.mainmenu

import android.os.Bundle
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.singhaestate.slife.R
import com.singhaestate.slife.util.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_main_menu.*
import q.rorbin.badgeview.QBadgeView

class MainMenuActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment? = null
        when (item.itemId) {
            R.id.navigation_home -> {
                selectedFragment = HomeFragment.newInstance()
            }
            R.id.navigation_privilege -> {
                selectedFragment = PrivilegeFragment.newInstance()
            }
            R.id.navigation_notification -> {
                selectedFragment = NotificationFragment.newInstance()
            }
            R.id.navigation_profile -> {
                selectedFragment = ProfileFragment.newInstance()
            }
        }

        if (selectedFragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.contentContainer, selectedFragment)
            transaction.commit()
        }
        return@OnNavigationItemSelectedListener true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        BottomNavigationViewHelper.disableShiftMode(navigation)

        navigation.selectedItemId = R.id.navigation_home
        setBadge()
    }

    private fun setBadge() {
        val menuView = navigation.getChildAt(0) as BottomNavigationMenuView
        val view = menuView.getChildAt(2)
        val badge = QBadgeView(this).bindTarget(view)
        badge.badgeBackgroundColor = ContextCompat.getColor(this, R.color._ff6200)
        badge.badgeText = " "
    }
}
