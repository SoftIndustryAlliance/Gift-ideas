package com.soft_industry.findgift

import androidx.multidex.MultiDexApplication
import com.facebook.FacebookSdk
import com.soft_industry.findgift.dependencies.*

/**
 * Created by user on 4/20/18.
 */

class App : MultiDexApplication() {
    lateinit var component: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        FacebookSdk.setClientToken(getString(R.string.facebook_client_token))

        component = DaggerApplicationComponent.builder()
                .appModule(AppModule(this))
                .databaseModule(DatabaseModule(this))
                .placesModule(PlacesModule())
                .repositoriesModule(RepositoriesModule())
                .build()

    }

}
