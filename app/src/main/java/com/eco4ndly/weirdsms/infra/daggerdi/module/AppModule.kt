package com.eco4ndly.weirdsms.infra.daggerdi.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.eco4ndly.weirdsms.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * A Sayan Porya code on 2020-01-18
 */
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(app: Application): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java, "weird-sms-db"
        ).build()
    }

}