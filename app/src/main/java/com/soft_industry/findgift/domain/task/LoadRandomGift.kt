package com.soft_industry.findgift.domain.task

import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.domain.repository.DataRepository
import io.reactivex.Observable
import javax.inject.Inject

class LoadRandomGift @Inject constructor(val dataRepository: DataRepository) {
    fun execute(giftTarget: GiftTarget?): Observable<Gift> {
        return dataRepository.loadRandomGift(giftTarget)


    }
}