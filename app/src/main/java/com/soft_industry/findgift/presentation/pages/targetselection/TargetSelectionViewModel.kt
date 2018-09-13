package com.soft_industry.findgift.presentation.pages.targetselection

import com.soft_industry.findgift.domain.task.LoadTargets
import com.soft_industry.findgift.domain.task.TargetData
import com.soft_industry.findgift.presentation.BaseViewModel
import com.soft_industry.findgift.presentation.StateReducer
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class TargetSelectionViewModel(private val loadTargets: LoadTargets, scheduler: Scheduler)
    : BaseViewModel<TargetSelectionAction, TargetSelectionState>(scheduler) {

    override fun route(action: TargetSelectionAction)
            : Observable<StateReducer<TargetSelectionState>> {
        return when(action) {
            is TargetSelectionAction.LoadTargetListAction -> {
                Observable.merge(dismissHint(), loadTargetsAndComplete())
            }
        }
    }


    override fun createInitialState() =  TargetSelectionState.initial()

    private fun dismissHint(): Observable<StateReducer<TargetSelectionState>> {
        return Observable.interval(2000, TimeUnit.MILLISECONDS)
                .take(1)
                .map { TargetsViewStateReducer.DismissHint }
    }

    private fun loadTargetsAndComplete() = Observable.concat(loadTargets(), complete())
    private fun loadTargets()  = loadTargets.execute()
            .map {
                when(it) {
                    is TargetData.Editors -> TargetsViewStateReducer.EditorsLoaded(it.data)
                    is TargetData.Themed -> TargetsViewStateReducer.Themed(it.data)
                    is TargetData.ForMen -> TargetsViewStateReducer.ForMen(it.data)
                    is TargetData.ForWomen -> TargetsViewStateReducer.ForWomen(it.data)
                }
            }
            .onErrorReturn { TargetsViewStateReducer.Error(it) }
            .startWith(TargetsViewStateReducer.Loading)
            .subscribeOn(Schedulers.io())
    private fun complete() = Observable.just(TargetsViewStateReducer.Loaded)


}


