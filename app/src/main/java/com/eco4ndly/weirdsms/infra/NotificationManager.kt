package com.eco4ndly.weirdsms.infra

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.eco4ndly.weirdsms.R
import com.eco4ndly.weirdsms.main.model.MessageListItem.SmsMessageModel
import com.eco4ndly.weirdsms.main.ui.MainActivity
import com.eco4ndly.weirdsms.utl.Constants.DEFAULT_CHANNEL_ID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A Sayan Porya code on 2020-01-18
 *
 * Manages push notifications in app
 */
@Singleton
class NotificationManager @Inject constructor(private val context: Context) {

  private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
  private val vibrationPattern = longArrayOf(0, 200, 0, 200)

  init {
      createDefaultNotificationChannel()
  }

  /**
   * Used to make a notification appear
   */
  fun showNotification(messages: List<SmsMessageModel>) {

    val notifyIntent = Intent(context, MainActivity::class.java).apply {
      putExtra("from_notification", true)
    }
    val notifyPendingIntent = PendingIntent.getActivity(
        context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
    )
    val notification = NotificationCompat.Builder(context, DEFAULT_CHANNEL_ID). apply {
      setCategory(NotificationCompat.CATEGORY_MESSAGE)
      setSmallIcon(R.drawable.ic_launcher_foreground)
      setNumber(messages.size)
      setAutoCancel(true)
      setLights(Color.WHITE, 500, 2000)
      setWhen(messages.last().date)
      setVibrate(vibrationPattern)
      priority = NotificationCompat.PRIORITY_DEFAULT
      setContentIntent(notifyPendingIntent)
    }

    messages.forEach {
      notification.apply {
        setContentTitle(it.sender)
        setContentText(it.body)
      }
      notificationManager.notify(it.uid, notification.build())
    }
  }

  /**
   * Creates the default notification channel.
   * As my app is weird all messages will be shown in one single channel :p
   */
  private fun createDefaultNotificationChannel() {

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O || getNotificationChannel() != null) {
      return
    }

    val channel = NotificationChannel(DEFAULT_CHANNEL_ID, "Default", NotificationManager.IMPORTANCE_HIGH).apply {
      enableLights(true)
      lightColor = Color.WHITE
      enableVibration(true)
      vibrationPattern = vibrationPattern
    }

    notificationManager.createNotificationChannel(channel)
  }

  /**
   * To check if the channel is already created
   */
  private fun getNotificationChannel(): NotificationChannel? {
    val channelId = buildNotificationChannelId()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      return notificationManager.notificationChannels
          .find { channel -> channel.id == channelId }
    }

    return null
  }

  private fun buildNotificationChannelId(): String {
    return DEFAULT_CHANNEL_ID
  }
}