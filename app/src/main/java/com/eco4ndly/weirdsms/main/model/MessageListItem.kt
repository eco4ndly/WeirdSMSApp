package com.eco4ndly.weirdsms.main.model

import android.provider.Telephony
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A Sayan Porya code on 2020-01-18
 */
sealed class MessageListItem {
    @Entity
    data class SmsMessageModel(
        val body: String,
        val sender: String,
        val date: Long,
        val boxId: Int
    ): MessageListItem() {
        @PrimaryKey
        var uid: Int = hashCode()
    }

    data class TimeTypeItem(val time: String): MessageListItem()
}