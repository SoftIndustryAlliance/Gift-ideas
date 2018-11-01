package com.soft_industry.findgift.presentation.pages.map

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.soft_industry.findgift.App
import com.soft_industry.findgift.R
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.NearestPlace
import com.soft_industry.findgift.utils.plusAssign
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_map.*
import javax.inject.Inject

class MapActivity : AppCompatActivity(), OnMapReadyCallback, Observer<MapState> {

    companion object {
        val GIFT = "gift"
        @JvmStatic
        fun start(activity: AppCompatActivity, gift: Gift) {
            val intent=  Intent(activity, MapActivity::class.java)
                    .apply { putExtra(GIFT, gift) }
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity)
            activity.startActivity(intent, options.toBundle())
        }

    }

    private lateinit var mMap: GoogleMap
    @Inject lateinit var viweModelFactory: MapViewModelFactory
    private val disposables = CompositeDisposable()

    //activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val model = ViewModelProviders.of(this, viweModelFactory)
                .get(MapViewModel::class.java)
        model.state.observe(this, this)
        model.state.value?.let { onChanged(it) }

        disposables += Observable.just(loadPlacesForGift()).subscribe(model.input)

    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true

    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    override fun onChanged(state: MapState) {
        with(state) {
            places?.let { it -> renderPoints(it) }
            renderLoading(loading)
        }
    }

    private fun loadPlacesForGift(): MapAction.LoadPlacesForGiftAction {
        return MapAction.LoadPlacesForGiftAction(intent.getParcelableExtra(GIFT))
    }


    private fun renderLoading(loading: Boolean) {
        if (loading) {
            pb_shops_loading.visibility = View.VISIBLE
        } else {
            pb_shops_loading.visibility = View.GONE

        }
    }

    // internal methods
    private fun renderPoints(points: List<NearestPlace>) {
        disposables += Observable.fromIterable(points)
                .map(this::toMarkerOptions)
                .subscribe { mMap.addMarker(it) }
        points.firstOrNull()
                ?.let {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it.latLng, 15f))
        }
    }


    private fun toMarkerOptions(point: NearestPlace): MarkerOptions {
        return MarkerOptions()
                .position(point.latLng)
                .title(point.name)
    }
}
