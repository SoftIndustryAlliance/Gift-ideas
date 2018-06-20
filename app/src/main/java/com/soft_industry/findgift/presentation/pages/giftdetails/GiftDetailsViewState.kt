package com.soft_industry.findgift.presentation.pages.giftdetails

import com.soft_industry.findgift.domain.entities.Gift


class GiftDetailsViewState(val loading: Boolean,
                           val gift: Gift?,
                           val error: Throwable? = null)

