package com.soft_industry.findgift.presentation.pages.giftselection

import com.soft_industry.findgift.data.repository.TestDataRepositoryImpl
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.GiftTarget
val defaultTarget = GiftTarget(1L, 1, "test")
val gifts = listOf(Gift(1L, "test", "test", "test"))

class GiftSelectionRobot(private val dataRepository: TestDataRepositoryImpl, private val vm: GiftSelectionViewModel) {
    fun loadGiftListAction() {
        vm.input.accept(GiftSelectionAction.LoadGiftListAction(defaultTarget))
        dataRepository.gifts.onNext(gifts)
        dataRepository.gifts.onComplete()

    }
}