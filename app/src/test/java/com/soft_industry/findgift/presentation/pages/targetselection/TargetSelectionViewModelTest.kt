package com.soft_industry.findgift.presentation.pages.targetselection

import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.verify
import com.soft_industry.findgift.MockMainLooper
import com.soft_industry.findgift.data.repository.TestDataRepositoryImpl
import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.domain.task.LoadTargets
import com.soft_industry.findgift.mock
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

class TargetSelectionViewModelTest: Spek({
    given("Target selection view model") {
        MockMainLooper()
        var (renderer, robot) = setup()
        beforeEachTest {
            reset(renderer)
        }
        on("LoadTargetListAction") {
            robot.loadTargetList()
            val stateUpdater = StateUpdater(TargetSelectionState.initial())
            it("should show initial state ") {
                verify(renderer).render(stateUpdater.updateAndGet(showHint = false))
            }

            it("should show hint ") {
                verify(renderer).render(stateUpdater.updateAndGet(showHint = false))
            }
            it("should show loading ") {
                verify(renderer).render(stateUpdater.updateAndGet(loading = true))
            }
            it("should show editors") {
                verify(renderer).render(stateUpdater.updateAndGet(editors = defaultTargets))
            }
            it("should show thematic") {
                verify(renderer).render(stateUpdater.updateAndGet(thematic = defaultTargets))
            }
            it("should show for women") {
                verify(renderer).render(stateUpdater.updateAndGet(forwomen = defaultTargets))
            }
            it("should show for men") {
                verify(renderer).render(stateUpdater.updateAndGet(formen = defaultTargets))
            }
            it("should finish loading") {
                verify(renderer).render(stateUpdater.updateAndGet(loading = false))
            }

            it("should dismiss hint") {
                verify(renderer).render(stateUpdater.updateAndGet(showHint = false))
            }

        }
    }
})

fun setup(): Pair<TargetSelectionRenderer, TargetSelectionRobot> {
    val renderer: TargetSelectionRenderer = mock()
    val testScheduler = Schedulers.trampoline()
    RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
    RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
    val dataRepository = TestDataRepositoryImpl()
    val vm = TargetSelectionViewModel(LoadTargets(dataRepository), testScheduler)
    vm.state.observeForever { renderer.render(it) }
    val robot = TargetSelectionRobot(dataRepository, vm)
    return Pair(renderer, robot)
}








internal class StateUpdater(var state: TargetSelectionState) {
    fun updateAndGet(loading: Boolean = state.loading,
                     editors: List<GiftTarget> = state.editors,
                     thematic: List<GiftTarget> = state.thematic,
                     forwomen: List<GiftTarget> = state.forwomen,
                     formen: List<GiftTarget> = state.formen,
                     error: Throwable? = state.error,
                     showHint: Boolean = state.showHint): TargetSelectionState {
        state = TargetSelectionState(loading, editors, thematic, forwomen, formen, error, showHint)
        return state
    }
}

