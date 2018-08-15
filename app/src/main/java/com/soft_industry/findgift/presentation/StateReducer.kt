package com.soft_industry.findgift.presentation

/**
 * Reducer interface
 */
interface StateReducer<VS> {
    /**
     * reducer method, which transforms old view state to new
     * @param old old view state
     * @return new view state
     */
    fun reduce(old: VS): VS
}