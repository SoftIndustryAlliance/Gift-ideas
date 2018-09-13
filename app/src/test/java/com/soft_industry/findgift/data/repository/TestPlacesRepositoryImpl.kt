package com.soft_industry.findgift.data.repository

import com.google.android.gms.maps.model.LatLng
import com.soft_industry.findgift.domain.entities.NearestPlace
import com.soft_industry.findgift.domain.repository.PlacesRepository
import io.reactivex.subjects.ReplaySubject

class TestPlacesRepositoryImpl : PlacesRepository {
    val relay = ReplaySubject.create<List<NearestPlace>>()
    override fun loadNearestPlaces(type: String, latLng: LatLng, languageCode: String) = relay
}