package com.eco4ndly.weirdsms.main.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.eco4ndly.weirdsms.infra.daggerdi.vm.ViewModelFactory
import com.eco4ndly.weirdsms.infra.daggerdi.scope.PerActivity
import com.eco4ndly.weirdsms.main.ui.MainActivityViewModel
import com.eco4ndly.weirdsms.main.ui.MessageListAdapter
import com.eco4ndly.weirdsms.repo.SmsRepository
import dagger.Module
import dagger.Provides

/**
 * A Sayan Porya code on 2020-01-18
 */

@Module
class MainActivityModule {

  @Provides
  @PerActivity
  fun provideMainActivityViewModel(
    smsRepository: SmsRepository,
    application: Application
  ): MainActivityViewModel {
    return MainActivityViewModel(application, smsRepository)
  }

  @Provides
  @PerActivity
  fun providesMainViewModelFactory(mainActivityViewModel: MainActivityViewModel): ViewModelProvider.Factory {
    return ViewModelFactory(
        mainActivityViewModel
    )
  }

  @Provides
  @PerActivity
  fun provideMessageListAdapter(): MessageListAdapter {
    return MessageListAdapter()
  }
}