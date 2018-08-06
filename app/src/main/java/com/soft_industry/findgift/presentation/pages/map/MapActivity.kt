package com.soft_industry.findgift.presentation.pages.map

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.soft_industry.findgift.App
import com.soft_industry.findgift.R
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.NearestPlace
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_map.*
import javax.inject.Inject

class MapActivity : MviActivity<MapView, MapPresenter>(), OnMapReadyCallback, MapView {
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
    @Inject lateinit var presenter: MapPresenter
    //activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true

    }
    //MVI lifecycle
    override fun createPresenter(): MapPresenter {
//        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        val locationRepository = LocationRepositoryImpl(locationManager)
//        val placesService = Retrofit.Builder()
//                .baseUrl("https://maps.googleapis.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
//                .build()
//                .create(PlacesService::class.java)
//        val placesRepository = GooglePlacesRepository(placesService, getString(R.string.places_api_key))
//        val settingsRepository = SettingsRepositoryImpl(this)
//        val appDatabase = Room.databaseBuilder(this, AppDatabase::class.java, "dbn.db").build()
//        val dataRepository = DataRepositoryImpl(this, appDatabase.dataDao())
//        val task = LoadNearestPlacesByType(locationRepository, placesRepository, settingsRepository, dataRepository)
//        return MapPresenter(task)
        return presenter
    }
    override fun loadPlacesForGift(): Observable<Gift> {
        return Observable.just(intent.getParcelableExtra(GIFT))
    }

    override fun render(state: MapViewState) {
        state.apply {
            places?.let { it -> renderPoints(it) }
            renderLoading(loading)
        }

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
        Observable.fromIterable(points)
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
