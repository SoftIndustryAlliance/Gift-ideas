package com.soft_industry.findgift.presentation.pages.map

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.soft_industry.findgift.domain.entities.Gift
import io.reactivex.Observable


interface MapView : MvpView {
    fun loadPlacesForGift(): Observable<Gift>
    fun render(state: MapViewState)
}


