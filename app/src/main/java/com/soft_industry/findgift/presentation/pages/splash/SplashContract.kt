package com.soft_industry.findgift.presentation.pages.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soft_industry.findgift.domain.task.Prepopulation
import com.soft_industry.findgift.presentation.StateReducer
import io.reactivex.Scheduler
import javax.inject.Inject

data class SplashState(val loaded: Boolean, val error: Throwable? = null)
sealed class SplashAction {
    object LoadAction: SplashAction()
}

sealed class SplashReducer: StateReducer<SplashState> {
    class DatabaseLoaded: SplashReducer() {
        override fun reduce(oldViewState: SplashState): SplashState {
            return SplashState(true)
        }
    }

    class StartLoading: SplashReducer() {
        override fun reduce(oldViewState: SplashState): SplashState {
            return SplashState(false)
        }
    }

    class Error(val err: Throwable): SplashReducer() {
        override fun reduce(oldViewState: SplashState): SplashState {
            return SplashState(true, err)
        }

    }
}


class SplashViewModelFactory@Inject constructor(
        private val coordinator: SplashFlowCoordinator,
        private val prepopulation: Prepopulation,
        private val scheduler: Scheduler) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SplashViewModel(coordinator, prepopulation, scheduler) as T
    }

}