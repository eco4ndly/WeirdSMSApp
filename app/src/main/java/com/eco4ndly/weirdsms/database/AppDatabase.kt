package com.eco4ndly.weirdsms.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eco4ndly.weirdsms.main.model.MessageListItem
import com.eco4ndly.weirdsms.repo.data.SmsMessageDao

@Database(entities = [MessageListItem.SmsMessageModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun smsMessageDao(): SmsMessageDao
}