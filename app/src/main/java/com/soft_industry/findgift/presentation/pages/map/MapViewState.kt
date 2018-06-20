package com.soft_industry.findgift.presentation.pages.map

import com.soft_industry.findgift.domain.entities.NearestPlace


class MapViewState(val loading: Boolean,
                   val places: List<NearestPlace>?,
                   val error: Throwable? = null)

