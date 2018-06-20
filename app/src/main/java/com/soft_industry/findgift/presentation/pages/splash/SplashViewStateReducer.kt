package com.soft_industry.findgift.presentation.pages.splash

import com.soft_industry.findgift.presentation.StateReducer

sealed class SplashViewStateReducer: StateReducer<SplashViewState> {
    class DatabaseLoaded: SplashViewStateReducer() {
        override fun reduce(oldViewState: SplashViewState): SplashViewState {
            return SplashViewState(true)
        }
    }

    class StartLoading: SplashViewStateReducer() {
        override fun reduce(oldViewState: SplashViewState): SplashViewState {
            return SplashViewState(false)
        }
    }

    class Error(val err: Throwable): SplashViewStateReducer() {
        override fun reduce(oldViewState: SplashViewState): SplashViewState {
            return SplashViewState(true, err)
        }

    }
}
