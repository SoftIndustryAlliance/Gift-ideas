package com.soft_industry.findgift.presentation.pages.targetselection

import com.soft_industry.findgift.domain.entities.GiftTarget


data class TargetSelectionViewState(val loading: Boolean ,
                               val editors: List<GiftTarget>,
                               val thematic: List<GiftTarget>,
                               val forwomen: List<GiftTarget>,
                               val formen: List<GiftTarget>,
                               val error: Throwable? = null,
                               val showHint: Boolean)

