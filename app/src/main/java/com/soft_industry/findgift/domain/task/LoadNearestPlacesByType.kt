package com.soft_industry.findgift.presentation.task

import com.google.android.gms.maps.model.LatLng
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.NearestPlace
import com.soft_industry.findgift.domain.repository.DataRepository
import com.soft_industry.findgift.domain.repository.LocationRepository
import com.soft_industry.findgift.domain.repository.PlacesRepository
import com.soft_industry.findgift.domain.repository.SettingsRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

/**
 * Created by user on 3/23/18.
 */
class LoadNearestPlacesByType
@Inject constructor(private val locationRepository: LocationRepository,
                    private val placesRepository: PlacesRepository,
                    private val settingsRepository: SettingsRepository,
                    private val dataRepository: DataRepository) {
    fun execute(gift: Gift) = locationRepository.listen()
            .take(1)
            .observeOn(Schedulers.io())
            .switchMap { loc ->
                dataRepository.loadShopTypes(gift)
                        .flatMap { requestPlaces(loc, it) }
            }
            .subscribeOn(Schedulers.io())




    private fun requestPlaces(latlng: LatLng, types: List<String>): Observable<List<NearestPlace>>{
        return settingsRepository.loadUserLanguage()
                .flatMap { lang -> loadPlaces(types, latlng, lang) }

    }

    private fun loadPlaces(types: List<String>, latlng: LatLng, lang: Locale): Observable<MutableList<NearestPlace>>? {
        return Observable.fromIterable(types)
                .flatMap { placesRepository.loadNearestPlaces(it, latlng, lang.language) }
                .observeOn(Schedulers.computation())
                .reduce(mutableListOf<NearestPlace>()) { list, items ->
                    list.addAll(items)
                    return@reduce list
                }
                .toObservable()
    }

}