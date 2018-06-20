package com.soft_industry.findgift.presentation.pages.giftselection

import com.soft_industry.findgift.domain.entities.Gift


class GiftSelectionViewState(val loading: Boolean,
                             val content: List<Gift>,
                             val error: Throwable? = null)

