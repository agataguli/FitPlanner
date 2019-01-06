package com.who.helathy.fitplanner.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.who.helathy.fitplanner.R
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val activityStarter = ActivityStarter()
        activityStarter.start()
    }

    private inner class ActivityStarter : Thread() {
        private var isMenuActivityStarted = false

        private val sleepTime = 5000L

        override fun run() {
            splashScreenLogo?.setOnClickListener { _ -> startMenuActivity() }

            Thread.sleep(sleepTime)
            if (!this.isMenuActivityStarted) {
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
