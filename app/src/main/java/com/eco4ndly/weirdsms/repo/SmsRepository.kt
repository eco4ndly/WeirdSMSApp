package com.eco4ndly.weirdsms.repo

import android.content.Context
import android.provider.Telephony
import androidx.core.content.contentValuesOf
import com.eco4ndly.weirdsms.database.AppDatabase
import com.eco4ndly.weirdsms.infra.MessageCursorProvider
import com.eco4ndly.weirdsms.main.model.MessageListItem.SmsMessageModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

/**
 * A Sayan Porya code on 2020-01-18
 */
class SmsRepository @Inject constructor(
    private val messageCursorProvider: MessageCursorProvider,
    private val context: Context,
    private val appDatabase: AppDatabase) {

    @Deprecated("Doesn't persist data")
    fun getSmsMessages(offset: Int, limit: Int): List<SmsMessageModel> {
        val smsList: MutableList<SmsMessageModel> = mutableListOf()
        messageCursorProvider.getSmsMessageCursor()?.let {c ->
            if (c.moveToFirst()) {
                for (i in 0 until c.count) {
                    smsList.add(messageCursorProvider.cursorToMessageConverter(c))
                    c.moveToNext()
                }
            }
        }
        return smsList
    }

    fun getMessagesV2(): Flow<List<SmsMessageModel>> {
        return appDatabase.smsMessageDao().getAll()
    }

    fun updateContentReolverOnSmsReceive(smsMessageModel: SmsMessageModel) {
        val updateValues = contentValuesOf(
            Telephony.Sms.BODY to smsMessageModel.body,
            Telephony.Sms.ADDRESS to smsMessageModel.sender,
            Telephony.Sms.DATE to smsMessageModel.date
        )

        context.contentResolver.insert(Telephony.Sms.Inbox.CONTENT_URI, updateValues)
    }

    fun syncDatabase() {
        val smsList: MutableList<SmsMessageModel> = mutableListOf()
        messageCursorProvider.getSmsMessageCursor()?.let {c ->
            if (c.moveToFirst()) {
                for (i in 0 until c.count) {
                    smsList.add(messageCursorProvider.cursorToMessageConverter(c))
                    c.moveToNext()
                }
            }
        }
        GlobalScope.launch(Dispatchers.IO) {
            appDatabase.smsMessageDao().insertAll(smsList)
        }
    }
}