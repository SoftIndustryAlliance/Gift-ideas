package com.soft_industry.findgift.presentation.pages.giftdetails

import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.presentation.StateReducer

/**
 * Created by user on 4/13/18.
 */
sealed class GiftDetailsViewStateReducer : StateReducer<GiftDetailsViewState> {
    class GiftLoaded(val gift: Gift): GiftDetailsViewStateReducer() {
        override fun reduce(old: GiftDetailsViewState): GiftDetailsViewState {
            return GiftDetailsViewState(false, gift, null)
        }

    }
    class Loading: GiftDetailsViewStateReducer() {
        override fun reduce(old: GiftDetailsViewState): GiftDetailsViewState {
            return GiftDetailsViewState(true, old.gift, null)
        }
    }

    class Error(val error: Throwable): GiftDetailsViewStateReducer() {
        override fun reduce(old: GiftDetailsViewState): GiftDetailsViewState {
            return GiftDetailsViewState(old.loading, old.gift, error)
        }
    }
}