package com.soft_industry.findgift

import android.support.multidex.MultiDexApplication
import com.soft_industry.findgift.dependencies.*

/**
 * Created by user on 4/20/18.
 */

class App : MultiDexApplication() {
    lateinit var component: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
                .appModule(AppModule(this))
                .databaseModule(DatabaseModule(this))
                .placesModule(PlacesModule())
                .repositoriesModule(RepositoriesModule())
                .build()

    }

}
