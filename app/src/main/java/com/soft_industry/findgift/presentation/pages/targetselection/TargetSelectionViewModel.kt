package com.soft_industry.findgift.presentation.pages.targetselection

import com.soft_industry.findgift.domain.task.LoadTargets
import com.soft_industry.findgift.presentation.BaseViewModel
import com.soft_industry.findgift.presentation.StateReducer
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit


class TargetSelectionViewModel(private val loadTargets: LoadTargets, scheduler: Scheduler)
    : BaseViewModel<TargetSelectionAction, TargetSelectionState>(scheduler) {

    override fun route(action: TargetSelectionAction)
            : Observable<StateReducer<TargetSelectionState>> {
        return when(action) {
            is TargetSelectionAction.LoadTargetListAction -> {
                Observable.merge(dismissHint(), loadTargets.execute())
            }
        }
    }

    override fun createInitialState() =  TargetSelectionState.initial()

    private fun dismissHint(): Observable<StateReducer<TargetSelectionState>> {
        return Observable.interval(2000, TimeUnit.MILLISECONDS)
                .take(1)
                .map { TargetsViewStateReducer.DismissHint }
    }

}


