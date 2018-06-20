package com.soft_industry.findgift.domain.task

import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.domain.repository.DataRepository
import com.soft_industry.findgift.presentation.pages.giftselection.LoadGiftsStateReducer
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by user on 3/28/18.
 */
class LoadGifts @Inject constructor(val dataRepository: DataRepository) {
    fun execute(target: GiftTarget): Observable<LoadGiftsStateReducer> {
        return dataRepository.loadGifts(target)
                .subscribeOn(Schedulers.io())
                .map { LoadGiftsStateReducer.ContentLoaded(it) }
                .cast(LoadGiftsStateReducer::class.java)
                .onErrorReturn { LoadGiftsStateReducer.Error(it) }
                .startWith(LoadGiftsStateReducer.Loading())
    }
}