package com.who.helathy.fitplanner.domain

import java.util.Date

class Workout {
    var isCompleted: Boolean = false
    lateinit var date: Date
    var lostKcal: Int = 0
    lateinit var sport: Sport

    constructor(tIsCompleted: Boolean, tDate: Date, tLostKcal: Int, tSport: Sport) {
        this.isCompleted = tIsCompleted
        this.date = tDate
        this.lostKcal = tLostKcal
        this.sport = tSport
    }
}