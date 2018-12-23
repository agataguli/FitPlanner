package com.who.helathy.fitplanner.helper.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.who.helathy.fitplanner.domain.User

class JsonHelper {
    companion object {
        fun getUserFromJson(json: String): User {
            return GsonBuilder().create().fromJson(json, User::class.java)
        }

        fun getJsonFromUser(user: User): String {
            return Gson().toJson(user)
        }
    }
}