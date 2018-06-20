package com.soft_industry.findgift.presentation.pages.splash

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.soft_industry.findgift.App
import com.soft_industry.findgift.R
import io.reactivex.Observable
import javax.inject.Inject

class SplashActivity : MviActivity<SplashView, SplashPresenter>(), SplashView {

    @Inject
    lateinit var presenter: SplashPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).component
                .splashComponent(SplashModule(this))
                .inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
    }

    override fun loadedIntent() = Observable.just(true)


    override fun createPresenter() = presenter


    override fun render(state: SplashViewState) {
    }

}
