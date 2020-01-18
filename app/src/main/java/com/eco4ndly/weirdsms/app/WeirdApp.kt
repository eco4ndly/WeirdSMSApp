package com.eco4ndly.weirdsms.app

import android.app.Application
import com.eco4ndly.weirdsms.infra.daggerdi.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * A Sayan Porya code on 2020-01-18
 */
class WeirdApp: Application(), HasAndroidInjector {
  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

  override fun onCreate() {
    super.onCreate()
    DaggerAppComponent
      .builder()
      .application(this)
      .build()
      .inject(this)

  }

  override fun androidInjector(): AndroidInjector<Any> {
    return dispatchingAndroidInjector
  }
}