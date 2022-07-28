package com.example.bookstore

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage
import okhttp3.internal.Version
import java.util.*

class FirebaseMessaging : FirebaseMessagingService() {
    lateinit var nottitle:String
    lateinit var notmessage:String
    lateinit var manager : NotificationManager
    var CHANNEL_ID="CHANNEL"

    override fun onMessageReceived(remotemessage: RemoteMessage) {
        super.onMessageReceived(remotemessage)
        nottitle = remotemessage.notification!!.title!!
        notmessage= remotemessage.notification!!.body!!



         manager=this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        sendNotification()
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
    private fun sendNotification(){
        var intent= Intent(applicationContext,MainActivity::class.java)
        intent.putExtra("title",nottitle)
        intent.putExtra("message",notmessage)
        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK


        if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.O){
            var channel=NotificationChannel(CHANNEL_ID,"pushnotification",NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }

        var builder =NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle(nottitle)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setAutoCancel(true)
            .setContentText(notmessage)

        var pendingintent=PendingIntent.getActivity(applicationContext,0,intent,PendingIntent.FLAG_ONE_SHOT)
        builder.setContentIntent(pendingintent)
        manager.notify(0,builder.build())


    }



}