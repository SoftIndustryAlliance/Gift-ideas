package com.soft_industry.findgift.utils.adapter

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by user on 4/11/18.
 */
abstract class DiffCallbackFactory<T> {
    abstract fun create(old:List<T>, new: List<T>): DiffUtil.Callback

}