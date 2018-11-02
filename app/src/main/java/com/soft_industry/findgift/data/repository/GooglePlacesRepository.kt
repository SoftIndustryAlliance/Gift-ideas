package com.soft_industry.findgift.data.repository

import com.google.android.gms.maps.model.LatLng
import com.soft_industry.findgift.data.converters.convertToNearestPlace
import com.soft_industry.findgift.data.datasources.remote.PlacesService
import com.soft_industry.findgift.domain.entities.NearestPlace
import com.soft_industry.findgift.domain.repository.PlacesRepository
import io.reactivex.Observable
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by user on 3/23/18.
 */
class GooglePlacesRepository @Inject constructor(private val placesService: PlacesService,
                                                 private val placesApiKey: String)
    : PlacesRepository {
    override fun loadNearestPlaces(type: String, latLng: LatLng, languageCode: String)
            : Observable<List<NearestPlace>> {
        return placesService.getNearesLocationsByType(
                latlng = "${latLng.latitude},${latLng.longitude}",
                type = type,
                language = languageCode,
                key = placesApiKey
        )
                .map { result ->
                    if(result.error_message.isNotEmpty()) throw Exception(result.error_message)
                    else result.results
                }
                .flatMapIterable { it }
                .map{ it.convertToNearestPlace()}
                .toList()
                .toObservable()
    }

}