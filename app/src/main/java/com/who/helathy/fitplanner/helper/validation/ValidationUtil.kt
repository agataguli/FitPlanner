package com.who.helathy.fitplanner.helper.validation

import com.who.helathy.fitplanner.helper.util.DateUtil
import java.util.*
import java.util.regex.Pattern

interface ValidationUtil {
    companion object {
        private val PICTURE_URL_PATTERN = "(http(s?):)([/.|=|?|&|\\w|\\s|-])*\\.(?:jpg|gif|png)"
        private val SHORT_DATE_PATTERN = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))"

        fun isPositiveInt(s: String): Boolean {
            if (!isIntValue(s)) return false
            return s.toInt() > 0
        }

        fun isShortDateFormatted(s: String): Boolean {
            val pattern = Pattern.compile(SHORT_DATE_PATTERN)
            if (!pattern.matcher(s).matches()) return false

            val date = DateUtil.shortDateFormatStringToDate(s)
            return Date().after(date)
        }

        // TODO: agataguli - check why Kotlin wants this isEmpty condition in a separated line
        // It looks like even if 1st condition is false return is still checking 2nd, 3nd... etc condition boolean value
        fun isValidName(s: String): Boolean {
            if (s.isEmpty()) return false
            return Character.isUpperCase(s[0]) and (s.length > 2) and (s.length < 16)
        }

        fun isValidUrl(s: String): Boolean {
            if (s.isEmpty()) return false
            val pattern = Pattern.compile(PICTURE_URL_PATTERN)
            return (s.length < 2083) and pattern.matcher(s).matches()
        }

        private fun isIntValue(s: String): Boolean {
            val numericRegex = "[0-9]+"
            val pattern : Pattern = Pattern.compile(numericRegex)
            return pattern.matcher(s).matches()
        }
    }
}