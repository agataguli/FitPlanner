package com.who.helathy.fitplanner.helper.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.who.helathy.fitplanner.domain.Sport
import com.who.helathy.fitplanner.domain.User
import com.who.helathy.fitplanner.domain.Workout

class JsonHelper {
    companion object {
        fun getUserFromJson(json: String): User {
            return GsonBuilder().create().fromJson(json, User::class.java)
        }

        fun getSportFromJson(json: String): Sport {
            return GsonBuilder().create().fromJson(json, Sport::class.java)
        }

        fun getWorkoutFromJson(json: String): Workout {
            return GsonBuilder().create().fromJson(json, Workout::class.java)
        }

        fun getJsonFromUser(user: User): String {
            return Gson().toJson(user)
        }

        fun getJsonFromSport(sport: Sport): String {
            return Gson().toJson(sport)
        }

        fun getJsonFromWorkout(workout: Workout): String {
            return Gson().toJson(workout)
        }
    }
}