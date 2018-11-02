package com.soft_industry.findgift.presentation.pages.map

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.lyft.domic.android.rendering.AndroidRenderer
import com.soft_industry.findgift.App
import com.soft_industry.findgift.R
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.presentation.pages.placedetails.PlaceDetailsFragment
import com.soft_industry.findgift.presentation.pages.placedetails.PlaceDetailsFragment.Companion.newInstance
import com.soft_industry.findgift.utils.plusAssign
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_map.*
import javax.inject.Inject

class MapActivity : AppCompatActivity() {

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

        val binder = MapBinder.Impl(mapFragment, findViewById(R.id.root_view), AndroidRenderer())

        val model = ViewModelProviders.of(this, viweModelFactory)
                .get(MapViewModel::class.java)
        model.state.observe(this, Observer { binder.stateRenderer.accept(it) })
        model.state.value?.let { binder.stateRenderer.accept(it) }
        model.input.accept(loadPlacesForGift())

        disposables += binder.showNearestPlaceDetails.subscribe {
            PlaceDetailsFragment.newInstance(it).show(supportFragmentManager, "dialog")
        }
        disposables += binder

    }


    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }



    private fun loadPlacesForGift(): MapAction.LoadPlacesForGiftAction {
        return MapAction.LoadPlacesForGiftAction(intent.getParcelableExtra(GIFT))
    }
}
