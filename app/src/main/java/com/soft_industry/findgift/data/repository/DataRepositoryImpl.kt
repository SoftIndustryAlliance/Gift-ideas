package com.soft_industry.findgift.data.repository

import android.content.Context
import com.soft_industry.findgift.data.converters.mapToEntity
import com.soft_industry.findgift.data.datasources.local.DataDao
import com.soft_industry.findgift.data.dto.gifts.GiftDTO
import com.soft_industry.findgift.data.dto.gifts.GiftTargetParent
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.domain.repository.DataRepository
import io.reactivex.Observable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

/**
 * Created by user on 4/18/18.
 */
class DataRepositoryImpl @Inject constructor(val context: Context, val dataDao: DataDao): DataRepository {


    override fun loadEditors(): Observable<List<GiftTarget>> {
        return dataDao.getGiftTargetsForParent(GiftTargetParent.Editors.id)
                .toObservable()
                .map { it.map { it.mapToEntity(context) } }
    }

    override fun loadThematic(): Observable<List<GiftTarget>> {
        return dataDao.getGiftTargetsForParent(GiftTargetParent.Thematic.id)
                .toObservable()
                .map { it.map { it.mapToEntity(context) } }
    }

    override fun loadForWomen(): Observable<List<GiftTarget>> {
        return dataDao.getGiftTargetsForParent(GiftTargetParent.ForWomen.id)
                .toObservable()
                .map { it.map { it.mapToEntity(context) } }
    }

    override fun loadForMen(): Observable<List<GiftTarget>> {
        return dataDao.getGiftTargetsForParent(GiftTargetParent.ForMen.id)
                .toObservable()
                .map { it.map { it.mapToEntity(context) } }
    }

    override fun loadGifts(giftTarget: GiftTarget): Observable<List<Gift>> {
        return dataDao.getGiftsForTarget(giftTarget.id)
                .toObservable()
                .map { it.map { it.mapToEntity(context) } }
    }

    override fun loadShopTypes(gift: Gift): Observable<List<String>> {
        return dataDao.getGiftShopsForGift(gift.id)
                .toObservable()
                .map { it.map { it.label } }
                .map { it.takeIf { !it.isEmpty() } ?: listOf("book_store") }
    }

    override fun loadRandomGift(giftTarget: GiftTarget?): Observable<Gift> {
        return loadGiftInternal(giftTarget)
                .map { it.mapToEntity(context) }
                .toObservable()

    }

    private fun loadGiftInternal(giftTarget: GiftTarget?): Single<GiftDTO> {
        return if (giftTarget == null) {
            dataDao.randomGift
        } else {
            dataDao.getGiftsForTarget(giftTarget.id)
                    .map { it[Random().nextInt(it.size)] }
        }
    }
}