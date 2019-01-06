package com.who.helathy.fitplanner.helper.database.templates

import android.content.ContentValues
import android.database.Cursor
import com.who.helathy.fitplanner.domain.Sport

class DatabaseSportTemplateUtil {
    companion object {
        const val TABLE_NAME: String = "sport"

        // sport table columns
        private const val KEY_ID: String = "id"
        private const val KEY_NAME: String = "name"
        private const val KEY_PICTURE_URL: String = "pictureUrl"
        private const val KEY_LOST_KCAL_PER_H: String = "lostKcalPerH"


        fun getCreateTableExecString(): String {
            return "CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY," +
                    "$KEY_NAME TEXT, $KEY_PICTURE_URL TEXT, $KEY_LOST_KCAL_PER_H INTEGER)"
        }

        fun getDropTableExecString(): String {
            return "DROP TABLE IF EXISTS $TABLE_NAME"
        }

        fun getDeleteRowWhereClause(): String {
            return "$KEY_ID=?"
        }

        fun getSportContentValues(sport: Sport): ContentValues {
            val values = ContentValues()
            values.put(KEY_NAME, sport.name)
            values.put(KEY_PICTURE_URL, sport.pictureUrl)
            values.put(KEY_LOST_KCAL_PER_H, sport.lostKcalPerH)

            return values
        }

        fun getSportFromCursor(cursor: Cursor): Sport {
            val sport = Sport()
            sport.name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
            sport.lostKcalPerH = cursor.getInt(cursor.getColumnIndex(KEY_LOST_KCAL_PER_H))
            sport.pictureUrl = cursor.getString(cursor.getColumnIndex(KEY_PICTURE_URL))
            sport.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))

            return sport
        }
    }
}