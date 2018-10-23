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
                Observable.merge(dismissHint(), loadTargetsAndComplete()) //начинаем загрузку и отсчет, что бы прятать хинт
            }
        }
    }


    override fun createInitialState() =  TargetSelectionState.initial()

    /***
     * Начальный стейт у нас  - показывать хинт, поэтому прячем хинт через 2 секунды
     */
    private fun dismissHint(): Observable<StateReducer<TargetSelectionState>> {
        return Observable.interval(2000, TimeUnit.MILLISECONDS)
                .take(1)
                .map { TargetsViewStateReducer.DismissHint }
    }

    private fun loadTargetsAndComplete() = Observable.concat(loadTargets(), complete())
    private fun loadTargets()  = loadTargets.execute()
            .map { //мапим данные соответствующим редюсерам
                when(it) {
                    is TargetData.Editors -> TargetsViewStateReducer.EditorsLoaded(it.data)
                    is TargetData.Themed -> TargetsViewStateReducer.Themed(it.data)
                    is TargetData.ForMen -> TargetsViewStateReducer.ForMen(it.data)
                    is TargetData.ForWomen -> TargetsViewStateReducer.ForWomen(it.data)
                }
            }
            .onErrorReturn { TargetsViewStateReducer.Error(it) } //в случае ерора, место краша делаем ресюер, который создаст стейт с ерором для рендеринга
            .startWith(TargetsViewStateReducer.Loading) //начинаем с реюдюсера для загрузки
            .subscribeOn(Schedulers.io())
    private fun complete() = Observable.just(TargetsViewStateReducer.Loaded)


}


