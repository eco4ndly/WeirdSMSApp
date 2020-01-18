package com.eco4ndly.weirdsms.main.ui.viewholder

import android.view.View
import com.eco4ndly.weirdsms.main.model.MessageListItem
import com.eco4ndly.weirdsms.utl.millisToDateFormat
import kotlinx.android.synthetic.main.item_sms_message.view.*

/**
 * A Sayan Porya code on 2020-01-18
 */
class MessageItemViewHolder(itemView: View): BaseViewHolder(itemView) {
    fun bind(smsMessageModel: MessageListItem.SmsMessageModel) {
        itemView.tv_sender_.text = smsMessageModel.sender
        itemView.tv_message_body.text = smsMessageModel.body
        itemView.tv_date.text = millisToDateFormat(smsMessageModel.date)
    }
}