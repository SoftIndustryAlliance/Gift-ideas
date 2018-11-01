package com.soft_industry.findgift.domain.instruments

import com.google.android.gms.maps.model.LatLng

interface Navigation {
    fun navigateTo(location: LatLng)
}