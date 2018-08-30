package com.soft_industry.findgift.presentation.pages.targetselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.domain.task.LoadTargets
import com.soft_industry.findgift.presentation.StateReducer
import io.reactivex.Scheduler
import javax.inject.Inject

sealed class TargetSelectionAction {
    object LoadTargetListAction: TargetSelectionAction()
}

data class TargetSelectionState(val loading: Boolean,
                                val editors: List<GiftTarget>,
                                val thematic: List<GiftTarget>,
                                val forwomen: List<GiftTarget>,
                                val formen: List<GiftTarget>,
                                val error: Throwable? = null,
                                val showHint: Boolean)


sealed class TargetsViewStateReducer : StateReducer<TargetSelectionState> {
    object  Loading: TargetsViewStateReducer() {
        override fun reduce(old: TargetSelectionState) = old.copy(loading = true)
    }

    object Loaded: TargetsViewStateReducer() {
        override fun reduce(oldVS: TargetSelectionState) = oldVS.copy(loading = false)
    }

    class EditorsLoaded(private val data: List<GiftTarget>): TargetsViewStateReducer() {
        override fun reduce(old: TargetSelectionState) = old.copy(editors = data)
    }

    class Themed(private val data: List<GiftTarget>): TargetsViewStateReducer() {
        override fun reduce(old: TargetSelectionState) = old.copy(thematic = data)
    }

    class ForWomen(private val data: List<GiftTarget>): TargetsViewStateReducer() {
        override fun reduce(old: TargetSelectionState) = old.copy(forwomen = data)
    }
    class ForMen(private val data: List<GiftTarget>): TargetsViewStateReducer() {
        override fun reduce(old: TargetSelectionState) = old.copy(formen = data)
    }

    object DismissHint:TargetsViewStateReducer() {
        override fun reduce(oldVs: TargetSelectionState) = oldVs.copy(showHint = false)
    }

    class Error(private val error: Throwable) : TargetsViewStateReducer() {
        override fun reduce(old: TargetSelectionState) = old.copy(error = error)
    }

}


class TargetSelectionViewModelFactory
@Inject constructor(private val loadTargets: LoadTargets, val scheduler: Scheduler)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TargetSelectionViewModel(loadTargets, scheduler ) as T
    }

}