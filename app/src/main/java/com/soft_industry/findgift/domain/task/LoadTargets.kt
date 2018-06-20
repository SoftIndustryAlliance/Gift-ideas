package com.soft_industry.findgift.domain.task

import com.soft_industry.findgift.domain.repository.DataRepository
import com.soft_industry.findgift.presentation.pages.targetselection.TargetsViewStateReducer
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by user on 3/26/18.
 */
class LoadTargets @Inject constructor(val dataRepository: DataRepository) {
    fun execute(): Observable<TargetsViewStateReducer> {
        return Observable.concat(load(), complete())
                .cast(TargetsViewStateReducer::class.java)
                .onErrorReturn { TargetsViewStateReducer.Error(it) }
                .startWith(TargetsViewStateReducer.Loading())
                .subscribeOn(Schedulers.io())

    }

    private fun load(): Observable<TargetsViewStateReducer> {
        return Observable.merge(loadEditors(),
                loadThemed(),
                loadForWomen(),
                loadForMen())
    }

    private fun complete() = Observable.just(TargetsViewStateReducer.Loaded())

    private fun loadEditors() = dataRepository.loadEditors()
            .map { TargetsViewStateReducer.EditorsLoaded(it) }

    private fun loadThemed() = dataRepository.loadThematic()
            .map { TargetsViewStateReducer.Themed(it) }

    private fun loadForWomen() = dataRepository.loadForWomen()
            .map { TargetsViewStateReducer.ForWomen(it) }


    private fun loadForMen() = dataRepository.loadForMen()
            .map { TargetsViewStateReducer.ForMen(it) }


}