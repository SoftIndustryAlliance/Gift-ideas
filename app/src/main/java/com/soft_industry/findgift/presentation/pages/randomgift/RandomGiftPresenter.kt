package com.soft_industry.findgift.presentation.pages.randomgift

import com.gojuno.koptional.Optional
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.domain.task.LoadRandomGift
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RandomGiftPresenter @Inject constructor(val loadRandomGift: LoadRandomGift,
                                              val coordinator: RandomGiftFlowCoordinator)
    : MviBasePresenter<RandomGiftView, RandomGiftViewState>() {
    override fun bindIntents() {
        val initialVs = RandomGiftViewState(false, null, "", false, false, null)
        val loadGiftIntent = loadGiftIntent()
        val shakeIntent = shakeIntent()

        val intents = Observable.merge(loadGiftIntent, shakeIntent)
                .onErrorReturn { RandomGiftReducer.GotError(it) }
                .scan(initialVs) { vs, reducer -> reducer.reduce(vs) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { handleNavigation(it) }
        subscribeViewState(intents, RandomGiftView::render)
    }

    private fun loadGiftIntent(): Observable<RandomGiftReducer>? {
        return intent { it.loadGiftIntent() }
                .switchMap { Observable.concat(labelObservable(it), loadRandomGift.execute(it.toNullable())) }
    }

    private fun shakeIntent() = intent { it.shakeIntent() }
            .take(1)
            .flatMap { Observable.concat(animate(), openGift()) }

    private fun openGift(): Observable<RandomGiftReducer> {
        return Observable.interval(2000, TimeUnit.MILLISECONDS)
                .take(1)
                .map { RandomGiftReducer.ShowGift }
    }

    private fun animate() = Observable.just(RandomGiftReducer.AniamteShake)

    private fun handleNavigation(state: RandomGiftViewState) {
        if(state.showGift && state.gift != null ) {
            coordinator.openGift(state.gift)
        }
    }

    private fun labelObservable(target: Optional<GiftTarget>) =
            Observable.just(RandomGiftReducer.LabelLoaded(target.toNullable()?.label))
}