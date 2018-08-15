package com.soft_industry.findgift.presentation.pages.giftselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soft_industry.findgift.presentation.BaseViewModel
import com.soft_industry.findgift.dependencies.RenderScheduler
import com.soft_industry.findgift.domain.task.LoadGifts
import com.soft_industry.findgift.presentation.StateReducer
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject


class GiftSelectionViewModel (private val loadGifts: LoadGifts, private val scheduler: Scheduler)
    : BaseViewModel<GiftSelectionAction, GiftSelectionState>(scheduler) {


    class Factory @Inject constructor(
            private val loadGifts: LoadGifts,
            @RenderScheduler private val scheduler: Scheduler)
        : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GiftSelectionViewModel(loadGifts, scheduler) as T
        }

    }
    override fun route(action: GiftSelectionAction)
            : Observable<StateReducer<GiftSelectionState>> {
        return when(action) {
            is GiftSelectionAction.LoadGiftListAction -> loadGifts(action)
        }
    }

    private fun loadGifts(action: GiftSelectionAction.LoadGiftListAction)
            = loadGifts.execute(action.giftTarget)
                .map<StateReducer<GiftSelectionState>> { GiftSelectionReducer.ContentLoaded(it) }
                .onErrorReturn { GiftSelectionReducer.Error(it) }
                .startWith(GiftSelectionReducer.Loading())


    override fun createInitialState() = GiftSelectionState(true, mutableListOf())
}


