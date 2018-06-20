package com.soft_industry.findgift.presentation.pages.splash

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable


interface SplashView : MvpView {
    fun loadedIntent(): Observable<Boolean>
    fun render(state: SplashViewState)
}


