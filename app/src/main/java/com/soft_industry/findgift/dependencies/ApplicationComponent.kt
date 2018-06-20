package com.soft_industry.findgift.dependencies

import com.soft_industry.findgift.data.datasources.local.DataDao
import com.soft_industry.findgift.data.datasources.remote.PlacesService
import com.soft_industry.findgift.domain.repository.DataRepository
import com.soft_industry.findgift.domain.repository.LocationRepository
import com.soft_industry.findgift.domain.repository.PlacesRepository
import com.soft_industry.findgift.domain.repository.SettingsRepository
import com.soft_industry.findgift.presentation.pages.giftselection.GiftSelectionFragment
import com.soft_industry.findgift.presentation.pages.map.MapActivity
import com.soft_industry.findgift.presentation.pages.randomgift.RandomComponent
import com.soft_industry.findgift.presentation.pages.randomgift.RandomModule
import com.soft_industry.findgift.presentation.pages.splash.SplashComponent
import com.soft_industry.findgift.presentation.pages.splash.SplashModule
import com.soft_industry.findgift.presentation.pages.targetselection.TargetSelectionFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by user on 4/20/18.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, DatabaseModule::class, PlacesModule::class, RepositoriesModule::class))
interface ApplicationComponent {
    fun getDataRepository(): DataRepository
    fun getLocationRepository(): LocationRepository
    fun getPlacesRepository(): PlacesRepository
    fun getSettingsRepository(): SettingsRepository

    fun getPlacesKey(): String
    fun getPlacesService(): PlacesService
    fun getDao(): DataDao

    fun splashComponent(splashModule: SplashModule): SplashComponent
    fun randomComponent(randomModule: RandomModule): RandomComponent

    fun inject(giftSelectionFragment: GiftSelectionFragment)
    fun inject(mapActivity: MapActivity)
    fun inject(targetSelectionFragment: TargetSelectionFragment)
}