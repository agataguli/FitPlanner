package com.who.helathy.fitplanner.activity

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Toast
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.R.string.weight
import com.who.helathy.fitplanner.domain.Sport
import com.who.helathy.fitplanner.helper.database.DatabaseHelper
import com.who.helathy.fitplanner.helper.util.DateUtil
import com.who.helathy.fitplanner.helper.util.SportUtil
import com.who.helathy.fitplanner.service.volley.VolleyRequestProcessor
import kotlinx.android.synthetic.main.activity_train.*
import java.util.Date
import kotlin.collections.ArrayList

class TrainActivity : AppCompatActivity() {
    private var sportNamesList: ArrayList<String> = ArrayList()
    private var sportList: ArrayList<Sport>? = ArrayList()

    private var selectedSport: Sport? = null
    private var timeStart: Date? = null
    private var timeEnd: Date? = null

    private var isRunning = false
    private var isNew = true
    private var pauseOffset: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train)

        sportList = DatabaseHelper.getInstance(applicationContext)?.getAllSports()
        sportNamesList = SportUtil.getSportNames(sportList)

        setContentViewDetails()
    }

    private fun setContentViewDetails() {
        button_start.isEnabled = true
        button_pause.isEnabled = false
        button_save_workout.isEnabled = false

        val spinnerSportAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sportNamesList)
        spinnerSportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner_train_sport_choose_r.adapter = spinnerSportAdapter

        button_start.setOnClickListener { _ ->
            this.startChronometr()
        }

        button_pause.setOnClickListener { _ ->
            this.pauseChronometr()
        }

        button_cancel_workout.setOnClickListener { _ ->
            this.finish()
            val startIntent = Intent(applicationContext, MenuActivity::class.java)
            startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(startIntent)
        }

        button_save_workout.setOnClickListener { _ ->
            timeEnd = Date()
            this.saveWorkout()

            this.finish()
            val startIntent = Intent(applicationContext, MyTrainingsActivity::class.java)
            startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    Toast.makeText(applicationContext, getString(R.string.trainingCreated), Toast.LENGTH_SHORT).show()
            startActivity(startIntent)
        }
    }

    private fun saveWorkout() {
        val workout = SportUtil.calculateWorkout(selectedSport!!, (SystemClock.elapsedRealtime() - chronometer_training.base), timeStart!!, timeEnd!!)
        DatabaseHelper.getInstance(applicationContext!!)?.addWorkout(workout = workout)
    }

    private fun pauseChronometr() {
        if (!isRunning) return
        chronometer_training.stop()
        pauseOffset = SystemClock.elapsedRealtime() - chronometer_training.base
        isRunning = false
        button_start.isEnabled = true
        button_pause.isEnabled = false
        button_save_workout.isEnabled = true

        label_burned_kcals_r.setText(SportUtil.calculateKcals(selectedSport!!.lostKcalPerH, pauseOffset).toString())
    }

    private fun startChronometr() {
        if (sportList == null || sportList!!.isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content),
                    getString(R.string.cannotTrainWithoutSport), Snackbar.LENGTH_SHORT).show()
            return
        }

        val selectedSportName = spinner_train_sport_choose_r.selectedItem.toString()
        selectedSport = findSportWithName(selectedSportName)
        if (isNew) downloadSportPicture()
        if (isRunning || selectedSportName.isEmpty() || selectedSportName.isBlank()) return
        chronometer_training.base = SystemClock.elapsedRealtime() - pauseOffset // includes time spend during sleep
        chronometer_training.start()
        isRunning = true

        spinner_train_sport_choose_r.isEnabled = false
        timeStart = Date()
        label_date_start_r.setText(DateUtil.dateToString(timeStart!!))
        button_pause.isEnabled = true
        button_start.isEnabled = false
        button_save_workout.isEnabled = false
    }

    private fun downloadSportPicture() {
        VolleyRequestProcessor.processImageViewRequest(sport_train_image, selectedSport!!.pictureUrl!!, this)
    }

    private fun findSportWithName(selectedSportName: String): Sport? {
        return sportList!!.first { sport -> sport.name == selectedSportName }
    }

}
