package com.singhaestate.slife.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.view.inputmethod.InputMethodManager
import com.singhaestate.slife.R
import java.text.DecimalFormat
import java.util.*


/**
 * Created by Nut on 11/23/17.
 */
object Utils {
    val commaFormat: DecimalFormat
        get() = DecimalFormat("#,###,###")

    fun getNumberCommaFormat(number: Double): String =
            DecimalFormat("#,###,###").format(number) + "฿"

    fun getNumberCommaTwoDigitFormat(number: Double): String =
            DecimalFormat("#,###,##0.00").format(number) + "฿"

    val isInternetAvailable: Boolean
        get() {
            val connectivityManager = Contextor.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = connectivityManager.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }

    inline fun <reified T> getJsonGenericKotlin(jsonObject: String): T {

        return GsonHelper.getGson().fromJson(jsonObject, T::class.java)
    }

    inline fun getAppVersion(context: Context): String {
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            return pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return ""
    }

    @Suppress("DEPRECATION")
    inline fun getLocale(context: Context): String {
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            context.resources.configuration.locale
        }

        return if (locale == Locale.US) {
            "EN"
        } else {
            "TH"
        }
    }


    internal fun isAlphaNumreic(str: String): Boolean {
        /*
        .*[A-Za-z].*   check for the presence of at least one letter
        .*[0-9].*      check for the presence of at least one number
        [A-Za-z0-9]*   check that only numbers and letters compose this string
         */
        return str.matches(".*[A-Za-z].*".toRegex()) || str.matches(".*[0-9].*".toRegex()) || str.matches("[A-Za-z0-9]*".toRegex())
    }

    internal fun showErrorDialog(network: Boolean, activity: Activity) {
        if (network) {
            ToastDialog.showToast(Contextor.context.getString(R.string.network_error), activity)
        } else {
            ToastDialog.showToast(Contextor.context.getString(R.string.system_error), activity)
        }
    }

    internal fun hideKeyboard(context: Context?) {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

}