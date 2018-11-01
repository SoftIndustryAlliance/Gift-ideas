package com.soft_industry.findgift.presentation.pages.map

import android.annotation.SuppressLint
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.jakewharton.rxrelay2.PublishRelay
import com.lyft.domic.android.AndroidView
import com.lyft.domic.api.View
import com.lyft.domic.api.rendering.Renderer
import com.soft_industry.findgift.R
import com.soft_industry.findgift.domain.entities.NearestPlace
import com.soft_industry.findgift.utils.plusAssign
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.Observables

interface MapBinder {

    val stateRenderer: Consumer<MapState>
    val placeNavigationRequested: Observable<NearestPlace>

    @SuppressLint("MissingPermission")
    class Impl(mapFragment: MapFragment, root: android.view.View, renderer: Renderer)
        : MapBinder, Disposable {

        override val stateRenderer = PublishRelay.create<MapState>()
        override val placeNavigationRequested = PublishRelay.create<NearestPlace>()

        private val disposable = CompositeDisposable()
        private val mapInitialized = PublishRelay.create<GoogleMap>()

        private val places = stateRenderer
                .filter { it.places != null }
                .flatMapIterable { it.places!! }

        private val loading = stateRenderer.map {
            when(it.loading) {
                true -> View.Visibility.VISIBLE
                false -> View.Visibility.GONE
            }
        }
        private val loadingView = AndroidView(root.findViewById(R.id.pb_shops_loading), renderer)

        private val placesRenderer = Observables.combineLatest(mapInitialized, places)

        init {
            mapFragment.getMapAsync(mapInitialized::accept)
            disposable += mapInitialized.subscribe { map ->
                map.isMyLocationEnabled = true
                map.moveCamera(CameraUpdateFactory.zoomTo(15f))
            }
            disposable += loadingView.change.visibility(loading)
            disposable += placesRenderer.subscribe { (map, place) ->
                val marker  = map.addMarker(place.asMarker())
                marker.tag = place
            }


        }


        override fun isDisposed() = disposable.isDisposed

        override fun dispose()  = disposable.dispose()
    }

}

internal fun NearestPlace.asMarker(): MarkerOptions {
    return MarkerOptions()
            .position(latLng)
            .title(name)

}