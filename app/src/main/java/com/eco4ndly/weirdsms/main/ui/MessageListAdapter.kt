package com.eco4ndly.weirdsms.main.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eco4ndly.weirdsms.R
import com.eco4ndly.weirdsms.main.model.MessageListItem
import com.eco4ndly.weirdsms.main.ui.viewholder.BaseViewHolder
import com.eco4ndly.weirdsms.main.ui.viewholder.MessageItemViewHolder
import com.eco4ndly.weirdsms.main.ui.viewholder.TimeViewHolder
import java.lang.Exception
import java.lang.RuntimeException

/**
 * A Sayan Porya code on 2020-01-18
 */

class MessageListAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    var messageModelList: MutableList<MessageListItem> = ArrayList()
        set(value) {
            if(field == value) return

            val diffCallback = MessageListDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field.clear()
            field.addAll(value)
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val vh = when (viewType) {
            MESSAGE_TYPE -> {
                 layoutInflater.inflate(R.layout.item_sms_message, parent, false).run { MessageItemViewHolder(this) }
            }
            TIME_TYPE -> {
                layoutInflater.inflate(R.layout.item_hour_header, parent, false).run { TimeViewHolder(this) }
            }
            else -> null
        }
        if(vh != null) {
            return vh
        }
        throw RuntimeException("Unknown view type")
    }

    override fun getItemCount(): Int {
        return messageModelList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(messageModelList[position]) {
            is MessageListItem.SmsMessageModel -> MESSAGE_TYPE
            is MessageListItem.TimeTypeItem -> TIME_TYPE
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is MessageItemViewHolder) {
            holder.bind(messageModelList[position] as MessageListItem.SmsMessageModel)
        } else if(holder is TimeViewHolder) {
            holder.bind((messageModelList[position] as MessageListItem.TimeTypeItem).time)
        }
    }

    companion object {
        const val MESSAGE_TYPE = 99
        const val TIME_TYPE = 18
    }
}