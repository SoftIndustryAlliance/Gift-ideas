package com.soft_industry.findgift.domain.entities

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by user on 3/26/18.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class GiftTarget(val id: Long, val resource: Int, val label: String): Parcelable
