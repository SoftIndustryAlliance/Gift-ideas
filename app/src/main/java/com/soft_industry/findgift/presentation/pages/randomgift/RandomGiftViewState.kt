package com.soft_industry.findgift.presentation.pages.randomgift

import com.soft_industry.findgift.domain.entities.Gift

data class RandomGiftViewState(val loading: Boolean,
                               val gift:Gift?,
                               val targetLabel: String,
                               val animateShake: Boolean,
                               val showGift: Boolean,
                               val error: Throwable?) {
    companion object {
        fun createInitial() = RandomGiftViewState(false,
                        null,
                        "",
                        false,
                        false,
                        null)

    }
}