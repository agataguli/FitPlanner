package com.who.helathy.fitplanner.helper.database.templates

import android.content.ContentValues
import com.who.helathy.fitplanner.domain.Workout
import java.text.SimpleDateFormat
import java.util.Date

class DatabaseWorkoutTemplateUtil {
    companion object {
        val TABLE_NAME: String = "workout"

        // workout table columns
        private const val KEY_ID: String = "id"
        private const val KEY_IS_COMPLETED: String = "isCompleted"
        private const val KEY_TIME: String = "time"
        private const val KEY_LOST_KCAL: String = "lostKcal"
        private const val KEY_SPORT_FK: String = "sportFk"


        fun getCreateTableExecString(): String {
            return "CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY," +
                    "$KEY_SPORT_FK INTEGER REFERENCES $TABLE_NAME," +
                    "$KEY_IS_COMPLETED INTEGER, $KEY_TIME TEXT, $KEY_LOST_KCAL INTEGER)"
        }

        fun getDropTableExecString(): String {
            return "DROP TABLE IF EXISTS $TABLE_NAME"
        }

        fun getWorkoutContentValues(workout: Workout): ContentValues {
            var values = ContentValues()
            values.put(KEY_IS_COMPLETED, workout.isCompleted)
            values.put(KEY_TIME, getConvertedDateToSimpleStringFormat(workout.date))
            values.put(KEY_LOST_KCAL, workout.lostKcal)
            values.put(KEY_SPORT_FK, workout.sport.getId())

            return values
        }

        private fun getConvertedDateToSimpleStringFormat(date: Date): String {
            return SimpleDateFormat.getDateTimeInstance().format(date)
        }

    }
}