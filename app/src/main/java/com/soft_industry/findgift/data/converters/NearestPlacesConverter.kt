package com.soft_industry.findgift.data.converters

import com.google.android.gms.maps.model.LatLng
import com.soft_industry.findgift.data.dto.NearbySearchItemDTO
import com.soft_industry.findgift.domain.entities.NearestPlace

/**
 * Created by user on 3/23/18.
 */
fun NearbySearchItemDTO.convertToNearestPlace(): NearestPlace {
    val location = this.geometry.location
    val latLng = LatLng(location.lat, location.lng)
    return NearestPlace(this.name, latLng, this.icon)
}