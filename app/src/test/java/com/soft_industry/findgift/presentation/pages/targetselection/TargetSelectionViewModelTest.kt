package com.soft_industry.findgift.presentation.pages.targetselection

import androidx.lifecycle.Observer
import com.jakewharton.rxrelay2.PublishRelay
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.domain.repository.DataRepository
import com.soft_industry.findgift.domain.task.LoadTargets
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

val defaultTarget = Observable.just(listOf(GiftTarget(1L, 1, "test")))
val gifts = Observable.just(listOf(Gift(1L, "test", "test", "test")))
val randomGift = Observable.just(Gift(1L, "test", "test", "test"))
val shopTypes = Observable.just<List<String>>(listOf())

class TargetSelectionViewModelTest: Spek({
    describe("Target selection viewmodel") {
        val vm = TargetSelectionViewModel(LoadTargets(DummyDataRepository), Schedulers.trampoline())
        val states = PublishRelay.create<TargetSelectionState>()
        val testObserver = Observer<TargetSelectionState> { states.accept(it) }
        vm.state.observeForever(testObserver)

        context("LoadTargetListAction") {
            vm.input.accept(TargetSelectionAction.LoadTargetListAction)
            it("Should  dismiss hint, show content loading ") {
                states.test()
                        .assertValues()

            }
        }
    }
})

object DummyDataRepository: DataRepository {

    override fun loadEditors(): Observable<List<GiftTarget>> {
        return defaultTarget
    }

    override fun loadThematic(): Observable<List<GiftTarget>> {
        return defaultTarget
    }

    override fun loadForWomen(): Observable<List<GiftTarget>> {
        return defaultTarget
    }

    override fun loadForMen(): Observable<List<GiftTarget>> {
        return defaultTarget
    }

    override fun loadGifts(giftTarget: GiftTarget): Observable<List<Gift>> {
        return gifts

    }

    override fun loadRandomGift(giftTarget: GiftTarget?): Observable<Gift> {
        return randomGift
    }

    override fun loadShopTypes(gift: Gift): Observable<List<String>> {
        return shopTypes
    }
}