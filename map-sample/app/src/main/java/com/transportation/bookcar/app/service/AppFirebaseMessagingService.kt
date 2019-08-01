package com.transportation.bookcar.app.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import com.transportation.bookcar.app.KEY_NOTIFICATION
import com.transportation.bookcar.app.R
import com.transportation.bookcar.app.splash.SplashActivity

/**
 * Created on 4/5/2018.
 */
class AppFirebaseMessagingService : FirebaseMessagingService() {
    
    companion object {
        private const val TAG = "AppMessagingService"
        private const val KEY_MESSAGE = "message"
        private const val REQUEST_OPEN_APP = 1000
    }
    
    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(newToken)
    }
    
    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        dumpMessage(remoteMessage)
        // Check if message contains a data payload.
        if (remoteMessage.data.containsKey(KEY_MESSAGE)) {
            sendNotification(
                    remoteMessage.messageId!!.hashCode(),
                    remoteMessage.data[KEY_MESSAGE]!!
            )
        }
        
        if (remoteMessage.notification != null) {
            // Check if message contains a notification payload.
        }
        
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    
    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String) {
        Timber.d("sendRegistrationToServer $token")
        RegistrationDeviceTokenService.scheduleSendDeviceToken(this, token)
    }
    
    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageId: Int, messageBody: String) {
        val intent = Intent(this, SplashActivity::class.java)
        intent.putExtra(KEY_NOTIFICATION, REQUEST_OPEN_APP)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
                this, REQUEST_OPEN_APP, intent,
                PendingIntent.FLAG_ONE_SHOT
        )
        
        val channelId = getString(R.string.app_name)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_app_stat_notification)
                .setContentTitle(channelId)
                .setContentText(messageBody)
                //                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
        
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    channelId,
                    channelId,
                    NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        //TODO change notification id
        notificationManager.notify(messageId /* ID of notification */, notificationBuilder.build())
    }
    
    private fun dumpMessage(message: RemoteMessage) {
        val sb = StringBuilder("From : ${message.from}  \nTo : ${message.to} + \nMessageId : ${message.messageId} \nMessageType : ${message.messageType}")
        var index = 0
        for (data in message.data.entries) {
            sb.append("\nkey").append(index).append("=").append(data.key)
                    .append("\nvalue").append(index).append("=").append(data.value)
            index++
        }
        Timber.d(sb.toString())
    }
}
