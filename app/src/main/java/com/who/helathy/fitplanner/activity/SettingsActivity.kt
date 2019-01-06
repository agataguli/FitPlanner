package com.who.helathy.fitplanner.activity

import android.os.Bundle
import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.widget.Toast
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.helper.database.DatabaseHelper
import com.who.helathy.fitplanner.helper.util.SharedPreferencesUtil

import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        this.setOnEventsListeners()
    }

    private fun setOnEventsListeners() {
        link_app_info_r.setOnClickListener { _ ->
            Toast.makeText(applicationContext, getString(R.string.shortAboutApp), Toast.LENGTH_LONG).show()
        }

        copy_firebase_token.setOnLongClickListener { _ ->
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData: ClipData = ClipData.newPlainText("text", SharedPreferencesUtil.readFromSharedPreferences(applicationContext, getString(R.string.firebaseTokenKey)))
            clipboardManager.primaryClip = clipData
            Toast.makeText(applicationContext, getString(R.string.tokenCopied), Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener true
        }

        button_remove_all_info_r.setOnClickListener { _ ->
            AlertDialog.Builder(this)
                    .setTitle(resources.getString(R.string.clearAllAppData))
                    .setMessage(resources.getString(R.string.clearAllAppDataQuestion))
                    .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                        clearAllData()
                    }.setNegativeButton(resources.getString(R.string.no)) { _, _ ->
                        // Do nothing
                    }.show()
        }
    }

    private fun clearAllData() {
        SharedPreferencesUtil.cleanSharedPreferences(applicationContext)

        val dbInstance = DatabaseHelper.getInstance(applicationContext)
        dbInstance!!.removeAnCreateDatabase(dbInstance.writableDatabase)

        val startIntent = Intent(applicationContext, SplashScreenActivity::class.java)
        startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startIntent)
    }

}
