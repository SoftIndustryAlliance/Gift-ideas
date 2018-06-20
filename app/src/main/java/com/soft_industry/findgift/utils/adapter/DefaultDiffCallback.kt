package com.soft_industry.findgift.utils.adapter

import android.support.v7.util.DiffUtil

/**
 * Created by user on 4/11/18.
 */
class DefaultDiffCallback<T>(val old:List<T>, val new:List<T>): DiffUtil.Callback() {
    override fun getOldListSize() = old.size
    override fun getNewListSize() = new.size
    override fun areItemsTheSame(oldPos: Int, newPos: Int) = old[oldPos] === new[newPos]
    override fun areContentsTheSame(oldPos: Int, newPos: Int) = old[oldPos] == new[newPos]
    class Factory<T>: DiffCallbackFactory<T>() {
        override fun create(old: List<T>, new: List<T>) = DefaultDiffCallback(old, new)

    }
}

