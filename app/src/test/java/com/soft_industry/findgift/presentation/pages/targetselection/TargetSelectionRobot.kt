package com.soft_industry.findgift.presentation.pages.targetselection

import com.soft_industry.findgift.data.repository.TestDataRepositoryImpl
import com.soft_industry.findgift.domain.task.robot.LoadTargetsRobot

val shopTypes =listOf("book_shop")

class TargetSelectionRobot(val dataRepository: TestDataRepositoryImpl,
                           val vm: TargetSelectionViewModel) {
    fun loadTargetList() {
        vm.input.accept(TargetSelectionAction.LoadTargetListAction)
        LoadTargetsRobot(dataRepository).execute()
    }
}