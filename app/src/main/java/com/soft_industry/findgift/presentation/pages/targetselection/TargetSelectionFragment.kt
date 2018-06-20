package com.soft_industry.findgift.presentation.pages.targetselection

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnNextLayout
import com.hannesdorfmann.mosby3.mvi.MviFragment
import com.jakewharton.rxrelay2.PublishRelay
import com.soft_industry.findgift.App
import com.soft_industry.findgift.R
import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.presentation.pages.giftselection.GiftSelectionFragment
import com.soft_industry.findgift.presentation.pages.randomgift.RandomGiftActivity
import com.soft_industry.findgift.presentation.pages.targetselection.viewholders.EditorsViewHolder
import com.soft_industry.findgift.presentation.pages.targetselection.viewholders.ThemedViewHolder
import com.soft_industry.findgift.utils.DEFAULT_ANIMATION_DURATION
import com.soft_industry.findgift.utils.Transitions
import com.soft_industry.findgift.utils.adapter.DefaultAdapter
import com.soft_industry.findgift.utils.addDefaultTransitions
import com.soft_industry.findgift.utils.plusAssign
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import kotlinx.android.synthetic.main.fragment_target_selection.*
import javax.inject.Inject

/**
 * Created by user on 3/23/18.
 */
class TargetSelectionFragment : MviFragment<TargetSelectionView, TargetSelectionPresenter>(), TargetSelectionView {
    private lateinit var editorsAdapter: DefaultAdapter<GiftTarget>
    private lateinit var themedAdapter: DefaultAdapter<GiftTarget>
    private lateinit var forMenAdapter: DefaultAdapter<GiftTarget>
    private lateinit var forWomenAdapter: DefaultAdapter<GiftTarget>
    @Inject lateinit var presenter: TargetSelectionPresenter
    private lateinit var handler: Handler
    private val hideHintEndAction = Runnable { hideHint() }



    private val disposables = CompositeDisposable()

    private val hintRendererRelay = PublishRelay.create<Boolean>()
    private var hintVisible = false

    companion object {
        @JvmStatic
        fun create(): TargetSelectionFragment {
            return TargetSelectionFragment().apply {
                addDefaultTransitions()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        (activity!!.application as App).component.inject(this)
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_target_selection, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler = Handler()
        val listener: (GiftTarget) -> Unit = { openGiftSelectionForTarget(it) }
        editorsAdapter = DefaultAdapter(EditorsViewHolder.Factory(listener))
        themedAdapter = DefaultAdapter(ThemedViewHolder.Factory(listener))
        forMenAdapter = DefaultAdapter(ThemedViewHolder.Factory(listener))
        forWomenAdapter = DefaultAdapter(ThemedViewHolder.Factory(listener))
        fab_add.setOnClickListener { RandomGiftActivity.start(context!!, null) }

        setupList(list_editors_collection, editorsAdapter)
        setupList(list_thematic_collection, themedAdapter)
        setupList(list_for_men, forMenAdapter)
        setupList(list_for_women, forWomenAdapter)
        disposables += hintRendererRelay
                .subscribe(this::renderShowHint)
    }



    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(hideHintEndAction)
        disposables.dispose()

    }

    override fun loadTargetListIntent() = Observable.just(true)

    override fun createPresenter() = presenter


    override fun render(state: TargetSelectionViewState) {
        hintRendererRelay.accept(state.showHint)
        editorsAdapter.updateItems(state.editors)
        themedAdapter.updateItems(state.thematic)
        forMenAdapter.updateItems(state.formen)
        forWomenAdapter.updateItems(state.forwomen)
        renderError(state.error)
    }



    private fun setupList(listView: RecyclerView, adapter: DefaultAdapter<GiftTarget>) {
        listView.adapter = adapter
        listView.itemAnimator = SlideInRightAnimator()
        listView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }




    private fun renderShowHint(showHint: Boolean) {
        if (showHint) {
            showHint()
        } else{
            hideHints()
        }
    }

    private fun showHint() {
        if (!hintVisible) {
            hintVisible = true
            prepareHintViewsForAnimations()
            layout_hint_fab.doOnNextLayout { animateHintAppearing() }
        }
    }

    private fun prepareHintViewsForAnimations() {
        layout_hint_fab.visibility = View.VISIBLE
        image_hand.visibility = View.VISIBLE
        layout_hint_fab.alpha = 1f
        layout_hint_fab.translationY = layout_hint_fab.height.toFloat()
    }



    private fun animateHintAppearing() {
        ViewCompat.animate(layout_hint_fab)
                .translationY(0f)
                .withEndAction {
                    animateHintLabelAppearing(label_hint_tap)
                    animateHintLabelAppearing(label_hint_desc)
                    animateHintLabelAppearing(image_arrow)
                }
                .start()
    }

    private fun animateHintLabelAppearing(label: View) {
        label.visibility = View.VISIBLE
        label.alpha = 0f
        ViewCompat.animate(label)
                .alpha(1f)
                .start()
    }


    private fun hideHints() {
        if(!hintVisible) {
            return
        }
        hintVisible = false
        label_hint_tap.visibility = View.GONE
        label_hint_desc.visibility = View.GONE
        image_arrow.visibility = View.GONE
        image_hand.visibility = View.GONE
        ViewCompat.animate(layout_hint_fab)
                .translationY(layout_hint_fab.height.toFloat())
                .withEndAction(hideHintEndAction)
    }
    private fun hideHint() {
        layout_hint_fab.visibility = View.GONE
    }

    private fun renderError(error: Throwable?) {
        text_error.visibility = if (error == null)  View.GONE  else  View.VISIBLE
    }

    private fun openGiftSelectionForTarget(target: GiftTarget) {
        openFragment(GiftSelectionFragment.newInstance(target))
    }

    private fun openFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.apply {
            exitTransition = Transitions.createDefaultTransitionSet().apply {
                duration = DEFAULT_ANIMATION_DURATION / 2
                startDelay = 0
            }
            enterTransition = Transitions.createDefaultTransitionSet().apply {
                duration = DEFAULT_ANIMATION_DURATION
                startDelay = DEFAULT_ANIMATION_DURATION
            }
            replace(R.id.fragment_container, fragment)
            addToBackStack(TargetSelectionFragment::class.java.simpleName)
            commit()
        }
    }


}


