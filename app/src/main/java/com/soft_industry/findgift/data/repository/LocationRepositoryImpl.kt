package com.soft_industry.findgift.data.repository

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxrelay2.PublishRelay
import com.soft_industry.findgift.domain.repository.LocationRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by user on 3/21/18.
 */

class LocationRepositoryImpl @Inject constructor(private val manager: LocationManager) : LocationRepository {
    private val relay: PublishRelay<LatLng> = PublishRelay.create()
    @SuppressLint("MissingPermission")
    override fun listen(): Observable<LatLng> {
        var listener: LocationListener = object : LocationListener {
            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

            override fun onProviderEnabled(p0: String?) {}

            override fun onProviderDisabled(p0: String?) {}

            override fun onLocationChanged(location: Location?) {
                location?.apply { relay.accept(LatLng(latitude, longitude)) }
            }
        }

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0L, 0f, listener)
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, listener)
        return relay.doOnDispose { manager.removeUpdates(listener) }
    }
}
