package com.soft_industry.findgift.presentation.pages.giftdetails

import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.presentation.StateReducer

interface GiftDetailsContract{
    data class State(val loading: Boolean,
                val gift: Gift?,
                val error: Throwable? = null)

    sealed class Reducer : StateReducer<GiftDetailsContract.State> {
        class GiftLoaded(val gift: Gift): Reducer() {
            override fun reduce(old: GiftDetailsContract.State)
                    = GiftDetailsContract.State(loading = false, gift = gift, error = null)

        }
        class Loading: Reducer() {
            override fun reduce(old: GiftDetailsContract.State)
                    = old.copy(loading = true, gift = old.gift, error = null)
        }

        class Error(val error: Throwable): Reducer() {
            override fun reduce(old: GiftDetailsContract.State)
                    = old.copy(loading = false, gift = old.gift, error =  error)
        }
    }

    sealed class Action {
        data class LoadGiftAction(val gift: Gift): Action()
    }
}