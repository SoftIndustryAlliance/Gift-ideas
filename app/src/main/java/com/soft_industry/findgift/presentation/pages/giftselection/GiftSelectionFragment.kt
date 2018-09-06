package com.soft_industry.findgift.presentation.pages.giftselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.soft_industry.findgift.App
import com.soft_industry.findgift.R
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.presentation.pages.giftdetails.GiftDetailsActivity
import com.soft_industry.findgift.presentation.pages.randomgift.RandomGiftActivity
import com.soft_industry.findgift.utils.adapter.DefaultAdapter
import com.soft_industry.findgift.utils.addDefaultTransitions
import com.soft_industry.findgift.utils.applyArguments
import com.soft_industry.findgift.utils.plusAssign
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_gift_selection.*
import javax.inject.Inject

/**
 * Created by user on 3/23/18.
 */
class GiftSelectionFragment : Fragment(), Observer<GiftSelectionState>, GiftSelectionRenderer {



    companion object {
        private val KEY_TARGET = "taget"
        fun newInstance(target: GiftTarget) = GiftSelectionFragment()
                .addDefaultTransitions()
                .applyArguments(bundleOf(Pair(KEY_TARGET, target)))
    }

    private val defaultAdapter = DefaultAdapter(GiftViewHolder.Factory(this::openGiftDetails))
    private val disposables = CompositeDisposable()

    private lateinit var giftTarget: GiftTarget

    @Inject lateinit var viewModelFactory: GiftSelectionViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as App).component.inject(this)
        super.onCreate(savedInstanceState)
        giftTarget = arguments!!.getParcelable(KEY_TARGET)


    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_gift_selection, container, false)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_gifts.adapter = defaultAdapter
        list_gifts.itemAnimator = SlideInUpAnimator()
        list_gifts.layoutManager = GridLayoutManager(context, 2)
        fab_add.setOnClickListener { RandomGiftActivity.start(context!!, giftTarget) }
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(GiftSelectionViewModel::class.java)
        viewModel.state.observe(this, this)
        viewModel.state.value?.let(::onChanged)
        disposables += loadGiftList().subscribe(viewModel.input)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.dispose()
    }


    override fun onChanged(state: GiftSelectionState) {
        render(state)
    }

    override fun render(state: GiftSelectionState) {
        with(state) {
            renderLoading(loading)
            defaultAdapter.updateItems(content)
            renderError(error)
        }
    }


    private fun loadGiftList() = Observable.just(GiftSelectionAction.LoadGiftListAction(giftTarget))


    private fun renderLoading(loading: Boolean) {

    }

    private fun renderError(error: Throwable?) {
        text_error.visibility = if (error != null) {
            error.printStackTrace()
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun openGiftDetails(gift: Gift, x: Float, y:Float):Boolean {
        GiftDetailsActivity.start(activity!!, gift,x,y)
        return true
    }
}