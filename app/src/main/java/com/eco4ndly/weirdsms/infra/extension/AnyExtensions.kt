package com.eco4ndly.weirdsms.infra.extension

import android.content.Context
import android.provider.Telephony

/**
 * A Sayan Porya code on 2020-01-18
 */

fun Any.isDefaultSmsApp(context: Context): Boolean {
  return Telephony.Sms.getDefaultSmsPackage(context) == context.packageName
}