package com.soft_industry.findgift.presentation.pages.giftselection

import com.hannesdorfmann.mosby3.mvp.MvpView
import com.soft_industry.findgift.domain.entities.GiftTarget
import io.reactivex.Observable


interface GiftSelectionView : MvpView {
    fun loadGiftList() :Observable<GiftTarget>
    fun render(state: GiftSelectionViewState)
}


