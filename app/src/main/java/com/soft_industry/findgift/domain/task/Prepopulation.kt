package com.soft_industry.findgift.domain.task

import com.soft_industry.findgift.data.datasources.local.AppDatabase
import com.soft_industry.findgift.data.datasources.local.DbCallback
import com.soft_industry.findgift.data.dto.DBPopulator
import com.soft_industry.findgift.presentation.pages.splash.SplashViewStateReducer
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class Prepopulation @Inject constructor(val appDatabase: AppDatabase,
                                        val dbCallback: DbCallback) {

    fun execute(): Observable<SplashViewStateReducer> {
        return appDatabase.populationDao()
                .start()
                .toObservable()
                .switchMap { checkPopulatedOrPopulate(it) }
                .filter { it }
                .map { handleState() }
                .startWith(SplashViewStateReducer.StartLoading())
                .onErrorReturn { SplashViewStateReducer.Error(it) }
                .subscribeOn(Schedulers.io())
    }

    private fun checkPopulatedOrPopulate(population: List<DBPopulator>) =
            if (population.isNotEmpty()) Observable.just(true)
            else dbCallback.relay
                    .doOnNext { setPopulated() }

    private fun setPopulated() {
        val timestamp = Calendar.getInstance().timeInMillis
        appDatabase.populationDao()
                .setPopulated(DBPopulator(timestamp = timestamp))
    }

    private fun handleState(): SplashViewStateReducer = SplashViewStateReducer.DatabaseLoaded()

}
