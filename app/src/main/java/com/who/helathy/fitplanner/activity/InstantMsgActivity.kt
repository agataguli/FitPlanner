package com.who.helathy.fitplanner.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.who.helathy.fitplanner.R
import kotlinx.android.synthetic.main.activity_instant_msg.*

class InstantMsgActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instant_msg)

        showInstantMsgText(intent.extras)
    }

    private fun showInstantMsgText(extras: Bundle?) {
        msg_topic.text = extras!!.getString(getString(R.string.msgTitle))
        msg_body.text = extras.getString(getString(R.string.msgBody))
    }
}
