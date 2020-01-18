package com.eco4ndly.weirdsms.infra

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import javax.inject.Inject

/**
 * A Sayan Porya code on 2020-01-18
 *
 * Didn't require to use permission as the app is being used as the default sms app
 * Keep the class for further development to add other features
 */
class PermissionManager @Inject constructor(private val context: Context) {
  fun hasReadSmsPermission(): Boolean {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
  }

  fun hasSendSmsPermission(): Boolean {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
  }

  fun hasReceieveSmsPermission(): Boolean {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
  }
}