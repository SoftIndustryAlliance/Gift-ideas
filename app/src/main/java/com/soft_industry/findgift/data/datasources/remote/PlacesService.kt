package com.soft_industry.findgift.data.datasources.remote

import com.soft_industry.findgift.data.dto.NearbysearchDTO
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by user on 3/21/18.
 */
interface PlacesService {
    @GET("maps/api/place/nearbysearch/json?radius=5000")
    fun getNearesLocationsByType(
            @Query("location", encoded = true) latlng: String,
            @Query("type") type: String,
            @Query("language") language: String,
            @Query("key") key: String): Observable<NearbysearchDTO>

}