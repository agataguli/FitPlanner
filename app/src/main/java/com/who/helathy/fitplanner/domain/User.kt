package com.who.helathy.fitplanner.domain

import java.util.Date

class User {
    // required
    var name: String? = null
    var weight: Int? = null
    var birthDate: Date? = null
    var height: Int? = null

    constructor()

    constructor(tName: String, tWeight: Int, tBirthDate: Date, tHeight: Int) {
        name = tName
        weight = tWeight
        birthDate = tBirthDate
        height = tHeight
    }
}