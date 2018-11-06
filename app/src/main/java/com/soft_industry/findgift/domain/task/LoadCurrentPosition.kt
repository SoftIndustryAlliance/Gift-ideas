package com.soft_industry.findgift.presentation.task

import com.google.android.gms.maps.model.LatLng
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.NearestPlace
import com.soft_industry.findgift.domain.repository.DataRepository
import com.soft_industry.findgift.domain.repository.LocationRepository
import com.soft_industry.findgift.domain.repository.PlacesRepository
import com.soft_industry.findgift.domain.repository.SettingsRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by user on 3/23/18.
 */
class LoadCurrentPosition
@Inject constructor(private val locationRepository: LocationRepository) {
    fun execute() = locationRepository.listen()
            .take(1)
            .observeOn(Schedulers.io())

}