package com.soft_industry.findgift.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract open class BaseViewModel<Action,State>(scheduler: Scheduler)
    : ViewModel() {
    private val TAG = "BaseViewModel"
    private val mutableState =  MutableLiveData<State>()
    private val initialState: State by lazy(LazyThreadSafetyMode.NONE) { createInitialState() }
    protected val viewStateObservable: Observable<State>
    private val viewStateSubscription: Disposable

    val state: LiveData<State> = mutableState
    val input: PublishRelay<Action> = PublishRelay.create()

    init {
        viewStateObservable = input
                .observeOn(scheduler)
                .doOnNext(::onNewActionReceived)
                .compose { it -> it.switchMap { route(it) } }
                .observeOn(Schedulers.computation()) //todo add named scheduler to constructor dependencies
                .scan(initialState) { oldVs, reducer -> reducer.reduce(oldVs) }
                .observeOn(scheduler)
                .doOnNext (::onStateUpdated)
        viewStateSubscription = viewStateObservable
                .subscribe { mutableState.value = it }


    }

    open fun onNewActionReceived(action: Action) {

    }
    open fun onStateUpdated(state: State) {

    }

    override fun onCleared() {
        super.onCleared()
        viewStateSubscription.dispose()
    }


    /**
     *
     * Method needed for roting actions to state reducers
     * @param action action to handle
     * @return view state reducer
     * Note: this method called on main scheduler
     */

    abstract fun route(action: Action): Observable<StateReducer<State>>
    abstract fun createInitialState(): State
}