package com.eco4ndly.weirdsms.infra

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.Telephony
import com.eco4ndly.weirdsms.main.model.MessageListItem
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A Sayan Porya code on 2020-01-18
 */
@Singleton
class MessageCursorProvider @Inject constructor(
    private val context: Context) {

    fun getSmsMessageCursor(): Cursor? {
        val smsProjection = arrayOf(
            Telephony.Sms.Inbox.BODY,
            Telephony.Sms.ADDRESS,
            Telephony.Sms.BODY,
            Telephony.Sms.SEEN,
            Telephony.Sms.TYPE,
            Telephony.Sms.STATUS,
            Telephony.Sms.ERROR_CODE,
            Telephony.Sms.DATE
        )

        val maxDate = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(24)
        val clause = "${Telephony.Sms.DATE} >= $maxDate"

        val uri = Telephony.Sms.Inbox.CONTENT_URI
        return context.contentResolver.query(
            uri,
            smsProjection,
            clause,
            null,
            Telephony.Sms.DEFAULT_SORT_ORDER
        )

    }

    fun cursorToMessageConverter(cursor: Cursor): MessageListItem.SmsMessageModel {
        return MessageListItem.SmsMessageModel(
            cursor.getString(0),
            cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS)),
            cursor.getLong(cursor.getColumnIndex(Telephony.Sms.DATE)),
            cursor.getInt(cursor.getColumnIndex(Telephony.Sms.TYPE))
        )
    }
}