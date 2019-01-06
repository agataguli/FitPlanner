package com.who.helathy.fitplanner.helper.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DateUtil {
    companion object {
        private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        private val SHORT_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd")
        private val YEAR_DATE_FORMAT = SimpleDateFormat("yyyy")
        private val MONTH_DATE_FORMAT = SimpleDateFormat("MM")
        private val DAY_DATE_FORMAT = SimpleDateFormat("dd")

        fun dateToString(date: Date): String {
            return DATE_FORMAT.format(date)
        }

        fun dateToStringShortDateFormat(date: Date): String {
            return SHORT_DATE_FORMAT.format(date)
        }

        fun stringToDate(year : Int, month: Int, dayOfMonth: Int): Date {
            return GregorianCalendar(year, month, dayOfMonth).time
        }

        fun stringToDate(stringDate: String): Date {
            return DATE_FORMAT.parse(stringDate)
        }

        fun shortDateFormatStringToDate(stringDate: String): Date {
            return SHORT_DATE_FORMAT.parse(stringDate)
        }

        fun getYear(date: Date): Int {
            return Integer.valueOf(YEAR_DATE_FORMAT.format(date));
        }

        fun getMonth(date: Date): Int {
            return Integer.valueOf(MONTH_DATE_FORMAT.format(date));
        }

        fun getDay(date: Date): Int {
            return Integer.valueOf(DAY_DATE_FORMAT.format(date));
        }

        fun getDatePlusDays(oldDate: Date, daysToAdd: Int): Date {
            val cal = GregorianCalendar()
            cal.time = oldDate
            cal.add(Calendar.DATE, daysToAdd)

            return cal.time
        }

        fun millisecondsToSeconds(milliseconds: Long): Long {
            return TimeUnit.MILLISECONDS.toSeconds(milliseconds)
        }

    }
}