package com.soft_industry.findgift.data.repository

import android.content.Context
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.domain.repository.DataRepository
import io.reactivex.Observable
import java.androidx.core.content.findDrawalbeIdByName
import java.androidx.core.content.findStringIdByName

/**
 * Created by user on 3/26/18.
 */
class DummyDataRepositoryImpl(val context: Context) : DataRepository{
    override fun loadRandomGift(giftTarget: GiftTarget?): Observable<Gift> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadShopTypes(gift: Gift): Observable<List<String>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadForWomen(): Observable<List<GiftTarget>> {
        return Observable.fromIterable(loadForWomenInternal())
                .map(this::toGiftTarget)
                .toList()
                .toObservable()
    }



    override fun loadForMen(): Observable<List<GiftTarget>> {
        return Observable.fromIterable(loadForMenInternal())
                .map(this::toGiftTarget)
                .toList()
                .toObservable()
    }

    override fun loadThematic(): Observable<List<GiftTarget>> {
        return Observable.fromIterable(loadThematicInternal())
                .map(this::toGiftTarget)
                .toList()
                .toObservable()
    }



    override fun loadEditors(): Observable<List<GiftTarget>> {
        return Observable.fromIterable(loadEditorsInternal())
                .map(this::toGiftTarget)
                .toList()
                .toObservable()
    }



    override fun loadGifts(giftTarget: GiftTarget): Observable<List<Gift>> {
        return Observable.just(loadGiftsInternal())
    }


    private fun loadGiftsInternal(): List<Gift> {
        return listOf(
                Gift(0,"Book", "book", "Book is perfect present for bla bla bla ..."),
                Gift(0,"Gift card to spa", "spa_gift_card", "Spa is perfect present for bla bla bla ..."),
                Gift(0,"Ballon flight", "baloonflight_giftcard", "Ballon flight can help you to make pretty fancy suicide"),
                Gift(0,"Book", "book", "Book is perfect present for bla bla bla ..."),
                Gift(0,"Gift card to spa", "spa_gift_card", "Spa is perfect present for bla bla bla ..."),
                Gift(0,"Ballon flight", "baloonflight_giftcard", "Ballon flight can help you to make pretty fancy suicide"),
                Gift(0,"Book", "book", "Book is perfect present for bla bla bla ..."),
                Gift(0,"Gift card to spa", "spa_gift_card", "Spa is perfect present for bla bla bla ..."),
                Gift(0,"Ballon flight", "baloonflight_giftcard", "Ballon flight can help you to make pretty fancy suicide")
                )
    }

    private fun toGiftTarget(it: String) =
            context.run { GiftTarget(0L,findDrawalbeIdByName(it), getString(findStringIdByName(it))) }



    private fun loadEditorsInternal(): List<String> {
        return listOf(
                "forchildren",
                "hobby",
                "celebration",
                "profession",
                "formen",
                "forwomen")
    }
    private fun loadThematicInternal(): List<String> {
        return listOf(
                "housewife",
                "photo",
                "music",
                "work",
                "creative",
                "cooking",
                "driver",
                "fisher",
                "hunter",
                "itperson",
                "sport",
                "coffe",
                "wine",
                "book",
                "traveling",
                "game",
                "film"
        )
    }

    private fun loadForWomenInternal(): List<String>  {
        return listOf("mother", "wife")
    }

    private fun loadForMenInternal(): List<String> {
        return listOf("boyfriend", "husband")
    }
}