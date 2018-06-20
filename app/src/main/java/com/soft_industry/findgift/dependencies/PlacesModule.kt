package com.soft_industry.findgift.dependencies

import android.content.Context
import com.soft_industry.findgift.R
import com.soft_industry.findgift.data.datasources.remote.PlacesService
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by user on 4/19/18.
 */
@Module
class PlacesModule {
    @Provides
    fun providePlacesService(): PlacesService {
        return  Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(PlacesService::class.java)
    }
    @Provides
    fun providePlacesApiKey(context: Context) = context.getString(R.string.places_api_key)




}