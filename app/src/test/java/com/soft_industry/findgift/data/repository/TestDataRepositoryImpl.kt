package com.soft_industry.findgift.data.repository

import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.domain.repository.DataRepository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class TestDataRepositoryImpl : DataRepository {
    val editors =  BehaviorSubject.create<List<GiftTarget>>()
    val thematic =  PublishSubject.create<List<GiftTarget>>()
    val forwomen =  PublishSubject.create<List<GiftTarget>>()
    val formen =  PublishSubject.create<List<GiftTarget>>()
    val gifts = PublishSubject.create<List<Gift>>()
    val randomGift = PublishSubject.create<Gift>()
    val shoptypes = PublishSubject.create<List<String>>()

    override fun loadEditors(): Observable<List<GiftTarget>> {
       return editors
    }

    override fun loadThematic(): Observable<List<GiftTarget>> {
        return thematic
    }

    override fun loadForWomen(): Observable<List<GiftTarget>> {
        return forwomen
    }

    override fun loadForMen(): Observable<List<GiftTarget>> {
        return formen
    }

    override fun loadGifts(giftTarget: GiftTarget): Observable<List<Gift>> {
        return gifts
    }

    override fun loadRandomGift(giftTarget: GiftTarget?): Observable<Gift> {
        return randomGift
    }

    override fun loadShopTypes(gift: Gift): Observable<List<String>> {
        return shoptypes
    }


}