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
            .concatMap { loc ->  //concatMap -используем что бы последовательно создать следующий обзервабл используя результат предыдущего
                dataRepository.loadShopTypes(gift)  //создаем обзевабл получения тайпов
                        .flatMap { requestPlaces(loc, it) }  // делаем это здесь, а не в следующей цепочке так как у нас есть результаты и получения локации и  получения тайпов
            }




    private fun requestPlaces(latlng: LatLng, types: List<String>): Observable<List<NearestPlace>>{
        return settingsRepository.loadUserLanguage() //сперва получим язык (Реактивненько конечно
                .flatMap { lang -> loadPlaces(types, latlng, lang) }

    }

    private fun loadPlaces(types: List<String>, latlng: LatLng, lang: Locale): Observable<List<NearestPlace>> {
        return Observable.fromIterable(types) //обрабатываем каждый тайп
                .concatMap { placesRepository.loadNearestPlaces(it, latlng, lang.language) } // concatMap -последовательно
                .observeOn(Schedulers.computation())

    }

}