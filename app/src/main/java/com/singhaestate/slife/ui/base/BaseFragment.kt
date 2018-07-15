package com.singhaestate.slife.ui.base

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.singhaestate.slife.R
import com.singhaestate.slife.util.DialogHelper
import com.singhaestate.slife.util.setupActionBar
import com.singhaestate.slife.util.transact


/**
 * Created by natsu on 3/15/18.
 */
open class BaseFragment : Fragment() {

    internal fun setupToolbar(toolbar: Toolbar, customTitle: String) {
        (activity as AppCompatActivity).apply {
            toolbar.also {
                setupActionBar(it) {
                    setDisplayShowTitleEnabled(false)
//                    toolbarTitle.text = customTitle
//                    btnToolbarBack.setOnClickListener { activity.onBackPressed() }
                }
            }
        }
    }

    internal fun setLoadingIndicator(active: Boolean) {
        if (isAdded) {
            if (active)
                activity?.let { DialogHelper.showProgressDialog(it) }
            else
                DialogHelper.hideProgressDialog()
        }
    }

    internal fun replaceFragmentWithAnimation(fragment: Fragment, targetResId: Int, tag: String) {
        (activity as AppCompatActivity).apply {
            supportFragmentManager.transact {
                setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.my_pop_enter, R.anim.my_pop_exit)
                replace(targetResId, fragment, tag)
                addToBackStack(null)
            }
        }
    }

}