package com.soft_industry.findgift.dependencies

import com.soft_industry.findgift.data.instruments.NavigationImpl
import com.soft_industry.findgift.data.instruments.NavigationImpl_Factory
import com.soft_industry.findgift.data.repository.*
import com.soft_industry.findgift.domain.instruments.Navigation
import com.soft_industry.findgift.domain.repository.DataRepository
import com.soft_industry.findgift.domain.repository.LocationRepository
import com.soft_industry.findgift.domain.repository.PlacesRepository
import com.soft_industry.findgift.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides

/**
 * Created by user on 4/19/18.
 */
@Module 
 class RepositoriesModule {
    @Provides
    fun providDataRepository(dataRepository: DataRepositoryImpl): DataRepository {
        return dataRepository
    }

    @Provides
    fun providLocationRepository(locationRepository: LocationRepositoryImpl): LocationRepository {
        return locationRepository
    }

    @Provides
    fun providPlacesRepository(placesRepository: FacebookPlacesRepository): PlacesRepository {
        return placesRepository
    }

    @Provides
    fun providSettingsRepository(settingsRepository: SettingsRepositoryImpl) : SettingsRepository {
        return settingsRepository
    }

    @Provides
    fun provideNavigation(navigation : NavigationImpl) : Navigation = navigation
}