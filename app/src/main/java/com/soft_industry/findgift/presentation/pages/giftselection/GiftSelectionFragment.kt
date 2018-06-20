package com.soft_industry.findgift.presentation.pages.giftselection

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.soft_industry.findgift.App
import com.soft_industry.findgift.R
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.presentation.pages.giftdetails.GiftDetailsActivity
import com.soft_industry.findgift.presentation.pages.randomgift.RandomGiftActivity
import com.soft_industry.findgift.utils.adapter.DefaultAdapter
import com.soft_industry.findgift.utils.addDefaultTransitions
import io.reactivex.Observable
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_gift_selection.*
import javax.inject.Inject

/**
 * Created by user on 3/23/18.
 */
class GiftSelectionFragment : MviFragment<GiftSelectionView, GiftSelectionPresenter>(), GiftSelectionView {
    private val KEY_TARGET = "taget"
    companion object {
        @JvmStatic fun newInstance(target: GiftTarget): GiftSelectionFragment {
            return GiftSelectionFragment().apply {
                addDefaultTransitions()
                arguments = bundleOf(Pair(KEY_TARGET, target))
            }
        }
    }

    private val defaultAdapter = DefaultAdapter(GiftViewHolder.Factory(this::openGiftDetails))
    private lateinit var giftTarget: GiftTarget

    @Inject lateinit var presenter: GiftSelectionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as App).component.inject(this)
        super.onCreate(savedInstanceState)
        giftTarget = arguments!!.getParcelable(KEY_TARGET)


    }
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gift_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_gifts.adapter = defaultAdapter
        list_gifts.itemAnimator = SlideInUpAnimator()
        list_gifts.layoutManager = GridLayoutManager(context, 2)
        fab_add.setOnClickListener { RandomGiftActivity.start(context!!, giftTarget) }
    }


    override fun loadGiftList() = Observable.just(giftTarget)

    override fun createPresenter() = presenter

    override fun render(state: GiftSelectionViewState) {
        state.apply {
            renderLoading(loading)
            defaultAdapter.updateItems(content)
            renderError(error)
        }
    }

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