package com.soft_industry.findgift.presentation.pages.targetselection

import android.util.Log
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.soft_industry.findgift.domain.task.LoadTargets
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class TargetSelectionPresenter @Inject constructor(private val loadTargets: LoadTargets)
    : MviBasePresenter<TargetSelectionView, TargetSelectionViewState>() {
    override fun bindIntents() {
        val initialState = TargetSelectionViewState(
                false,
                mutableListOf(),
                mutableListOf(),
                mutableListOf(),
                mutableListOf(),
                showHint = true
        )
        val dismissShowHint = Observable.interval(2000, TimeUnit.MILLISECONDS)
                .take(1)
                .map { TargetsViewStateReducer.DismissHint() }
        val loadTargetList = intent(TargetSelectionView::loadTargetListIntent)
                .switchMap { loadTargets.execute() }

        val intents = Observable.merge(dismissShowHint, loadTargetList)
                .scan(initialState) { oldViewState, reducer -> reducer.reduce(oldViewState) }
                .doOnNext { Log.e("TargetSelection", "got vs: ${it.showHint}" )}
                .observeOn(AndroidSchedulers.mainThread())
        subscribeViewState(intents, TargetSelectionView::render)
    }
}


