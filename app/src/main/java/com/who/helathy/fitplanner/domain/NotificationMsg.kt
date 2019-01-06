package com.who.helathy.fitplanner.domain

class NotificationMsg {
    var title: String?
    var body: String?

    constructor(title: String?, body: String?) {
        this.title = title
        this.body = body
    }
}