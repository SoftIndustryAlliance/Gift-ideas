package com.soft_industry.findgift.presentation.pages.giftselection

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.soft_industry.findgift.domain.task.LoadGifts
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class GiftSelectionPresenter @Inject constructor(private val loadGifts: LoadGifts) : MviBasePresenter<GiftSelectionView, GiftSelectionViewState>() {
    override fun bindIntents() {
        val initialState = GiftSelectionViewState(true, mutableListOf())
        val intent = intent(GiftSelectionView::loadGiftList)
                .switchMap { loadGifts.execute(it) }
                .scan(initialState) { oldState, reducer -> reducer.reduce(oldState) }
                .observeOn(AndroidSchedulers.mainThread())
        subscribeViewState(intent, GiftSelectionView::render)
    }
}


