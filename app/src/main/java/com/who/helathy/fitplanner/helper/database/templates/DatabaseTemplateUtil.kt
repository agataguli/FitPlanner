package com.who.helathy.fitplanner.helper.database.templates

interface DatabaseTemplateUtil {
    companion object {
        const val DB_NAME: String = "fitplannerDb"
        const val DB_VERSION = 1

        fun getSelectExecString(tableName: String): String {
            return "SELECT * FROM $tableName"
        }
    }
}
