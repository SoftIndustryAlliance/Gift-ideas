package com.soft_industry.findgift.presentation.pages.randomgift

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Scope


@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class RandomGiftScope

@Module
@RandomGiftScope
class RandomModule(val randomGiftActivity: RandomGiftActivity) {

    @Provides
    fun provideRandomGiftFragment() = randomGiftActivity
}


@RandomGiftScope
@Subcomponent(modules = arrayOf(RandomModule::class))
interface RandomComponent {
    fun inject(activity: RandomGiftActivity)
}