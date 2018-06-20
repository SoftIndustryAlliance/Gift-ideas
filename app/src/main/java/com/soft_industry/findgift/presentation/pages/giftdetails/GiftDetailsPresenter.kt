package com.soft_industry.findgift.presentation.pages.giftdetails

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers


class GiftDetailsPresenter (): MviBasePresenter<GiftDetailsView, GiftDetailsViewState>() {
    override fun bindIntents() {
        val loadedIntent = intent { it.loadedIntent() }
        val intent =loadedIntent.map { GiftDetailsViewStateReducer.GiftLoaded(it) }
                .cast(GiftDetailsViewStateReducer::class.java)
                .scan(GiftDetailsViewState(false, null)) { vs , reducer -> reducer.reduce(vs)}
                .observeOn(AndroidSchedulers.mainThread())
        subscribeViewState(intent, GiftDetailsView::render)

    }
}


