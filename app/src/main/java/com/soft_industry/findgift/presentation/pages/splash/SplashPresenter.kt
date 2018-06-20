package com.soft_industry.findgift.presentation.pages.splash

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import com.soft_industry.findgift.domain.task.Prepopulation
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class SplashPresenter  @Inject constructor(
                                           val coordinator: SplashFlowCoordinator,
                                           val prepopulation: Prepopulation)
    : MviBasePresenter<SplashView, SplashViewState>() {
    override fun bindIntents() {
        val initialVs = SplashViewState(false, null)
        val intent = intent { prepopulation.execute() }
                .observeOn(AndroidSchedulers.mainThread())
                .scan(initialVs) { vs, reducer -> reducer.reduce(vs) }
                .doOnNext { if(it.loaded) coordinator.openMainActivity() }
        subscribeViewState(intent, SplashView::render)
    }
}


