package com.soft_industry.findgift.domain.entities

import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

/**
 * Created by user on 3/21/18.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class NearestPlace(val name: String, val latLng: LatLng, val thumbnail: String) : Parcelable
