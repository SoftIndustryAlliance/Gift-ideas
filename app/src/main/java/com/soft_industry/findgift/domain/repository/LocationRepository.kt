package com.soft_industry.findgift.domain.repository

import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable

/**
 * Created by user on 3/22/18.
 */
interface LocationRepository {
    fun listen(): Observable<LatLng>
}