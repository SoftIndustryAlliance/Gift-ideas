package com.soft_industry.findgift.utils.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by user on 3/26/18.
 */
class DefaultAdapter<T>(
        val baseFactory: ViewHolderFactory<T>,
        val diffCallbackFactory: DiffCallbackFactory<T> = DefaultDiffCallback.Factory())
    : RecyclerView.Adapter<BaseViewHolder<T>>() {

    var items:List<T> = mutableListOf()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            baseFactory.createViewHolder(parent)

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateItems(data: List<T>){
        if (data !== items) {
            updateDataInternal(data)
        }
    }

    private fun updateDataInternal(data: List<T>) {
        val callback = diffCallbackFactory.create(items, data)
        val diff = DiffUtil.calculateDiff(callback)
        items = data
        diff.dispatchUpdatesTo(this)
    }

}