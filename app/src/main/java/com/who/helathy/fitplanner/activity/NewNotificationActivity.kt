package com.who.helathy.fitplanner.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.support.design.widget.Snackbar
import android.widget.ArrayAdapter
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.helper.database.DatabaseHelper
import com.who.helathy.fitplanner.helper.util.DateUtil
import com.who.helathy.fitplanner.helper.util.SportUtil
import com.who.helathy.fitplanner.helper.validation.TrainingValidationHelper
import kotlinx.android.synthetic.main.activity_new_notification.*
import java.util.*

class NewNotificationActivity : AppCompatActivity() {
    private var sportNamesList: ArrayList<String> = ArrayList()
    private var selectedDate = Date(Date().time + (1000 * 60 * 60 * 24))
    private var backToTrainingsActivity = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_notification)

        val sportList = DatabaseHelper.getInstance(applicationContext)?.getAllSports()
        sportNamesList = SportUtil.getSportNames(sportList)


        this.setViewPreconditions()
    }

    override fun onResume() {
        super.onResume()
        if (!backToTrainingsActivity) return
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(Intent(applicationContext, MenuActivity::class.java))
    }

    private fun setViewPreconditions() {
        input_time_choose_r.setIs24HourView(true)

        input_date_choose_r.date = selectedDate.time
        input_date_choose_r.minDate = Date().time
        input_date_choose_r.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = GregorianCalendar(year, month, dayOfMonth).time
        }

        val spinnerSportAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sportNamesList)
        spinnerSportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner_sport_choose_r.adapter = spinnerSportAdapter

        button_training_notification_save_r.setOnClickListener { _ ->
            this.validateAndCreateNotification()
        }
    }

    private fun validateAndCreateNotification() {
        if(this.isFormValid(selectedDate)) {
            val startMillis: Long = Calendar.getInstance().run {
                set(DateUtil.getYear(selectedDate), DateUtil.getMonth(selectedDate),
                        DateUtil.getDay(selectedDate), input_time_choose_r.hour, input_time_choose_r.minute)
                timeInMillis
            }
            backToTrainingsActivity = true
            this.createNotification(startMillis)
        } else {
            Snackbar.make(findViewById(android.R.id.content),
                    getString(R.string.newEntityErrorValidationMessage), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun createNotification(startMillis: Long) {
        val intent = Intent(Intent.ACTION_INSERT)
        intent.setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
                .putExtra(CalendarContract.Events.TITLE, spinner_sport_choose_r.selectedItem.toString())
                .putExtra(CalendarContract.Events.DESCRIPTION, resources.getString(R.string.fitPlannerActivityNotificationDescription))
        startActivity(intent)
    }

    private fun isFormValid(date: Date): Boolean {
        return TrainingValidationHelper(this).isSportNotificationValid(date)
    }
}
