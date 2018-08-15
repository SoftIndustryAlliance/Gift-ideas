package com.soft_industry.findgift.dependencies

import android.app.Application
import android.location.LocationManager
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by user on 4/19/18.
 */
@Module
class AppModule(val application: Application) {
    @Provides
    fun provideContext() = application.applicationContext

    @Provides
    fun provideLocationModule() = application.applicationContext.getSystemService(LocationManager::class.java)

    @Provides
    @RenderScheduler
    @Singleton
    fun provideRenderScheduler() = AndroidSchedulers.mainThread()
}
@Named
annotation class RenderScheduler
