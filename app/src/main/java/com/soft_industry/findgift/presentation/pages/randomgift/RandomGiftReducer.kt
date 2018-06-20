package com.soft_industry.findgift.presentation.pages.randomgift

import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.presentation.StateReducer

sealed class RandomGiftReducer : StateReducer<RandomGiftViewState> {
    object Loading: RandomGiftReducer() {
        override fun reduce(oldViewState: RandomGiftViewState) = oldViewState.copy(loading = true)
    }

    class LoadingSucceded(val gift: Gift): RandomGiftReducer() {
        override fun reduce(oldViewState: RandomGiftViewState) = oldViewState.copy(gift = gift)
    }

    class GotError(val err: Throwable): RandomGiftReducer() {
        override fun reduce(oldViewState: RandomGiftViewState) = oldViewState.copy(error = err)
    }

    class LabelLoaded(val label: String?): RandomGiftReducer() {
        override fun reduce(oldViewState: RandomGiftViewState) = oldViewState.copy(targetLabel = label.orEmpty())

    }

    object ShowGift : RandomGiftReducer() {
        override fun reduce(oldViewState: RandomGiftViewState) = oldViewState.copy(showGift =  true, animateShake = false)

    }
    object AniamteShake : RandomGiftReducer() {
        override fun reduce(oldViewState: RandomGiftViewState) = oldViewState.copy(animateShake =  true, showGift = false)
    }

}