package com.example.subtrack.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.subtrack.R

// notification helper class
class NotificationHelper(private val context: Context) {
    
    companion object {
        const val CHANNEL_ID = "subscription_reminders"
        const val CHANNEL_NAME = "Subscription Reminders"
    }
    
    init {
        createNotificationChannel()
    }
    
    // create notification channel for Android 8+
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for upcoming subscription renewals"
            }
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    // show notification for subscription renewal
    fun showRenewalNotification(subscriptionName: String, daysUntilRenewal: Int, notificationId: Int) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Subscription Renewal Reminder")
            .setContentText("$subscriptionName renews in $daysUntilRenewal days")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()
        
        try {
            NotificationManagerCompat.from(context).notify(notificationId, notification)
        } catch (e: SecurityException) {
            // notification permission not granted
        }
    }
}
