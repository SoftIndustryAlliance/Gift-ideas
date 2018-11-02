package com.soft_industry.findgift.presentation.pages.placedetails

import com.soft_industry.findgift.domain.task.OpenNavigation
import com.soft_industry.findgift.presentation.BaseViewModel
import com.soft_industry.findgift.presentation.StateReducer
import com.soft_industry.findgift.presentation.onErrorReduceState
import com.soft_industry.findgift.presentation.reduceViewState
import io.reactivex.Observable
import io.reactivex.Scheduler

class PlaceDetailsViewModel(private val openNavigation: OpenNavigation, scheduler: Scheduler) : BaseViewModel<PlaceDetailsAction, PlaceDetailsState>(scheduler) {
    override fun route(action: PlaceDetailsAction): Observable<StateReducer<PlaceDetailsState>> {
        return when(action) {
            is PlaceDetailsAction.RenderPlace -> Observable.just(action.place)
                    .reduceViewState { vs, place -> vs.copy(place = place) }
            is PlaceDetailsAction.ShowRoute -> openNavigation.execute(action.location)
                    .toObservable<StateReducer<PlaceDetailsState>>()
                    .onErrorReduceState { state, err ->  state.copy(error = err)}
        }
    }

    override fun createInitialState() = PlaceDetailsState()
}