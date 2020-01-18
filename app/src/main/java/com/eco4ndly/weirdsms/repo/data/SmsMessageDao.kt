package com.eco4ndly.weirdsms.repo.data

import android.telephony.SmsMessage
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eco4ndly.weirdsms.main.model.MessageListItem.SmsMessageModel
import kotlinx.coroutines.flow.Flow

/**
 * A Sayan Porya code on 2020-01-18
 */

@Dao
interface SmsMessageDao {
    @Query("SELECT * FROM SmsMessageModel ORDER BY date DESC")
    fun getAll(): Flow<List<SmsMessageModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(messages: List<SmsMessageModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: SmsMessageModel)

}