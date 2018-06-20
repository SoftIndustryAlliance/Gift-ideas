package com.soft_industry.findgift.presentation.pages.giftselection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soft_industry.findgift.R
import com.soft_industry.findgift.utils.adapter.BaseViewHolder
import com.soft_industry.findgift.utils.adapter.ViewHolderFactory
import com.soft_industry.findgift.domain.entities.Gift
import kotlinx.android.synthetic.main.item_gift.*
import java.androidx.core.content.findDrawalbeIdByName

/**
 * Created by user on 3/28/18.
 */
class GiftViewHolder(val context: Context, override val containerView: View, listener: (Gift, Float, Float)-> Boolean) :
        BaseViewHolder<Gift>(containerView, touchListener = listener) {

    override fun onBind(gift: Gift) {
        image_gift.setImageResource(context.findDrawalbeIdByName(gift.icon))
    }

    class Factory(private val listener: (Gift, Float, Float)-> Boolean): ViewHolderFactory<Gift>() {
        override fun createViewHolder(parent: ViewGroup): BaseViewHolder<Gift> {
            val context = parent.context
            val inflater = LayoutInflater.from(context)
            val containerView = inflater.inflate(R.layout.item_gift, parent, false)
            return GiftViewHolder(context, containerView, listener)
        }

    }

}