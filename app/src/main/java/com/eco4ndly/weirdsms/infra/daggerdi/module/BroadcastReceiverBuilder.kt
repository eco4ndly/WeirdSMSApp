package com.eco4ndly.weirdsms.infra.daggerdi.module

import com.eco4ndly.weirdsms.receivers.defaultreceiver.DefaultSmsAppChngBroadcastReceiver
import com.eco4ndly.weirdsms.receivers.smsreceiver.SmsReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * A Sayan Porya code on 2020-01-18
 */
@Module
abstract class BroadcastReceiverBuilder {

    @ContributesAndroidInjector()
    abstract fun bindSmsReceiver(): SmsReceiver

    @ContributesAndroidInjector()
    abstract fun bindDefaultAppReveiver(): DefaultSmsAppChngBroadcastReceiver
}