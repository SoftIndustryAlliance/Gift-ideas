package com.soft_industry.findgift.domain.repository

import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.GiftTarget
import io.reactivex.Observable

/**
 * Created by user on 3/26/18.
 */
interface DataRepository {
    fun loadEditors(): Observable<List<GiftTarget>>
    fun loadThematic(): Observable<List<GiftTarget>>
    fun loadForWomen(): Observable<List<GiftTarget>>
    fun loadForMen(): Observable<List<GiftTarget>>
    fun loadGifts(giftTarget: GiftTarget): Observable<List<Gift>>
    fun loadRandomGift(giftTarget: GiftTarget? = null): Observable<Gift>
    fun loadShopTypes(gift: Gift): Observable<List<String>>
}