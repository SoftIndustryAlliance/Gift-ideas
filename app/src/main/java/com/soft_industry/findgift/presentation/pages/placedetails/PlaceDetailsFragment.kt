package com.soft_industry.findgift.presentation.pages.placedetails

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jakewharton.rxrelay2.PublishRelay
import com.soft_industry.findgift.App
import com.soft_industry.findgift.R
import com.soft_industry.findgift.domain.entities.NearestPlace
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.plusAssign
import javax.inject.Inject

class PlaceDetailsFragment : BottomSheetDialogFragment() {
    companion object {
        private const val PLACE_KEY = "place"
        fun newInstance(place: NearestPlace) = PlaceDetailsFragment().apply {
            arguments = bundleOf(PLACE_KEY to place)
        }
    }

    @Inject
    lateinit var viweModelFactory: PlaceDetailsViewModelFactory
    private val disposables = CompositeDisposable()
    val populate = PublishRelay.create<NearestPlace>()
    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as App).component.inject(this)

        super.onCreate(savedInstanceState)
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_place_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model = ViewModelProviders.of(this, viweModelFactory)
                .get(PlaceDetailsViewModel::class.java)
        var binder : PlaceDetailsBinder = PlaceDetailsBinder.Impl(view)
        model.state.observe(this, Observer { binder.renderer.accept(it) })
        model.input.accept(PlaceDetailsAction.RenderPlace(arguments?.getParcelable(PLACE_KEY) as NearestPlace))
        model.state.value?.let(binder.renderer::accept)
        disposables += binder.showRouteRequested
                .map(NearestPlace::latLng)
                .map (PlaceDetailsAction::ShowRoute)
                .subscribe(model.input)
        disposables += binder

    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }


}
