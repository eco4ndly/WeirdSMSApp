package com.eco4ndly.weirdsms.main.ui.viewholder

import android.view.View
import kotlinx.android.synthetic.main.item_hour_header.view.*

/**
 * A Sayan Porya code on 2020-01-18
 */

class TimeViewHolder(itemView: View): BaseViewHolder(itemView) {
    fun bind(time: String) {
        itemView.tv_hour.text = time
    }
}