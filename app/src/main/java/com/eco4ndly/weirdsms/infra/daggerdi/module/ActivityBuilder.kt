package com.eco4ndly.weirdsms.infra.daggerdi.module

import com.eco4ndly.weirdsms.infra.daggerdi.scope.PerActivity
import com.eco4ndly.weirdsms.main.ui.MainActivity
import com.eco4ndly.weirdsms.main.di.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * A Sayan Porya code on 2020-01-18
 */

@Module
abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector(
        modules = [MainActivityModule::class]
    )
    abstract fun bindMainActivity(): MainActivity
}