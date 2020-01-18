package com.eco4ndly.weirdsms.receivers.smsreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony.Sms
import android.util.Log
import com.eco4ndly.weirdsms.infra.NotificationManager
import com.eco4ndly.weirdsms.main.model.MessageListItem.SmsMessageModel
import com.eco4ndly.weirdsms.repo.SmsRepository
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * A Sayan Porya code on 2020-01-18
 */
class SmsReceiver : BroadcastReceiver() {
  @Inject
  lateinit var notificationManager: NotificationManager

  @Inject
  lateinit var smsRepository: SmsRepository

  override fun onReceive(
    context: Context,
    intent: Intent
  ) {
    AndroidInjection.inject(this, context)
    Log.d("Sms Receiver", "new sms")

    Sms.Intents.getMessagesFromIntent(intent)
        ?.let { messages ->
          val messageList: MutableList<SmsMessageModel> = mutableListOf()
          messages.forEach {
            val smsModel = SmsMessageModel(
                body = it.messageBody,
                sender = it.displayOriginatingAddress,
                date = it.timestampMillis,
                boxId = 0
            )

            smsRepository.updateContentReolverOnSmsReceive(smsModel)

            messageList.add(
                smsModel
            )
          }
          Log.d("Sms Receiver", "Received Message Content: $messageList")
          smsRepository.syncDatabase()
          notificationManager.showNotification(messageList)
        }
  }
}