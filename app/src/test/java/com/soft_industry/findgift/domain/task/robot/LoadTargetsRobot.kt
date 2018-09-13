package com.soft_industry.findgift.domain.task.robot

import com.soft_industry.findgift.data.repository.TestDataRepositoryImpl
import com.soft_industry.findgift.domain.entities.GiftTarget

val defaultTargets = listOf(GiftTarget(1L, 1, "test"))
class LoadTargetsRobot(val dataRepository: TestDataRepositoryImpl) {

    fun execute(){
        dataRepository.editors.onNext(defaultTargets)
        dataRepository.editors.onComplete()
        dataRepository.thematic.onNext(defaultTargets)
        dataRepository.thematic.onComplete()
        dataRepository.forwomen.onNext(defaultTargets)
        dataRepository.forwomen.onComplete()
        dataRepository.formen.onNext(defaultTargets)
        dataRepository.formen.onComplete()
    }
}
