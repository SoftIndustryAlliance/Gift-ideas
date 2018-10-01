package com.soft_industry.findgift.presentation.pages.randomgift

import com.soft_industry.findgift.presentation.BaseViewModel
import com.gojuno.koptional.Optional
import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.domain.task.LoadRandomGift
import com.soft_industry.findgift.presentation.StateReducer
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RandomGiftViewModel(
        private val loadRandomGift: LoadRandomGift,
        private val coordinator: RandomGiftFlowCoordinator,
        scheduler: Scheduler)
    : BaseViewModel<RandomGiftAction, RandomGiftState>(scheduler) {

    override fun route(action: RandomGiftAction): Observable<StateReducer<RandomGiftState>> {
        return when(action) {
            is RandomGiftAction.LoadGiftAction -> loadGiftIntent(action.taget)
            is RandomGiftAction.ShakeAction -> shakeIntent()
        }
    }

    override fun onStateUpdated(state: RandomGiftState) {
        super.onStateUpdated(state)
        handleNavigation(state)
    }

    override fun createInitialState() = RandomGiftState.createInitial()


    private fun loadGiftIntent(taget: Optional<GiftTarget>): Observable<StateReducer<RandomGiftState>> {
        return  Observable.concat(labelObservable(taget), loadRandomGift(taget))
    }

    private fun loadRandomGift(taget: Optional<GiftTarget>) = loadRandomGift.execute(taget.toNullable())
                .map<RandomGiftReducer> { RandomGiftReducer.LoadingSucceded(it) }
                .startWith(RandomGiftReducer.Loading)
                .subscribeOn(Schedulers.io())


    private fun shakeIntent() = Observable.just(RandomGiftAction.ShakeAction)
            .flatMap<StateReducer<RandomGiftState>> { Observable.concat(animate(), openGift()) }

    private fun openGift(): Observable<RandomGiftReducer> {
        return Observable.interval(2000, TimeUnit.MILLISECONDS)
                .take(1)
                .map { RandomGiftReducer.ShowGift }
    }

    private fun animate() = Observable.just(RandomGiftReducer.AniamteShake)

    private fun handleNavigation(state: RandomGiftState) {
        if(state.showGift && state.gift != null ) {
            coordinator.openGift(state.gift)
        }
    }

    private fun labelObservable(target: Optional<GiftTarget>) =
            Observable.just(RandomGiftReducer.LabelLoaded(target.toNullable()?.label))
}