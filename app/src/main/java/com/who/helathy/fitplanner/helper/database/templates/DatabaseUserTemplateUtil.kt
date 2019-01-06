package com.who.helathy.fitplanner.helper.database.templates

import android.content.ContentValues
import android.database.Cursor
import com.who.helathy.fitplanner.domain.User
import com.who.helathy.fitplanner.helper.util.DateUtil

class DatabaseUserTemplateUtil {
    companion object {
        const val TABLE_NAME: String = "user"

        // user table columns
        private const val KEY_ID: String = "id"
        private const val KEY_NAME: String = "name"
        private const val KEY_BIRTHDATE: String = "birthdate"
        private const val KEY_HEIGHT: String = "height"
        private const val KEY_WEIGHT: String = "weight"

        fun getCreateTableExecString(): String {
            return "CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY," +
                    "$KEY_NAME TEXT, $KEY_BIRTHDATE TEXT," +
                    "$KEY_HEIGHT INTEGER, $KEY_WEIGHT INTEGER)"
        }

        fun getDropTableExecString(): String {
            return "DROP TABLE IF EXISTS $TABLE_NAME"
        }

        fun getSelectUserExecString(): String {
            return "SELECT * FROM $TABLE_NAME LIMIT 1"
        }

        fun getUserContentValues(user: User): ContentValues {
            val values = ContentValues()
            values.put(KEY_NAME, user.name)
            values.put(KEY_BIRTHDATE, DateUtil.dateToString(user.birthDate!!))
            values.put(KEY_HEIGHT, user.height)
            values.put(KEY_WEIGHT, user.weight)

            return values
        }

        fun getUserFromCursor(cursor: Cursor): User {
            val user = User()
            user.name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
            user.birthDate = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(KEY_BIRTHDATE)))
            user.height = cursor.getInt(cursor.getColumnIndex(KEY_HEIGHT))
            user.weight = cursor.getInt(cursor.getColumnIndex(KEY_WEIGHT))

            return user
        }

        fun getWhereClause(): String {
            return "$KEY_NAME=?"
        }
    }
}