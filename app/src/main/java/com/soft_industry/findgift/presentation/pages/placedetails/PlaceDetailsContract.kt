package com.soft_industry.findgift.presentation.pages.placedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.soft_industry.findgift.domain.entities.NearestPlace
import com.soft_industry.findgift.domain.task.OpenNavigation
import com.soft_industry.findgift.presentation.BaseViewModel
import com.soft_industry.findgift.presentation.StateReducer
import com.soft_industry.findgift.presentation.onErrorReduceState
import com.soft_industry.findgift.presentation.pages.map.MapViewModel
import com.soft_industry.findgift.presentation.reduceViewState
import com.soft_industry.findgift.presentation.task.LoadNearestPlacesByType
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

data class PlaceDetailsState(val showProgressBar: Boolean = false, val place: NearestPlace? = null, val error: Throwable? = null)

sealed class PlaceDetailsAction {
    data class RenderPlace(val place: NearestPlace) :PlaceDetailsAction()
    data class ShowRoute(val location: LatLng) : PlaceDetailsAction()
    //here could be some save actions etc
}



class PlaceDetailsViewModelFactory @Inject constructor(
        private val loadNearestPlacesByType: LoadNearestPlacesByType,
        val openNavigation: OpenNavigation,
        private val scheduler: Scheduler
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlaceDetailsViewModel(openNavigation, scheduler) as T
    }

}