package com.soft_industry.findgift.presentation.pages.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.NearestPlace
import com.soft_industry.findgift.domain.task.OpenNavigation
import com.soft_industry.findgift.presentation.StateReducer
import com.soft_industry.findgift.presentation.task.LoadNearestPlacesByType
import io.reactivex.Scheduler
import javax.inject.Inject

data class MapState(val loading: Boolean,
                   val places: List<NearestPlace>?,
                   val error: Throwable? = null)

sealed class MapAction {
    data class LoadPlacesForGiftAction(val gift: Gift) : MapAction()
}


sealed class MapReducer: StateReducer<MapState> {
    class LoadedNearestPlaces(val data: List<NearestPlace>): MapReducer() {
        override fun reduce(old: MapState)
                = old.copy(loading = false, places = old.places?: listOf<NearestPlace>() + data, error = null)
    }
    class Error(val error: Throwable) :MapReducer(){
        override fun reduce(old: MapState)
                = old.copy(loading = false, places = old.places, error = error)
    }

    object Loading : MapReducer() {
        override fun reduce(old: MapState) = old.copy(loading = true, places = old.places, error = null)
    }

}


class MapViewModelFactory @Inject constructor(
        private val loadNearestPlacesByType: LoadNearestPlacesByType,
        val openNavigation: OpenNavigation,
        private val scheduler: Scheduler
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapViewModel(loadNearestPlacesByType,openNavigation, scheduler) as T
    }

}