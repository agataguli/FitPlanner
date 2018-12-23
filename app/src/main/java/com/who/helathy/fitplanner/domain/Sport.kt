package com.who.helathy.fitplanner.domain

class Sport {
    var name: String? = null
    var pictureUrl: String? = null
    var lostKcalPerH: Int = 0

    private var id: Int = -1

    constructor(tName: String, tPictureUrl: String, tLostKcalPerH: Int) {
        this.name = tName
        this.pictureUrl = tPictureUrl
        this.lostKcalPerH = tLostKcalPerH
    }

    constructor(tName: String, tPictureUrl: String, tLostKcalPerH: Int, tId: Int) {
        Sport(tName, tPictureUrl, tLostKcalPerH)
        this.id = tId
    }

    fun getId(): Int {
        return id
    }
}