package com.soft_industry.findgift.presentation.pages.targetselection

import com.soft_industry.findgift.data.repository.TestDataRepositoryImpl
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.GiftTarget

val defaultTarget = listOf(GiftTarget(1L, 1, "test"))
val gifts = listOf(Gift(1L, "test", "test", "test"))
val randomGift = Gift(1L, "test", "test", "test")
val shopTypes =listOf("book_shop")

class TargetSelectionRobot(val dataRepository: TestDataRepositoryImpl,
                           val vm: TargetSelectionViewModel) {
    fun loadTargetList() {
        vm.input.accept(TargetSelectionAction.LoadTargetListAction)
        dataRepository.editors.onNext(defaultTarget)
        dataRepository.editors.onComplete()
        dataRepository.thematic.onNext(defaultTarget)
        dataRepository.thematic.onComplete()
        dataRepository.forwomen.onNext(defaultTarget)
        dataRepository.forwomen.onComplete()
        dataRepository.formen.onNext(defaultTarget)
        dataRepository.formen.onComplete()

//        testScheduler.advanceTimeBy(2000, TimeUnit.MILLISECONDS)
    }
}