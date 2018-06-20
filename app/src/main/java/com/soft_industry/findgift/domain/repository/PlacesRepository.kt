package com.soft_industry.findgift.domain.repository

import com.google.android.gms.maps.model.LatLng
import com.soft_industry.findgift.domain.entities.NearestPlace
import io.reactivex.Observable

/**
 * Created by user on 3/23/18.
 */
interface PlacesRepository {
    fun loadNearestPlaces(type: String, latLng: LatLng, languageCode: String): Observable<List<NearestPlace>>
}