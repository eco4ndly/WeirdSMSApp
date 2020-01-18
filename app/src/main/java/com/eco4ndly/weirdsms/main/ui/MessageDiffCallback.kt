package com.eco4ndly.weirdsms.main.ui

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.eco4ndly.weirdsms.main.model.MessageListItem

class MessageListDiffCallback(private val oldList: List<MessageListItem>, private val newList: List<MessageListItem>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition] == newList[newPosition]
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}