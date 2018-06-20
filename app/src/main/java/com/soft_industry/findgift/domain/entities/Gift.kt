package com.soft_industry.findgift.domain.entities

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by user on 3/28/18.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class Gift(val id: Long, val name: String, val icon: String, val caption: String):Parcelable