package com.soft_industry.findgift.dependencies

import androidx.room.Room
import android.content.Context
import com.jakewharton.rxrelay2.BehaviorRelay
import com.soft_industry.findgift.data.datasources.local.AppDatabase
import com.soft_industry.findgift.data.datasources.local.DbCallback
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by user on 4/19/18.
 */
@Singleton
@Module
class DatabaseModule(val context: Context) {
    val DB_NAME = "gift_ideas.db"
    val relay = BehaviorRelay.createDefault(false)
    val dbCallback = DbCallback(context, relay)

    @Provides
    fun provideCallback() = dbCallback

    @Provides
    fun provideAppDatabase(context: Context) : AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .addCallback(dbCallback)
                .build()
    }


    @Provides
    fun provideDataDao(appDatabase: AppDatabase) = appDatabase.dataDao()
}