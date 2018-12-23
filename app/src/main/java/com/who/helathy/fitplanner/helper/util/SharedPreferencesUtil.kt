package com.who.helathy.fitplanner.helper.util

import android.content.Context
import com.who.helathy.fitplanner.R

class SharedPreferencesUtil {
    companion object {

        fun cleanSharedPreferences(context: Context) {
            context.getSharedPreferences(this.getSharedPreferencesName(context), 0).edit().clear().commit()
        }

        fun saveToSharedPreferences(context: Context, key: String, value: String) {
            val sharedPreferences = context.getSharedPreferences(this.getSharedPreferencesName(context), Context.MODE_PRIVATE)
                    ?: return
            with(sharedPreferences.edit()) {
                putString(key, value)
                commit()
            }
        }

        fun saveToSharedPreferences(context: Context, key: String, value: Boolean) {
            val sharedPreferences = context.getSharedPreferences(this.getSharedPreferencesName(context), Context.MODE_PRIVATE)
                    ?: return
            with(sharedPreferences.edit()) {
                putBoolean(key, value)
                commit()
            }
        }

        fun readFromSharedPreferences(context: Context, key: String): String? {
            val sharedPreferences = context.getSharedPreferences(this.getSharedPreferencesName(context), Context.MODE_PRIVATE)
            val defaultValue = null
            return sharedPreferences?.getString(key, defaultValue)
        }

        fun readFlagFromSharedPreferences(context: Context, key: String): Boolean? {
            val sharedPreferences = context.getSharedPreferences(this.getSharedPreferencesName(context), Context.MODE_PRIVATE)
            val defaultValue = false
            return sharedPreferences?.getBoolean(key, defaultValue)
        }

        private fun getSharedPreferencesName(context: Context): String {
            return context.getString(R.string.app_name)
        }
    }
}