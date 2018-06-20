package com.soft_industry.findgift.presentation

/**
 * Created by user on 3/26/18.
 */
interface StateReducer<VS> {
    fun reduce(oldViewState: VS): VS
}