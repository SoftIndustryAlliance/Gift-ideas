package com.soft_industry.findgift.presentation.pages.map

import com.soft_industry.findgift.presentation.BaseViewModel
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.task.OpenNavigation
import com.soft_industry.findgift.presentation.StateReducer
import com.soft_industry.findgift.presentation.onErrorReduceState
import com.soft_industry.findgift.presentation.task.LoadNearestPlacesByType
import io.reactivex.Observable
import io.reactivex.Scheduler


class MapViewModel(private val loadNearestPlacesByType: LoadNearestPlacesByType,
                   private val openNavigation: OpenNavigation,
                    scheduler: Scheduler)
    : BaseViewModel<MapAction, MapState>(scheduler) {



    override fun createInitialState() = MapState(false, null)

    override fun route(action: MapAction): Observable<StateReducer<MapState>> {
        return when(action){
            is MapAction.LoadPlacesForGiftAction -> loadPlaces(action.gift)
        }
    }
    private fun loadPlaces(gift: Gift): Observable<StateReducer<MapState>> {
        return loadNearestPlacesByType.execute(gift)
                .map<StateReducer<MapState>> { MapReducer.LoadedNearestPlaces(it) }
                .startWith(MapReducer.Loading)
                .onErrorReturn { MapReducer.Error(it) }
    }
}


