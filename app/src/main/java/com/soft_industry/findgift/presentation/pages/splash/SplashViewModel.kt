package com.soft_industry.findgift.presentation.pages.splash

import com.soft_industry.findgift.domain.task.Prepopulation
import com.soft_industry.findgift.presentation.BaseViewModel
import com.soft_industry.findgift.presentation.StateReducer
import io.reactivex.Observable
import io.reactivex.Scheduler


class SplashViewModel(private val coordinator: SplashFlowCoordinator,
                      private val prepopulation: Prepopulation,
                      scheduler: Scheduler)
    : BaseViewModel<SplashAction, SplashState>(scheduler) {

    override fun createInitialState() = SplashState(false, null)

    override fun route(action: SplashAction): Observable<StateReducer<SplashState>> {
        return when(action) {
            is SplashAction.LoadAction -> prepopulate()
        }
    }

    override fun onStateUpdated(state: SplashState) {
        super.onStateUpdated(state)
        if (state.loaded) {
            coordinator.openMainActivity()
        }
    }

    private fun prepopulate(): Observable<StateReducer<SplashState>> = prepopulation.execute()
            .map<StateReducer<SplashState>> { SplashReducer.DatabaseLoaded() }
            .startWith(SplashReducer.StartLoading())
            .onErrorReturn { SplashReducer.Error(it) }

}


