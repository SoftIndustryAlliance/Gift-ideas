package com.soft_industry.findgift.domain.task.robot

import com.google.android.gms.maps.model.LatLng
import com.soft_industry.findgift.data.repository.TestDataRepositoryImpl
import com.soft_industry.findgift.data.repository.TestLocationRepositoryImpl
import com.soft_industry.findgift.data.repository.TestPlacesRepositoryImpl
import com.soft_industry.findgift.data.repository.TestSettingsRepositoryImpl
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.NearestPlace
import java.util.*


val defaultGift = Gift(1L, "test", "test", "test")
class LoadNearestPlacesByTypeRobot(private val locationRepository: TestLocationRepositoryImpl,
                                   private val placesRepository: TestPlacesRepositoryImpl,
                                   private val settingsRepository: TestSettingsRepositoryImpl,
                                   private val dataRepository: TestDataRepositoryImpl) {
    val bookshopNearestPlaces = listOf(
            NearestPlace(name = "Knyholyb",
                    icon = "icon",
                    latLng = LatLng(1.0,1.0)
            )
    )
    val gymNearestPlaces = listOf(
            NearestPlace(name = "Virtus",
                    icon = "icon",
                    latLng = LatLng(1.0,1.0)
            )
    )
    fun execute() {
        dataRepository.shoptypes.onNext(listOf("bookshop", "gym"))
        locationRepository.relay.onNext(LatLng(100.0,100.0))
        settingsRepository.relay.onNext(Locale.ENGLISH)
        placesRepository.relay.onNext(bookshopNearestPlaces)
        placesRepository.relay.onNext(gymNearestPlaces)

    }

}
