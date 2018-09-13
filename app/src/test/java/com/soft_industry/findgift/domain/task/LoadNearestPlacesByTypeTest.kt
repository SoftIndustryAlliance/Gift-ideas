package com.soft_industry.findgift.domain.task

import com.soft_industry.findgift.data.repository.TestDataRepositoryImpl
import com.soft_industry.findgift.data.repository.TestLocationRepositoryImpl
import com.soft_industry.findgift.data.repository.TestPlacesRepositoryImpl
import com.soft_industry.findgift.data.repository.TestSettingsRepositoryImpl
import com.soft_industry.findgift.domain.task.robot.LoadNearestPlacesByTypeRobot
import com.soft_industry.findgift.domain.task.robot.defaultGift
import com.soft_industry.findgift.presentation.task.LoadNearestPlacesByType
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on


class LoadNearestPlacesByTypeTest : Spek({
    given("LoadNearestPlacesByType task") {
        val (robot, loadNearestPlacesByType) = setup()

        on("Execute") {
            robot.execute()
            val testObserver = loadNearestPlacesByType.execute(defaultGift)
                    .doOnNext { println("got $it") }
                    .test()
            it("should return bookshop nearest places and then gym nearest places") {
                testObserver.assertSubscribed()
                        .assertValueCount(2)
            }
        }

    }
}) {
    companion object {
        fun setup(): Pair<LoadNearestPlacesByTypeRobot, LoadNearestPlacesByType> {

            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }

            val locationRepo = TestLocationRepositoryImpl()
            val placesRepo = TestPlacesRepositoryImpl()
            val settingRepo = TestSettingsRepositoryImpl()
            val dataRepo = TestDataRepositoryImpl()
            val robot = LoadNearestPlacesByTypeRobot(locationRepo, placesRepo, settingRepo, dataRepo)
            val loadNearestPlacesByType = LoadNearestPlacesByType(locationRepo, placesRepo, settingRepo, dataRepo)
            return Pair(robot, loadNearestPlacesByType)
        }
    }
}




