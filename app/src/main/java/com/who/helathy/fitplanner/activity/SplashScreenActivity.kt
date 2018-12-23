package com.who.helathy.fitplanner.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.domain.User
import com.who.helathy.fitplanner.helper.database.DatabaseHelper
import com.who.helathy.fitplanner.helper.util.JsonHelper
import com.who.helathy.fitplanner.helper.util.SharedPreferencesUtil
import com.who.helathy.fitplanner.helper.util.StaticKeyValues
import kotlinx.android.synthetic.main.activity_splash_screen.splashScreenLogo

class SplashScreenActivity : AppCompatActivity() {
    private var isUserDataSelected = false
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val activityStarter = ActivityStarter()
        activityStarter.start()
        this.getUserData()
    }

    /**
     * Takes saved user data if exists.
     *
     * Sets isUserDataSelected variable, which allows to start MenuActivity.
     * Get user from database and save it to sharedPreference if not saved on sharedPreferences,
     * otherwise gets user from sharedPreferences.
     */
    private fun getUserData() {
        this.isUserDataSelected = true

        val userJson = SharedPreferencesUtil.readFromSharedPreferences(this, StaticKeyValues.KEY_OBJECT_USER)
        if(!userJson.isNullOrBlank()) {
            this.user = JsonHelper.getUserFromJson(userJson!!)
            return
        }

        this.user = DatabaseHelper.getInstance(this)?.getUser()
        if(this.user != null) {
            this.saveUserDataToSharedPreferences()
            SharedPreferencesUtil.saveToSharedPreferences(this, StaticKeyValues.KEY_FLAG_USER_CREATED, true)
        }
    }

    private fun saveUserDataToSharedPreferences() {
        SharedPreferencesUtil.
                saveToSharedPreferences(this, StaticKeyValues.KEY_OBJECT_USER, JsonHelper.getJsonFromUser(user!!))
    }

    private inner class ActivityStarter : Thread() {
        private var isMenuActivityStarted = false

        private val sleepTime = 5000L

        override fun run() {
            splashScreenLogo?.setOnClickListener { _ -> if (isUserDataSelected) startMenuActivity() }

            Thread.sleep(sleepTime)
            if (!this.isMenuActivityStarted and isUserDataSelected) {
                this.startMenuActivity()
            }
        }

        private fun startMenuActivity() {
            this.isMenuActivityStarted = true
            val intent = Intent(applicationContext, MenuActivity::class.java)

            startActivity(intent)
            overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_top)
        }
    }
}
