package com.who.helathy.fitplanner.domain

import java.util.Date

class Workout {
    var isCompleted: Boolean = false
    var dateStart: Date? = null
    var dateEnd: Date? = null
    var lostKcal: Int = 0
    var sport: Sport? = null

    constructor(tIsCompleted: Boolean, tDateStart: Date, tDateEnd: Date, tLostKcal: Int, tSport: Sport) {
        this.isCompleted = tIsCompleted
        this.dateStart = tDateStart
        this.dateEnd = tDateEnd
        this.lostKcal = tLostKcal
        this.sport = tSport
    }

    constructor() {
    }
}