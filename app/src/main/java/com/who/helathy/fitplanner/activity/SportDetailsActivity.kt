package com.who.helathy.fitplanner.activity

import android.os.Bundle
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.StatFs
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.activity.fragment.HelloUserFragment
import com.who.helathy.fitplanner.domain.Sport
import com.who.helathy.fitplanner.helper.database.DatabaseHelper
import com.who.helathy.fitplanner.helper.util.DateUtil
import com.who.helathy.fitplanner.helper.util.JsonHelper
import com.who.helathy.fitplanner.helper.util.StaticKeyValues
import com.who.helathy.fitplanner.service.volley.VolleyRequestProcessor

import kotlinx.android.synthetic.main.activity_sport_details.*

class SportDetailsActivity : Activity() {

    private var sport: Sport? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sport_details)


        val extras: Bundle? = intent.extras
        if (extras != null) {
            sport = JsonHelper.getSportFromJson(extras.getString(StaticKeyValues.KEY_INTENT_SPORT_JSON)!!)
            this.updateView()
        }
    }

    private fun updateView() {
        sport_content.text = sport!!.name
        kcal_content.text = sport!!.lostKcalPerH.toString()

        if (sport!!.pictureUrl != null) {
            this.downloadSportPicture()
        }

        button_remove_sport.setOnClickListener { _ ->
            AlertDialog.Builder(this)
                    .setTitle(resources.getString(R.string.removeSport))
                    .setMessage(resources.getString(R.string.removeSportConfirmationMsg))
                    .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                        this.removeSport()
                    }.setNegativeButton(resources.getString(R.string.no)) { _, _ ->
                        // Do nothing
                    }.show()
        }
    }

    private fun removeSport() {
        Toast.makeText(this, getString(R.string.sportWillBeRemoved), Toast.LENGTH_LONG).show()
        DatabaseHelper.getInstance(applicationContext)?.removeSport(sport!!)
        this.finish()
        val startIntent = Intent(applicationContext, MenuActivity::class.java)
        startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(startIntent)
    }

    private fun downloadSportPicture() {
        VolleyRequestProcessor.processImageViewRequest(sport_image, sport!!.pictureUrl!!, this)
    }

}
