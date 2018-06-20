package com.soft_industry.findgift.presentation.pages.giftselection

import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.presentation.StateReducer

/**
 * Created by user on 3/28/18.
 */
sealed class LoadGiftsStateReducer : StateReducer<GiftSelectionViewState> {
    class Loading(): LoadGiftsStateReducer() {
        override fun reduce(oldViewState: GiftSelectionViewState): GiftSelectionViewState {
            return GiftSelectionViewState(true, oldViewState.content)
        }
    }
    class ContentLoaded(val content: List<Gift>) : LoadGiftsStateReducer() {
        override fun reduce(oldViewState: GiftSelectionViewState): GiftSelectionViewState {
            return GiftSelectionViewState(false, content)
        }
    }

    class Error(val error: Throwable) : LoadGiftsStateReducer() {
        override fun reduce(oldViewState: GiftSelectionViewState): GiftSelectionViewState {
            return GiftSelectionViewState(false, oldViewState.content, error)
        }

    }

}