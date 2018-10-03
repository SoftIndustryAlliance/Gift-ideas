package com.soft_industry.  findgift.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import com.soft_industry.findgift.utils.plusAssign
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel<Action,State>(scheduler: Scheduler)
    : ViewModel() {
    private val TAG = "BaseViewModel"
    private val mutableState =  MutableLiveData<State>()
    private val initialState: State by lazy(LazyThreadSafetyMode.NONE) { createInitialState() }
    protected val viewStateObservable: Observable<State>
    private val subscriptions = CompositeDisposable()
    private val output: PublishRelay<State> = PublishRelay.create()

    val state: LiveData<State> = mutableState
    val input: PublishRelay<Action> = PublishRelay.create()

    init {
        viewStateObservable = input
                .observeOn(scheduler) //в роуте мы можем работать с какими-то данными зависимыми от мейн треда, но лучше не надо :)
                .compose { it -> it.switchMap { route(it) } }
                .observeOn(Schedulers.computation()) //здесь может быть вычисление сложного диффа, делаем на компьютейшен
                .scan(initialState) { oldVs, reducer -> reducer.reduce(oldVs) }
                .observeOn(scheduler) //дальше мы рендерим на мейн треде
                .doOnNext (::onStateUpdated)
        subscriptions += viewStateObservable
                .subscribe { mutableState.value = it }


    }

    open fun onStateUpdated(state: State) {
        output.accept(state)
    }

    fun doOnNewAction(run: (Action)-> Unit) {
        subscriptions += input.subscribe(run)
    }

    fun doOnNewState(run: (State) -> Unit) {
        subscriptions += output.subscribe(run)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
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