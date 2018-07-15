package com.singhaestate.slife.ui.base

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.singhaestate.slife.util.DialogHelper
import com.singhaestate.slife.util.setupActionBar
import com.singhaestate.slife.util.transact
import org.jetbrains.anko.bundleOf

open class BaseActivity : AppCompatActivity() {

    internal fun setupToolbar(toolbar: Toolbar, customTitle: String) {
        toolbar.also {
            setupActionBar(it) {
                setDisplayShowTitleEnabled(false)
//                toolbarTitle.text = customTitle
//                btnToolbarBack.setOnClickListener { onBackPressed() }
            }
        }
    }

    internal fun setLoadingIndicator(active: Boolean) {
        if (active)
            DialogHelper.showProgressDialog(this)
        else
            DialogHelper.hideProgressDialog()
    }

    internal fun AppCompatActivity.addFragmentToActivity(fragment: Fragment, targetResId: Int, tag: String) {
        supportFragmentManager.transact {
            add(targetResId, fragment, tag)
        }
    }

    inline fun <reified T : Fragment> newFragmentInstance(vararg params: Pair<String, Any>) = T::class.java.newInstance().apply {
        arguments = bundleOf(*params)
    }


}