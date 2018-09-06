package com.soft_industry.findgift.presentation.pages.giftselection

import com.nhaarman.mockitokotlin2.reset
import com.soft_industry.findgift.MockMainLooper
import com.soft_industry.findgift.data.repository.TestDataRepositoryImpl
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.task.LoadGifts
import com.soft_industry.findgift.mock
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.mockito.Mockito.verify

class GiftSelectionViewModelTest : Spek({
    given("Gift selection view model") {
        MockMainLooper()
        val (renderer, robot) = setup()
        beforeEachTest {
            reset(renderer)
        }
        on("load gift list action") {
            robot.loadGiftListAction()
            val initial = StateUpdater(GiftSelectionState(true, listOf()))
            it("should show loading") {
                verify(renderer).render(initial.state)
            }
            it("should hide loading and show content") {
                verify(renderer).render(initial.updateAndGet(false,gifts))
            }
        }
    }
})


fun setup(): Pair<GiftSelectionRenderer, GiftSelectionRobot> {
    val renderer: GiftSelectionRenderer = mock()
    val testScheduler = Schedulers.trampoline()
    RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
    RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
    val dataRepository = TestDataRepositoryImpl()
    val vm = GiftSelectionViewModel(LoadGifts(dataRepository), testScheduler)
    vm.state.observeForever { renderer.render(it) }
    val robot = GiftSelectionRobot(dataRepository, vm)
    return Pair(renderer, robot)
}

internal class StateUpdater(var state: GiftSelectionState) {
    fun updateAndGet(loading: Boolean,
                     content: List<Gift>,
                     error: Throwable? = null): GiftSelectionState {
        state = GiftSelectionState(loading,content, error)
        return state
    }
}