package com.soft_industry.findgift.data.dto

import java.util.*

/**
 * Created by user on 3/21/18.
 */
data class NearbysearchDTO(val results: ArrayList<NearbySearchItemDTO>, val error_message: String)
data class NearbySearchItemDTO(val geometry: Geometry, val name: String, val icon: String)
data class Geometry(val location: LocationDTO)
data class LocationDTO(val lat: Double, val lng: Double)