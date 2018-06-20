package com.soft_industry.findgift.presentation.pages.randomgift

import com.gojuno.koptional.Optional
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.soft_industry.findgift.domain.entities.GiftTarget
import io.reactivex.Observable

interface RandomGiftView: MvpView {
    fun loadGiftIntent(): Observable<Optional<GiftTarget>>
    fun shakeIntent(): Observable<Boolean>
    fun render(randomGiftViewState: RandomGiftViewState)
}