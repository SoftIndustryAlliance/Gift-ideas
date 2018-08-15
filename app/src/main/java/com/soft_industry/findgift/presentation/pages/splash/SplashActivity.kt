package com.soft_industry.findgift.presentation.pages.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.soft_industry.findgift.App
import com.soft_industry.findgift.R
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: SplashViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).component
                .splashComponent(SplashModule(this))
                .inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        ViewModelProviders.of(this, viewModelFactory)
                .get(SplashViewModel::class.java)
                .input.accept(SplashAction.LoadAction)
    }

}
