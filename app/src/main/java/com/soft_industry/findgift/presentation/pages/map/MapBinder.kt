package com.soft_industry.findgift.presentation.pages.map

import android.annotation.SuppressLint
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
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
import io.reactivex.rxkotlin.withLatestFrom

interface MapBinder : Disposable{

    val stateRenderer: Consumer<MapState>
    val showNearestPlaceDetails: Observable<NearestPlace>

    @SuppressLint("MissingPermission")
    class Impl(mapFragment: SupportMapFragment, root: android.view.View, renderer: Renderer)
        : MapBinder {

        override val stateRenderer = PublishRelay.create<MapState>()
        override val showNearestPlaceDetails = PublishRelay.create<NearestPlace>()

        private val disposable = CompositeDisposable()
        private val mapInitialized = PublishRelay.create<GoogleMap>()

        private val loadingView = AndroidView(root.findViewById(R.id.pb_shops_loading), renderer)
        private val places = stateRenderer
                .filter { it.places != null }
                .flatMapIterable { it.places!! }
        private val placesRenderer = Observables.combineLatest(mapInitialized, places)
        private val loading = stateRenderer.map {
            when(it.loading) {
                true -> View.Visibility.VISIBLE
                false -> View.Visibility.GONE
            }
        }
        private val zoomToPosition = stateRenderer
                .filter { it.currentPlace != null }
                .map { it.currentPlace!! }
                .withLatestFrom(mapInitialized)

        init {
            mapFragment.getMapAsync(mapInitialized::accept)
            disposable += loadingView.change.visibility(loading)

            disposable += mapInitialized.subscribe { map ->
                map.isMyLocationEnabled = true
                map.moveCamera(CameraUpdateFactory.zoomTo(15f))
                map.setOnMarkerClickListener { marker->
                    marker.tag
                        ?.takeIf { it is NearestPlace }
                        ?.let { showNearestPlaceDetails.accept(it as NearestPlace) }
                    return@setOnMarkerClickListener true
                }
            }
            disposable += placesRenderer.subscribe { (map, place) ->
                val marker  = map.addMarker(place.asMarker())
                marker.tag = place
            }

            disposable += zoomToPosition.subscribe { (position, map) ->
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15f))
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