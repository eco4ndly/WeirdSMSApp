package com.eco4ndly.weirdsms.infra.daggerdi

import android.app.Application
import com.eco4ndly.weirdsms.app.WeirdApp
import com.eco4ndly.weirdsms.infra.daggerdi.module.ActivityBuilder
import com.eco4ndly.weirdsms.infra.daggerdi.module.AppModule
import com.eco4ndly.weirdsms.infra.daggerdi.module.BroadcastReceiverBuilder
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * A Sayan Porya code on 2020-01-18
 */

@Singleton
@Component(
    modules = [
      AndroidSupportInjectionModule::class,
      AppModule::class,
      ActivityBuilder::class,
      BroadcastReceiverBuilder::class
    ]
)
interface AppComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }

  fun inject(app: WeirdApp)
}