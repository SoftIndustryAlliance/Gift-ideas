package com.soft_industry.findgift.presentation.pages.map

import com.google.android.gms.maps.model.LatLng
import com.soft_industry.findgift.presentation.BaseViewModel
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.presentation.StateReducer
import com.soft_industry.findgift.presentation.reduceViewState
import com.soft_industry.findgift.presentation.task.LoadCurrentPosition
import com.soft_industry.findgift.presentation.task.LoadNearestPlacesByType
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Scheduler


class MapViewModel(private val loadNearestPlacesByType: LoadNearestPlacesByType,
                   private val loadCurrentPosition: LoadCurrentPosition,
                    scheduler: Scheduler)
    : BaseViewModel<MapAction, MapState>(scheduler) {



    override fun createInitialState() = MapState(false, null)

    override fun route(action: MapAction): Observable<StateReducer<MapState>> {
        return when(action){
            is MapAction.LoadPlacesForGiftAction -> load(action)
        }
    }

    private fun load(action: MapAction.LoadPlacesForGiftAction): Observable<StateReducer<MapState>> {
        return Observable.merge(loadCurrentPosition(), loadPlaces(action.gift))
                .startWith(MapReducer.Loading)
                .onErrorReturn { MapReducer.Error(it) }
    }

    private fun loadCurrentPosition(): ObservableSource<StateReducer<MapState>> {
        return loadCurrentPosition.execute()
                .reduceViewState<LatLng, MapState> { vs, latLng -> vs.copy(currentPlace = latLng) }
    }

    private fun loadPlaces(gift: Gift): Observable<StateReducer<MapState>> {
        return loadNearestPlacesByType.execute(gift)
                .map<StateReducer<MapState>> { MapReducer.LoadedNearestPlaces(it) }
    }
}


