package com.soft_industry.findgift.presentation.pages.giftselection

import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.presentation.StateReducer

data class GiftSelectionState(val loading: Boolean,
                             val content: List<Gift>,
                             val error: Throwable? = null)

interface GiftSelectionRenderer {
    fun render(state: GiftSelectionState)
}
sealed class GiftSelectionAction {
    data class LoadGiftListAction(val giftTarget: GiftTarget): GiftSelectionAction()
}

sealed class GiftSelectionReducer : StateReducer<GiftSelectionState> {
    class Loading: GiftSelectionReducer() {
        override fun reduce(old: GiftSelectionState)
                = old.copy(loading = true, content = old.content)
    }
    class ContentLoaded(val content: List<Gift>) : GiftSelectionReducer() {
        override fun reduce(old: GiftSelectionState) = old.copy(loading = false, content = content)
    }

    class Error(val error: Throwable) : GiftSelectionReducer() {
        override fun reduce(old: GiftSelectionState)
                = old.copy(loading = false, content = old.content, error = error)

    }

}