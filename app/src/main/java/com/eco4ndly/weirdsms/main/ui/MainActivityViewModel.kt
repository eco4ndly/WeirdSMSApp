package com.eco4ndly.weirdsms.main.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eco4ndly.weirdsms.R
import com.eco4ndly.weirdsms.base.BaseViewModel
import com.eco4ndly.weirdsms.infra.extension.launchUI
import com.eco4ndly.weirdsms.main.model.MessageListItem
import com.eco4ndly.weirdsms.main.model.MessageListItem.TimeTypeItem
import com.eco4ndly.weirdsms.main.ui.MainActivityViewModel.Groups.HR_0
import com.eco4ndly.weirdsms.main.ui.MainActivityViewModel.Groups.HR_1
import com.eco4ndly.weirdsms.main.ui.MainActivityViewModel.Groups.HR_12
import com.eco4ndly.weirdsms.main.ui.MainActivityViewModel.Groups.HR_1D
import com.eco4ndly.weirdsms.main.ui.MainActivityViewModel.Groups.HR_2
import com.eco4ndly.weirdsms.main.ui.MainActivityViewModel.Groups.HR_3
import com.eco4ndly.weirdsms.main.ui.MainActivityViewModel.Groups.HR_6
import com.eco4ndly.weirdsms.main.ui.MainActivityViewModel.Groups.IGNORE
import com.eco4ndly.weirdsms.main.ui.MainActivityViewModel.Groups.NONE
import com.eco4ndly.weirdsms.repo.SmsRepository
import com.eco4ndly.weirdsms.utl.getDifferenceInHours
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * A Sayan Porya code on 2020-01-18
 */
class MainActivityViewModel @Inject constructor(
  private val application: Application,
  private val smsRepository: SmsRepository
) : BaseViewModel() {
  private var offset = 0
  private var previousDiff: Int = -1
  private var previousGroup: Groups = NONE

  private val _messageModelLiveData: MutableLiveData<MutableList<MessageListItem>> =
    MutableLiveData()

  fun messageListLiveData(): LiveData<MutableList<MessageListItem>> = _messageModelLiveData

  /**
   * Gets message list
   */
  /*fun getSmsMessages() {
    _showLoaderLiveData.call()
    viewModelScope.launch {
      smsRepository.getSmsMessages(offset, DEFAULT_LIMIT)
        .let {
          val currentTimeInMill: Long = System.currentTimeMillis()
          val messageList: MutableList<MessageListItem> = mutableListOf()
          it.forEach { smsModel: MessageListItem.SmsMessageModel ->
            val currentDiff = getDifferenceInHours(currentTimeInMill, smsModel.date)
            if (currentDiff > 24) {
              return@forEach
            }
            if (currentDiff != previousDiff) {
              when (currentDiff) {
                in 0..3 -> messageList.add(TimeTypeItem(application.resources.getQuantityString(R.plurals.hours_ago, currentDiff, currentDiff)))
                in 4..6 -> messageList.add(TimeTypeItem(application.resources.getQuantityString(R.plurals.hours_ago, 6, 6)))
                in 7..12 -> messageList.add(TimeTypeItem(application.resources.getQuantityString(R.plurals.hours_ago, 12, 12)))
                in 13..24 -> messageList.add(TimeTypeItem(application.getString(R.string.one_day)))
                else -> messageList.add(TimeTypeItem("More than 24 hrs"))
              }
              previousDiff = currentDiff
            }
            messageList.add(smsModel)
          }
          _hideLoaderLiveData.call()
          if (messageList.isEmpty()) {
            hasMessage = false
            _showToastLiveData.postValue(application.getString(R.string.no_more_message))
          } else {
            hasMessage = true
            offset += DEFAULT_LIMIT
            _messageModelLiveData.postValue(messageList)
          }
        }
    }
  }*/


  fun getMessagesV2() {
    viewModelScope.launch {
      smsRepository.getMessagesV2().collect {
        val currentTimeInMill: Long = System.currentTimeMillis()
        val messageList: MutableList<MessageListItem> = mutableListOf()
        it
          .distinct()
          .forEach { smsModel: MessageListItem.SmsMessageModel ->
          val currentGroup: Groups = getDifferenceInHours(currentTimeInMill, smsModel.date).run {
            groupMapper(this)
          }
          if (currentGroup == IGNORE) {
            return@forEach
          }
          if (currentGroup != previousGroup) {
            when (currentGroup) {
              HR_0 -> messageList.add(TimeTypeItem(application.resources.getQuantityString(R.plurals.hours_ago, 0, 0)))
              HR_1 -> messageList.add(TimeTypeItem(application.resources.getQuantityString(R.plurals.hours_ago, 1, 1)))
              HR_2 -> messageList.add(TimeTypeItem(application.resources.getQuantityString(R.plurals.hours_ago, 2, 2)))
              HR_3 -> messageList.add(TimeTypeItem(application.resources.getQuantityString(R.plurals.hours_ago, 3, 3)))
              HR_6 -> messageList.add(TimeTypeItem(application.resources.getQuantityString(R.plurals.hours_ago, 6, 6)))
              HR_12 -> messageList.add(TimeTypeItem(application.resources.getQuantityString(R.plurals.hours_ago, 12, 12)))
              HR_1D -> messageList.add(TimeTypeItem(application.getString(R.string.one_day)))
              else -> messageList.add(TimeTypeItem("More than 24 hrs"))
            }
            previousGroup = currentGroup
          }
          messageList.add(smsModel)
        }
        _hideLoaderLiveData.call()
        if (messageList.isNotEmpty()) {
          offset += DEFAULT_LIMIT
          _messageModelLiveData.postValue(messageList)
        }
      }
    }
  }

  fun syncDatabase() {
    smsRepository.syncDatabase()
  }

  companion object {
    const val DEFAULT_LIMIT = 200
  }

  private fun groupMapper(hour: Int): Groups {
    return when (hour) {
      0 -> HR_0
      1 -> HR_1
      2 -> HR_2
      3 -> HR_3
      in 4..6 -> HR_6
      in 7..12 -> HR_12
      in 13..24 -> HR_1D
      else -> IGNORE
    }
  }

  enum class Groups {
    NONE,HR_0, HR_1, HR_2, HR_3, HR_6, HR_12, HR_1D, IGNORE
  }
}