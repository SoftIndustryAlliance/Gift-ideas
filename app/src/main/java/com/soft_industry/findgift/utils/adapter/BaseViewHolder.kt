package com.soft_industry.findgift.utils.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by user on 3/26/18.
 */
abstract class BaseViewHolder<T>(
        containerView: View,
        private val itemClickListener: (T) -> Unit = {},
        private val viewClickListener: (BaseViewHolder<T>) -> Unit = {},
        private val touchListener: (T, Float, Float) -> Boolean = { _, _, _ -> false }
) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

    var target: T? = null
    init {
        setClickListeners(containerView)
        setOnTouchListener(containerView )
    }

    private fun setClickListeners(containerView: View) {
        containerView.setOnClickListener {
            target?.let(itemClickListener)
            viewClickListener(this)
        }
    }

    private fun setOnTouchListener(containerView: View) {
        containerView.setOnTouchListener { v, event ->
            return@setOnTouchListener if (event.action == MotionEvent.ACTION_UP) {
                val item = target
                if (item != null) {
                    touchListener(item, event.rawX, event.rawY)
                } else false
            } else false
        }
    }

    abstract fun onBind(target: T)

    fun bind(target: T) {
        this.target = target
        onBind(target)
    }



}