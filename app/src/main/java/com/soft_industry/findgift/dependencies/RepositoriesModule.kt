package com.soft_industry.findgift.dependencies

import com.soft_industry.findgift.data.repository.DataRepositoryImpl
import com.soft_industry.findgift.data.repository.GooglePlacesRepository
import com.soft_industry.findgift.data.repository.LocationRepositoryImpl
import com.soft_industry.findgift.data.repository.SettingsRepositoryImpl
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
    fun providPlacesRepository(placesRepository: GooglePlacesRepository): PlacesRepository {
        return placesRepository
    }

    @Provides
    fun providSettingsRepository(settingsRepository: SettingsRepositoryImpl) : SettingsRepository {
        return settingsRepository
    }

}