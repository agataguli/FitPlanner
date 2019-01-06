package com.who.helathy.fitplanner.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.who.helathy.fitplanner.R
import com.who.helathy.fitplanner.activity.InstantMsgActivity
import com.who.helathy.fitplanner.domain.NotificationMsg
import com.who.helathy.fitplanner.helper.util.SharedPreferencesUtil

class FirebaseRuntimeMessageService: FirebaseMessagingService() {

    companion object {
        const val TAG = "FireRuntMsgServ"
    }

    // token can be used to create direct messages! If anyone knows it's token it can be used to
    // send message/notification to single device
    override fun onNewToken(token: String?) {
        Log.i(TAG, "New token $token generated.")
        super.onNewToken(token)
        saveToken(token!!)
    }

    // background notifications produce notification as always but we can
    // customize what should be done when it meets runtime firebase notification
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        val notification = NotificationMsg(title = remoteMessage?.notification?.title, body = remoteMessage?.notification?.body)
        Log.i(TAG, "Received remote msg with title: ${notification.title}")
        this.produceEventPush(notification)
    }

    private fun produceEventPush(notificationEvent: NotificationMsg) {
        val intent = Intent(this, InstantMsgActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pending: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val notBuilder : NotificationCompat.Builder = NotificationCompat.Builder(this, getString(R.string.firebaseChannel))
        notBuilder.setContentTitle(notificationEvent.title)
        notBuilder.setContentText(notificationEvent.body)
        notBuilder.setSmallIcon(R.drawable.logo_small)
        notBuilder.setAutoCancel(true)
        notBuilder.setContentIntent(pending)

        val manager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, notBuilder.build())
    }

    private fun saveToken(token: String) {
        SharedPreferencesUtil.saveToSharedPreferences(this, getString(R.string.firebaseTokenKey), token)
    }

}