package com.soft_industry.findgift.presentation.pages.map

import com.soft_industry.findgift.domain.entities.NearestPlace
import com.soft_industry.findgift.presentation.StateReducer

/**
 * Created by user on 4/13/18.
 */
sealed class MapViewStateReducer: StateReducer<MapViewState> {
    class LoadedNearestPlaces(val data: List<NearestPlace>): MapViewStateReducer() {
        override fun reduce(oldViewState: MapViewState): MapViewState {
            return MapViewState(false, data )
        }
    }
    class Error(val error: Throwable) :MapViewStateReducer(){
        override fun reduce(oldViewState: MapViewState): MapViewState {
            return MapViewState(false, oldViewState.places, error)
        }
    }

    class Loading: MapViewStateReducer(){
        override fun reduce(oldViewState: MapViewState): MapViewState {
            return MapViewState(true, oldViewState.places, oldViewState.error)
        }
    }

}