package com.soft_industry.findgift.presentation.pages.placedetails

import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxrelay2.PublishRelay
import com.soft_industry.findgift.R
import com.soft_industry.findgift.domain.entities.NearestPlace
import com.soft_industry.findgift.utils.RxPicassoImageLoader
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign

interface PlaceDetailsBinder : Disposable {
    val renderer: Consumer<PlaceDetailsState>
    val showRouteRequested: Observable<NearestPlace>
    class Impl(root: View) : PlaceDetailsBinder {

        private val disposable = CompositeDisposable()

        override val renderer = PublishRelay.create<PlaceDetailsState>()

        private val placeReady = renderer.filter { it.place != null }.map { it.place!! }
        private val showDirectionObservable = RxView.clicks(root.findViewById(R.id.button_show_direction))
        private val placeLabel = RxTextView.text(root.findViewById(R.id.text_place_name))
        private val rxPicassoImageLoader = RxPicassoImageLoader(root.findViewById(R.id.image_photo))

        override val showRouteRequested: Observable<NearestPlace> = Observables.combineLatest(
                showDirectionObservable,
                placeReady,
                { _, place -> place}
        )

        init {
            disposable += placeReady.map { it.name }.subscribe(placeLabel)
            disposable += placeReady.map { it.icon }.subscribe(rxPicassoImageLoader)
        }

        override fun isDisposed() = disposable.isDisposed

        override fun dispose()  = disposable.dispose()

    }
}

