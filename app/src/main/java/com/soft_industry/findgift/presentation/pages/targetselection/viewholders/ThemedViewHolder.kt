package com.soft_industry.findgift.presentation.pages.targetselection.viewholders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soft_industry.findgift.R
import com.soft_industry.findgift.utils.adapter.BaseViewHolder
import com.soft_industry.findgift.utils.adapter.ViewHolderFactory
import com.soft_industry.findgift.domain.entities.GiftTarget
import kotlinx.android.synthetic.main.item_themed_target.*

/**
 * Created by user on 3/26/18.
 */
class ThemedViewHolder(val context: Context,
                       override val containerView: View,
                       private val itemClickListener: (GiftTarget) -> Unit) :
        BaseViewHolder<GiftTarget>(containerView, itemClickListener) {
    override fun onBind(target: GiftTarget) {
        image_target.setBackgroundResource(target.resource)
        label_themed_target.text = target.label
    }

    class Factory(val listener: (GiftTarget)->Unit): ViewHolderFactory<GiftTarget>() {
        override fun createViewHolder(parent: ViewGroup): BaseViewHolder<GiftTarget> {
            val context = parent.context
            val inflater = LayoutInflater.from(context)
            val containerView: View = inflater.inflate(R.layout.item_themed_target, parent, false)
            return ThemedViewHolder(context, containerView, listener)
        }
    }
}