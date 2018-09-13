package com.soft_industry.findgift.domain.task

import com.soft_industry.findgift.data.repository.TestDataRepositoryImpl
import com.soft_industry.findgift.domain.task.robot.LoadTargetsRobot
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

class LoadTargetsTest: Spek({
    given("LoadTargetsTest") {
        val (robot, loadTargets) = setup()
        on("execute") {
            val testObserver = loadTargets.execute()
                    .test()
            robot.execute()
            it("should return editors, thematic, forman end forwoman data") {
                testObserver.assertSubscribed()
                        .assertValueCount(4)
                        .assertComplete()
            }
        }
    }
}) {
    companion object {
        fun setup(): Pair<LoadTargetsRobot, LoadTargets> {
            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
            val dataRepository = TestDataRepositoryImpl()
            val robot = LoadTargetsRobot(dataRepository)
            val loadTargets = LoadTargets(dataRepository)
            return Pair(robot, loadTargets)
        }
    }
}


