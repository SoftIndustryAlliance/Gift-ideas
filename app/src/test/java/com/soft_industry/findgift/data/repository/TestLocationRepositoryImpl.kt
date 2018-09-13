package com.soft_industry.findgift.data.repository

import com.google.android.gms.maps.model.LatLng
import com.soft_industry.findgift.domain.repository.LocationRepository
import io.reactivex.subjects.ReplaySubject

class TestLocationRepositoryImpl : LocationRepository {
    val relay = ReplaySubject.create<LatLng>()
    override fun listen() = relay
}