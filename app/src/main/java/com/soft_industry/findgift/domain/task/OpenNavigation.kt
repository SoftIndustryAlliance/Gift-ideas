package com.soft_industry.findgift.domain.task

import com.google.android.gms.maps.model.LatLng
import com.soft_industry.findgift.domain.instruments.Navigation
import io.reactivex.Completable
import javax.inject.Inject

class OpenNavigation @Inject constructor(private val navigation: Navigation) {
    fun execute(location: LatLng): Completable {
        return Completable.defer {
            Completable.fromAction { navigation.navigateTo(location) }
        }
    }
}