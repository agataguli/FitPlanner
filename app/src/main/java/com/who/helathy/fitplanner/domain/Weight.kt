package com.who.helathy.fitplanner.domain

import java.util.Date

class Weight: Comparable<Weight> {
    var date: Date? = null
    var value: Int = 0

    constructor() {
    }

    constructor(date: Date, value: Int) {
        this.date = date
        this.value = value
    }

    override fun compareTo(other: Weight): Int {
       return this.date!!.compareTo(other.date)
    }
}
