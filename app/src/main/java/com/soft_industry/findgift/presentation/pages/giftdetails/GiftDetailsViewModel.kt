package com.soft_industry.findgift.presentation.pages.giftdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soft_industry.findgift.presentation.BaseViewModel
import com.soft_industry.findgift.presentation.StateReducer
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers


class GiftDetailsViewModel (scheduler: Scheduler)
    : BaseViewModel<GiftDetailsContract.Action, GiftDetailsContract.State>(scheduler) {

    object Factory: ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return GiftDetailsViewModel(AndroidSchedulers.mainThread()) as T
        }
    }
    override fun route(action: GiftDetailsContract.Action)
            : Observable<StateReducer<GiftDetailsContract.State>> {
        return when(action) {
            is GiftDetailsContract.Action.LoadGiftAction -> handleLoadedGift(action)
        }
    }

    private fun handleLoadedGift(action: GiftDetailsContract.Action.LoadGiftAction)
            : Observable<StateReducer<GiftDetailsContract.State>>
            = Observable.just(GiftDetailsContract.Reducer.GiftLoaded(action.gift))


    override fun createInitialState() = GiftDetailsContract.State(false, null)

}


