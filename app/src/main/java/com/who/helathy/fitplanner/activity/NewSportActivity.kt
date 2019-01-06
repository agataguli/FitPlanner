package com.who.helathy.fitplanner.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.domain.Sport
import com.who.helathy.fitplanner.helper.database.DatabaseHelper
import com.who.helathy.fitplanner.helper.validation.SportFormValidationHelper
import kotlinx.android.synthetic.main.activity_new_sport.*

class NewSportActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_sport)

        this.setEventListeners();
    }

    private fun setEventListeners() {
        button_save_sport.setOnClickListener { _ ->
            if(this.isFormValid()) {
                this.saveSport()
                this.startSportListActivity()
            } else {
                Toast.makeText(applicationContext, resources.getString(R.string.newEntityErrorValidationMessage),
                        Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startSportListActivity() {
        val newIntent = Intent(applicationContext, SportListActivity::class.java)
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(newIntent)
    }

    private fun saveSport() {
            val sport = Sport(tName = input_sport_name.text.toString(),
                    tLostKcalPerH = input_sport_kcal.text.toString().toInt(),
                    tPictureUrl = input_sport_url.text.toString())
            DatabaseHelper.getInstance(applicationContext!!)?.addSport(sport)
    }

    private fun isFormValid(): Boolean {
        return SportFormValidationHelper(applicationContext!!).isSportFormValid(input_sport_name,
                input_sport_kcal, input_sport_url)
    }
}
