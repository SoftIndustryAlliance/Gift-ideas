package com.soft_industry.findgift.presentation

import io.reactivex.Observable



typealias BodyMapper<VS> = VS.()->VS

typealias DataMapper<VS, T> = (VS, T)->VS


/***
 *
 * Функция которая позволяет избежать написания отдельного StateReducer класса для оператора Map,
 * можно создавать таковые инлайново
 * пример:
 * loadRandomGift.execute(target.toNullable())
 *    .reduceViewState { vs: RandomGiftState, gift ->  vs.copy(gift = gift)}
 *
 *  @mapper  - маппер который принимает старый ViewState и  данные для маппинга
 */

fun <D,VS> Observable<D>.reduceViewState(mapper: DataMapper<VS, D>): Observable<StateReducer<VS>>
        = map { DataReducer(it, mapper) }

/***
 *
 * Функция которая позволяет избежать написания отдельного StateReducer класса для оператора startWith,
 * можно создавать таковые инлайново
 * пример:
 *   loadRandomGift.execute(target.toNullable())
 *     .reduceViewState { vs: RandomGiftState, gift ->  vs.copy(gift = gift)}
 *     .startWithViewState { copy(loading = true) }
 *
 *  @mapper  - маппер член класса ViewState
 */


fun <VS> Observable<StateReducer<VS>>.startWithViewState(mapper: BodyMapper<VS>): Observable<StateReducer<VS>>
        = startWith(EmptyDataReducer(mapper))


/***
 *
 * Функция которая позволяет избежать написания отдельного StateReducer класса для оператора OnErrorReturn,
 * можно создавать таковые инлайново
 * пример:
 *   loadRandomGift.execute(target.toNullable())
 *       .reduceViewState { vs: RandomGiftState, gift ->  vs.copy(gift = gift)}
 *       .startWithViewState { copy(loading = true) }
 *       .onErrorReduceState { vs, error -> vs.copy(error = error) }
 *
 *  @mapper  - маппер который принимает старый ViewState и  данные для маппинга
 */

fun <VS> Observable<StateReducer<VS>>.onErrorReduceState(mapper: (VS, Throwable)->VS): Observable<StateReducer<VS>>
        = onErrorReturn { DataReducer(it, mapper) }


internal data class DataReducer<D,VS>(val data:D, val body: DataMapper<VS,D>) : StateReducer<VS> {
    override fun reduce(old: VS) = body(old, data)
}

internal data class EmptyDataReducer<VS>(val body: BodyMapper<VS>) : StateReducer<VS> {
    override fun reduce(old: VS) = old.body()
}

