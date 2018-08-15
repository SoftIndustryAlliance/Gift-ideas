package com.soft_industry.findgift.domain.task

import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.domain.repository.DataRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by user on 3/28/18.
 */
class LoadGifts @Inject constructor(val dataRepository: DataRepository) {
    fun execute(target: GiftTarget): Observable<List<Gift>> {
        return dataRepository.loadGifts(target)
                .subscribeOn(Schedulers.io())

    }
}