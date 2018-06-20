package com.soft_industry.findgift.domain.task

import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.domain.repository.DataRepository
import com.soft_industry.findgift.presentation.pages.randomgift.RandomGiftReducer
import io.reactivex.Observable
import javax.inject.Inject

class LoadRandomGift @Inject constructor(val dataRepository: DataRepository) {
    fun execute(giftTarget: GiftTarget?): Observable<RandomGiftReducer> {
        return dataRepository.loadRandomGift(giftTarget)
                .map { RandomGiftReducer.LoadingSucceded(it) }
                .cast(RandomGiftReducer::class.java)
                .startWith(RandomGiftReducer.Loading)
    }
}