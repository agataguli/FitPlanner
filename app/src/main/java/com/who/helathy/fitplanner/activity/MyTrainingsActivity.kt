package com.who.helathy.fitplanner.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.adapter.WorkoutAdapter
import com.who.helathy.fitplanner.domain.Workout
import com.who.helathy.fitplanner.helper.database.DatabaseHelper
import com.who.helathy.fitplanner.helper.util.JsonHelper
import com.who.helathy.fitplanner.helper.util.StaticKeyValues
import kotlinx.android.synthetic.main.activity_my_trainings.*

class MyTrainingsActivity : AppCompatActivity() {
    private var listViewAdapter: WorkoutAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_trainings)

        val workoutList = DatabaseHelper.getInstance(applicationContext)?.getAllWorkouts()
        listViewAdapter = WorkoutAdapter(this, workoutList!!)
        lv_trainings!!.adapter = listViewAdapter
        lv_trainings!!.invalidateViews()

        this.setViewPreconditions()
    }

    private fun setViewPreconditions() {
        button_add_notification.setOnClickListener { _ ->
            startActivity(Intent(this, NewNotificationActivity::class.java))
        }

        button_train.setOnClickListener { _ ->
            startActivity(Intent(this, TrainActivity::class.java))
        }

        lv_trainings.setOnItemClickListener { parent, view, position, id ->
            val entry: Workout = parent.adapter.getItem(position) as Workout
            val intent = Intent(this, WorkoutDetailsActivitity::class.java)
            intent.putExtra(StaticKeyValues.KEY_INTENT_WORKOUT_JSON, JsonHelper.getJsonFromWorkout(workout = entry))
            startActivity(intent)
        }
    }
}
