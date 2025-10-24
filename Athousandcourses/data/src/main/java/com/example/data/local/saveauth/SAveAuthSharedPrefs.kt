package com.example.data.local.saveauth

import android.content.Context
private val APP_PREFERENCES = "settings"
private val APP_PREFERENCES_AUTH = "user_auth"
class SAveAuthSharedPrefs(context: Context): StorageInt {
    val sharedPrefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    override fun save(saveParam: Boolean): Boolean {
        val editor = sharedPrefs.edit()
        editor.putBoolean(APP_PREFERENCES_AUTH, saveParam).apply()
        return true
    }

    override fun get(): Boolean {
        return sharedPrefs.getBoolean(APP_PREFERENCES_AUTH,false)
    }
}