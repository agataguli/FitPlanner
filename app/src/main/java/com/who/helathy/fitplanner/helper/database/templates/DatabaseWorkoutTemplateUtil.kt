package com.who.helathy.fitplanner.helper.database.templates

import android.content.ContentValues
import android.database.Cursor
import com.who.helathy.fitplanner.domain.Sport
import com.who.helathy.fitplanner.domain.Workout
import com.who.helathy.fitplanner.helper.util.DateUtil
import java.text.SimpleDateFormat
import java.util.Date

class DatabaseWorkoutTemplateUtil {
    companion object {
        val TABLE_NAME: String = "workout"

        // workout table columns
        private const val KEY_ID: String = "id"
        private const val KEY_SPORT_NAME: String = "sportName"
        private const val KEY_IS_COMPLETED: String = "isCompleted"
        private const val KEY_TIME_START: String = "timeStart"
        private const val KEY_TIME_END: String = "timeEnd"
        private const val KEY_LOST_KCAL: String = "lostKcal"
        // private const val KEY_SPORT_FK: String = "sportFk" not needed, probably just sport name is enough right now


        fun getCreateTableExecString(): String {
            return "CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY," +
                    //"$KEY_SPORT_FK INTEGER REFERENCES $TABLE_NAME," +
                    "$KEY_SPORT_NAME TEXT, " +
                    "$KEY_IS_COMPLETED INTEGER, $KEY_TIME_START TEXT, $KEY_TIME_END TEXT, $KEY_LOST_KCAL INTEGER)"
        }

        fun getDropTableExecString(): String {
            return "DROP TABLE IF EXISTS $TABLE_NAME"
        }

        fun getWorkoutContentValues(workout: Workout): ContentValues {
            val values = ContentValues()
            values.put(KEY_IS_COMPLETED, workout.isCompleted)
            values.put(KEY_TIME_START, DateUtil.dateToString(workout.dateStart!!))
            values.put(KEY_TIME_END, DateUtil.dateToString(workout.dateEnd!!))
            values.put(KEY_LOST_KCAL, workout.lostKcal)
            values.put(KEY_SPORT_NAME, workout.sport!!.name)
            //values.put(KEY_SPORT_FK, workout.sport!!.id)

            return values
        }

        fun getWorkoutFromCursor(cursor: Cursor): Workout {
            val workout = Workout()
            workout.isCompleted = cursor.getInt(cursor.getColumnIndex(KEY_IS_COMPLETED)) > 0;
            workout.dateStart = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(KEY_TIME_START)))
            workout.dateEnd = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(KEY_TIME_END)))
            workout.lostKcal = cursor.getInt(cursor.getColumnIndex(KEY_LOST_KCAL))

            workout.sport = Sport(cursor.getString(cursor.getColumnIndex(KEY_SPORT_NAME)))

            return workout
        }

    }
}