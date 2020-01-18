package com.eco4ndly.weirdsms.utl

import java.text.SimpleDateFormat
import java.util.*

/**
 * A Sayan Porya code on 2020-01-18
 */

fun getDifferenceInHours(current: Long, timeToCalculate: Long): Int {
    val difference = current - timeToCalculate
    val diffInHours = difference/(1000 * 60 * 60)
    return diffInHours.toInt()
}

fun millisToDateFormat(millis: Long): String {
    /*return DateFormat.getDateInstance(DateFormat.FULL).format(Date(millis))*/
    val sdf = SimpleDateFormat("yyyy-MM-dd hh-mm a", Locale.getDefault())
    return sdf.format(Date(millis))
}