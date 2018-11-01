package com.soft_industry.findgift.data.instruments

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.soft_industry.findgift.domain.instruments.Navigation
import javax.inject.Inject
import android.content.Intent
import android.net.Uri


class NavigationImpl @Inject constructor(val context: Context) : Navigation {
    override fun navigateTo(location: LatLng) {
        val gmmIntentUri = Uri.parse("google.navigation:q=${location.latitude},${location.longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        context.startActivity(mapIntent)
    }
}