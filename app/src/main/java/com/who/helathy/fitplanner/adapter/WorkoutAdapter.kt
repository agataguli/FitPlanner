package com.who.helathy.fitplanner.adapter

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.domain.Workout
import com.who.helathy.fitplanner.helper.util.DateUtil

class WorkoutAdapter: ArrayAdapter<Workout> {

    private var mContext: Context
    private var mWorkouts: List<Workout> = ArrayList()

    constructor(@NonNull context: Context, @LayoutRes workouts: ArrayList<Workout>): super(context, R.layout.item_workout, workouts) {
        mContext = context
        mWorkouts = workouts
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItem: View? = convertView
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_workout, parent, false)
        }

        val currentWorkout: Workout = mWorkouts.get(position)

        val sportTv = listItem!!.findViewById(R.id.tv_training_sport) as TextView
        sportTv.text = "${context.resources.getString(R.string.sport)}: ${currentWorkout.sport!!.name}"

        val kcalTv = listItem!!.findViewById(R.id.tv_training_kcal) as TextView
        kcalTv.text = "${context.resources.getString(R.string.lostKcals)}: ${currentWorkout.lostKcal}"

        val startTv = listItem.findViewById(R.id.tv_training_start) as TextView
        startTv.text = "${context.resources.getString(R.string.start)}: ${DateUtil.dateToString(currentWorkout.dateStart!!)}"

        val endTv = listItem.findViewById(R.id.tv_training_end) as TextView
        endTv.text = "${context.resources.getString(R.string.stop)}: ${DateUtil.dateToString(currentWorkout.dateEnd!!)}"

        return listItem
    }

}