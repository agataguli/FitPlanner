package com.who.helathy.fitplanner.helper.util

import com.who.helathy.fitplanner.domain.Sport
import com.who.helathy.fitplanner.domain.Workout
import java.util.Date

class SportUtil {
    companion object {

        fun getSportNames(list: ArrayList<Sport>?): ArrayList<String> {
            val names = ArrayList<String>()
            list!!.forEach { sport ->
                names.add(sport.name!!)
            }
            return names
        }

        fun calculateWorkout(sport: Sport, millisecondsTraining: Long, timeStart: Date, timeEnd: Date): Workout {
            val kcals = calculateKcals(sport.lostKcalPerH, millisecondsTraining)

            val workout = Workout(tIsCompleted = true, tDateStart = timeStart, tDateEnd = timeEnd,
                    tLostKcal = kcals, tSport = sport)
            return workout
        }

        fun calculateKcals(lostKcalPerH: Int, millisecondsTraining: Long): Int {
            val millisecondsInHour = 3600000
            val takenPartOfHour: Double = millisecondsTraining.toDouble()/millisecondsInHour.toDouble()

            return Math.round(takenPartOfHour * lostKcalPerH).toInt()
        }
    }
}