package com.soft_industry.findgift.dependencies

import android.app.Application
import android.location.LocationManager
import androidx.core.content.systemService
import dagger.Module
import dagger.Provides

/**
 * Created by user on 4/19/18.
 */
@Module
class AppModule(val application: Application) {
    @Provides
    fun provideContext() = application.applicationContext

    @Provides
    fun provideLocationModule() = application.systemService<LocationManager>()

}