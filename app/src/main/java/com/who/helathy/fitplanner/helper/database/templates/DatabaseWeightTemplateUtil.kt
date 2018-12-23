package com.who.helathy.fitplanner.helper.database.templates

import android.content.ContentValues
import android.os.Build
import com.who.helathy.fitplanner.domain.Weight
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
            var values = ContentValues()
            values.put(KEY_DATE, getConvertLocalDateToSimpleStringFormat(weight.date!!))
            values.put(KEY_WEIGHT_VALUE, weight.value)

            return values
        }

        private fun getConvertLocalDateToSimpleStringFormat(date: LocalDate): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            } else {
                return "${date.dayOfMonth}/${date.monthValue}/${date.year}"
            }
        }
    }
}