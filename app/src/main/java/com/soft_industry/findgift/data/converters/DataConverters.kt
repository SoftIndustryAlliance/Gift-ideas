package com.soft_industry.findgift.data.converters

import android.content.Context
import com.soft_industry.findgift.data.dto.gifts.GiftDTO
import com.soft_industry.findgift.data.dto.gifts.GiftTargetDTO
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.GiftTarget
import java.androidx.core.content.findDrawalbeIdByName
import java.androidx.core.content.findStringIdByName

/**
 * Created by user on 4/18/18.
 */

fun GiftTargetDTO.mapToEntity(context: Context): GiftTarget {
    return context.run {
        GiftTarget(id, findDrawalbeIdByName(icon), getString(findStringIdByName(label)))
    }
}

fun GiftDTO.mapToEntity(context: Context): Gift{
    return context.run {
        Gift(id, name, icon, caption)
    }
}