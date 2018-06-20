package com.soft_industry.findgift.presentation.pages.map

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.soft_industry.findgift.presentation.task.LoadNearestPlacesByType
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class MapPresenter
@Inject constructor(val loadNearestPlacesByType: LoadNearestPlacesByType)
    : MviBasePresenter<MapView, MapViewState>() {
    override fun bindIntents() {

        val intent = intent { it.loadPlacesForGift() }
                .switchMap { loadNearestPlacesByType.execute(it) }
                .scan(MapViewState(false, null)) { vs, reducer -> reducer.reduce(vs) }
                .observeOn(AndroidSchedulers.mainThread())
        subscribeViewState(intent, MapView::render)
    }
}


