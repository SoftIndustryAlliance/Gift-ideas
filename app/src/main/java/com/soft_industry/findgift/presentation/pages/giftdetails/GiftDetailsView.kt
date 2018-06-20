package com.soft_industry.findgift.presentation.pages.giftdetails

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.soft_industry.findgift.domain.entities.Gift
import io.reactivex.Observable


interface GiftDetailsView : MvpView {
    fun loadedIntent(): Observable<Gift>
    fun render(state: GiftDetailsViewState)
}