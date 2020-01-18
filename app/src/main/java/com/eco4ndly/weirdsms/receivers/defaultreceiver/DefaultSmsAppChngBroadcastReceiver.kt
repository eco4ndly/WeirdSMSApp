package com.eco4ndly.weirdsms.receivers.defaultreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import androidx.annotation.RequiresApi
import com.eco4ndly.weirdsms.repo.SmsRepository
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * A Sayan Porya code on 2020-01-18
 */
class DefaultSmsAppChngBroadcastReceiver: BroadcastReceiver() {
    @Inject
    lateinit var smsRepository: SmsRepository

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onReceive(p0: Context, intent: Intent) {
        AndroidInjection.inject(this, p0)

        intent.let {
            if (it.getBooleanExtra(Telephony.Sms.Intents.EXTRA_IS_DEFAULT_SMS_APP, false)) {
                smsRepository.syncDatabase()
            }
        }

    }
}
