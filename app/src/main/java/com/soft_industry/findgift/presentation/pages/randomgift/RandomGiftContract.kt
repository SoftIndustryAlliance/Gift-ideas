package com.soft_industry.findgift.presentation.pages.randomgift

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gojuno.koptional.Optional
import com.soft_industry.findgift.dependencies.RenderScheduler
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.domain.task.LoadRandomGift
import com.soft_industry.findgift.presentation.StateReducer
import io.reactivex.Scheduler
import javax.inject.Inject

sealed class RandomGiftAction {

    data class LoadGiftAction(val taget: Optional<GiftTarget>): RandomGiftAction()
    object ShakeAction: RandomGiftAction()
}

data class RandomGiftState(val loading: Boolean,
                           val gift: Gift?,
                           val targetLabel: String,
                           val animateShake: Boolean,
                           val showGift: Boolean,
                           val error: Throwable?) {
    companion object {
        fun createInitial() = RandomGiftState(false,
                null,
                "",
                false,
                false,
                null)

    }
}

sealed class RandomGiftReducer : StateReducer<RandomGiftState> {
    object Loading: RandomGiftReducer() {
        override fun reduce(old: RandomGiftState)
                = old.copy(loading = true)
    }

    class LoadingSucceded(val gift: Gift): RandomGiftReducer() {
        override fun reduce(old: RandomGiftState)
                = old.copy(gift = gift)
    }

    class GotError(val err: Throwable): RandomGiftReducer() {
        override fun reduce(old: RandomGiftState)
                = old.copy(error = err)
    }

    class LabelLoaded(val label: String?): RandomGiftReducer() {
        override fun reduce(old: RandomGiftState)
                = old.copy(targetLabel = label.orEmpty())

    }

    object ShowGift : RandomGiftReducer() {
        override fun reduce(old: RandomGiftState)
                = old.copy(showGift =  true, animateShake = false)

    }
    object AniamteShake : RandomGiftReducer() {
        override fun reduce(old: RandomGiftState)
                = old.copy(animateShake =  true, showGift = false)
    }
}

class RandomGiftViewModelFactory
@Inject constructor(val loadRandomGift: LoadRandomGift,
                    val coordinator: RandomGiftFlowCoordinator,
                    @RenderScheduler val scheduler: Scheduler)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RandomGiftViewModel(loadRandomGift, coordinator, scheduler) as T
    }

}