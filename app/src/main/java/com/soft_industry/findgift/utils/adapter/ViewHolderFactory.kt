package com.soft_industry.findgift.utils.adapter

import android.view.ViewGroup

/**
 * Created by user on 4/11/18.
 */

abstract class ViewHolderFactory<T> {
    abstract fun createViewHolder(parent: ViewGroup): BaseViewHolder<T>
}
