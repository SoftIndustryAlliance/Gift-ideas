package com.soft_industry.findgift.data.repository

import android.location.Location
import android.util.Log
import com.facebook.CallbackManager
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.facebook.places.PlaceManager
import com.facebook.places.model.PlaceFields
import com.google.android.gms.maps.model.LatLng
import com.soft_industry.findgift.domain.entities.NearestPlace
import com.soft_industry.findgift.domain.repository.PlacesRepository
import io.reactivex.Observable
import com.facebook.places.model.PlaceSearchRequestParams
import io.reactivex.ObservableSource
import io.reactivex.Observer
import java.lang.Exception
import javax.inject.Inject


class FacebookPlacesRepository @Inject constructor(): PlacesRepository {
    override fun loadNearestPlaces(type: String, latLng: LatLng, languageCode: String): Observable<List<NearestPlace>> {
        return Observable.create<GraphResponse> { emitter ->

            val builder = PlaceSearchRequestParams.Builder()
                    .addField(PlaceFields.NAME)
                    .addField(PlaceFields.LOCATION)
                    .addField(PlaceFields.PHONE)
                    .setSearchText(type)
            val location = Location("custom")
            location.longitude = latLng.longitude
            location.latitude = latLng.latitude
            try {
                val result = PlaceManager.newPlaceSearchRequestForLocation(builder.build(), location).executeAndWait()
                emitter.onNext(result)
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
                .doOnNext { Log.e("Facebookk", "got response ${it.rawResponse}") }
                .map { NearestPlace(
                        name = "Bookshop",
                        latLng = LatLng(50.9089649,34.7972135),
                        thumbnail = "http://edinstvennaya.ua/pictures/article/17784_max.jpg"
                )
                }
                .map { listOf(it) }
    }


}


