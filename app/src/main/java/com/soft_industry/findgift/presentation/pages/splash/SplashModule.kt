package com.soft_industry.findgift.presentation.pages.splash

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Scope


@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class SplashScope

@Module
@SplashScope
class SplashModule(val splashActivity: SplashActivity) {

    @Provides
    fun provideSplashActivity() = splashActivity
}


@SplashScope
@Subcomponent(modules = arrayOf(SplashModule::class))
interface SplashComponent {
    fun inject(splashActivity: SplashActivity)
}