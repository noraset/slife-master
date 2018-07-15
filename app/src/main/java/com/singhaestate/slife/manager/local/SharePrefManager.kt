package com.singhaestate.slife.manager.local

import android.content.Context
import android.content.SharedPreferences
import androidx.content.edit
import com.google.gson.Gson
import com.singhaestate.slife.R
import com.singhaestate.slife.util.Contextor

object SharePrefManager {

    private val sharedPref: SharedPreferences by lazy {
        Contextor.context.getSharedPreferences(Contextor.context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    }

    fun saveStringToPref(key: String, value: String) {
        sharedPref.edit { putString(key, value) }
    }

    fun getStringFromPref(key: String): String = sharedPref.getString(key, "")

    fun getStringFromPref(key: String, default: String): String = sharedPref.getString(key, default)

    fun saveIntToPref(key: String, value: Int) {
        sharedPref.edit { putInt(key, value) }
    }

    fun getIntFromPref(key: String): Int = sharedPref.getInt(key, 0)

    fun saveBooleanToPref(key: String, status: Boolean) {
        sharedPref.edit { putBoolean(key, status) }
    }

    fun getBooleanFromPref(key: String): Boolean = sharedPref.getBoolean(key, false)

    fun getBooleanFromPref(key: String, default: Boolean): Boolean = sharedPref.getBoolean(key, default)

    fun getUserFromPref(): UserModel? {
        val json = sharedPref.getString("user_model", null)
        return if (json == null) {
            null
        } else {
            Gson().fromJson(json, UserModel::class.java)
        }
    }

    fun setUserToPref(user: UserModel?) {
        sharedPref.edit {
            if (user == null) {
                remove("user_model")
            } else {
                putString("user_model", Gson().toJson(user))
            }
        }
    }
}