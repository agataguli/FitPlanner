package com.who.helathy.fitplanner.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.domain.Workout
import com.who.helathy.fitplanner.helper.util.DateUtil
import com.who.helathy.fitplanner.helper.util.JsonHelper
import com.who.helathy.fitplanner.helper.util.StaticKeyValues
import kotlinx.android.synthetic.main.activity_workout_details_activitity.*

class WorkoutDetailsActivitity : AppCompatActivity() {
    private var workout: Workout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_details_activitity)

        val extras: Bundle? = intent.extras
        if (extras != null) {
            workout = JsonHelper.getWorkoutFromJson(extras.getString(StaticKeyValues.KEY_INTENT_WORKOUT_JSON)!!)
            this.updateView()
        }
    }

    private fun updateView() {
        input_workout_sport_name_r.setText(workout!!.sport!!.name)
        input_workout_kcals_r.setText(workout!!.lostKcal.toString())
        input_workout_start_r.setText(DateUtil.dateToString(workout!!.dateStart!!))
        input_workout_stop_r.setText(DateUtil.dateToString(workout!!.dateEnd!!))

    }
}
