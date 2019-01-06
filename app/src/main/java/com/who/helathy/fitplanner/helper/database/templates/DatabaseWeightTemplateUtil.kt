package com.who.helathy.fitplanner.helper.database.templates

import android.content.ContentValues
import android.database.Cursor
import com.who.helathy.fitplanner.domain.Weight
import com.who.helathy.fitplanner.helper.util.DateUtil

class DatabaseWeightTemplateUtil {
    companion object {
        const val TABLE_NAME: String = "weight"

        // weight table columns
        private const val KEY_ID: String = "id"
        private const val KEY_DATE: String = "date"
        private const val KEY_WEIGHT_VALUE: String = "value"

        fun getCreateTableExecString(): String {
            return "CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY," +
                    "$KEY_DATE TEXT, $KEY_WEIGHT_VALUE REAL)"
        }

        fun getDropTableExecString(): String {
            return "DROP TABLE IF EXISTS $TABLE_NAME"
        }

        fun getWeightContentValues(weight: Weight): ContentValues {
            val values = ContentValues()
            values.put(KEY_DATE, DateUtil.dateToString(weight.date!!))
            values.put(KEY_WEIGHT_VALUE, weight.value)

            return values
        }

        fun getWeightFromCursor(cursor: Cursor): Weight {
            val weight = Weight()
            weight.value = cursor.getInt(cursor.getColumnIndex(KEY_WEIGHT_VALUE))
            weight.date = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)))

            return weight
        }
    }

}
