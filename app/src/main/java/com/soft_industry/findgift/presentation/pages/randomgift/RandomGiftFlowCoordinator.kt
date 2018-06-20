package com.soft_industry.findgift.presentation.pages.randomgift

import android.os.Handler
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.presentation.pages.giftdetails.GiftDetailsActivity
import javax.inject.Inject

class RandomGiftFlowCoordinator @Inject constructor (private val activity: RandomGiftActivity) {
    private val handler = Handler()

    fun openGift(gift: Gift) {
        GiftDetailsActivity.start(activity, gift,0f,0f)
        activity.finish()
    }
}