package com.soft_industry.findgift.presentation.pages.giftdetails

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.*
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewAnimationUtils
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.forEach
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxrelay2.PublishRelay
import com.soft_industry.findgift.R
import com.soft_industry.findgift.domain.entities.Gift
import com.soft_industry.findgift.presentation.pages.map.MapActivity
import com.soft_industry.findgift.utils.plusAssign
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_gift_details.*
import java.androidx.core.content.findDrawalbeIdByName
import java.androidx.core.view.View.doOnGlobalLayout


class GiftDetailsActivity : AppCompatActivity(), Observer<GiftDetailsContract.State> {
    companion object {
        val KEY_GIFT = "GIFT"
        val KEY_X ="X"
        val KEY_Y ="Y"
        val MIDDLE_OF_SCREEN = Float.MIN_VALUE
        @JvmStatic
        fun start(context: Context, gift: Gift, x: Float, y: Float) {
            val intent=  Intent(context, GiftDetailsActivity::class.java)
                    .apply {
                        putExtra(KEY_GIFT, gift)
                        putExtra(KEY_X, x)
                        putExtra(KEY_Y, y)
                    }
            context.startActivity(intent)
        }
    }

    private var gift: Gift? = null
    private var x = 0
    private var y = 0
    private var height = 0.0
    private var width = 0.0
    private val actions = PublishRelay.create<GiftDetailsContract.Action>()
    private val disposables = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move)
        setContentView(R.layout.activity_gift_details)
        initModel()
        fab_close_gift.setOnClickListener { unreveal() }
        if (savedInstanceState == null) {
            root_details.doOnGlobalLayout { reveal() }
        }
        text_suggest_buy.setOnClickListener { gift?.let { openMapActivity(it) } }

        initMetrics()

    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
    override fun onChanged(state: GiftDetailsContract.State) {
        state.gift?.let { renderGiftDetails(it) }
    }

    override fun finish() {
        unreveal()
        super.finish()
    }

    fun initModel() {
        val model = ViewModelProviders.of(this, GiftDetailsViewModel.Factory)[GiftDetailsViewModel::class.java]
        model.state.observe(this, this)
        model.state.value?.let(::onChanged)
        disposables += actions.subscribe(model.input)
        actions.accept(loadGiftAction())

    }
    private fun initMetrics() {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        width = metrics.widthPixels.toDouble()
        height = metrics.heightPixels.toDouble()

        val receivedX = intent.getFloatExtra(KEY_X, 0f)
        x = (if (receivedX == MIDDLE_OF_SCREEN) width.toFloat() / 2 else receivedX).toInt()
        val receivedY = intent.getFloatExtra(KEY_Y, 0f)
        y = (if (receivedY == MIDDLE_OF_SCREEN) height.toFloat() / 2 else receivedY).toInt()
    }

    private fun loadGiftAction() =
            GiftDetailsContract.Action.LoadGiftAction(intent.extras.getParcelable(KEY_GIFT))

    private fun openMapActivity(gift: Gift) {
        MapActivity.start(this, gift)
    }

    private fun renderGiftDetails(gift: Gift) {
        this.gift = gift
        text_gift_title.text = gift.name
        text_gift_caption.text = gift.caption
        text_gift_usage.text = gift.caption
        image_gift.setImageResource(findDrawalbeIdByName(gift.icon))
    }

    private fun notifyLocationPermissionRequired() {
        Snackbar.make(root_details,"This app require location permissions in order to find closest shop", Snackbar.LENGTH_SHORT)
                .show()
    }

    private fun reveal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val startRadius = 0f
            val endRadius = Math.hypot(width, height).toFloat()
            root_details.visibility = View.VISIBLE
            val anim = ViewAnimationUtils.createCircularReveal(root_details, x.toInt(), y.toInt(), startRadius, endRadius)
            anim.doOnEnd { animateContentReveal() }
            anim.start()
        } else {
            root_details.visibility = View.VISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun animateContentReveal() {
        val set = TransitionSet().apply {
            addTransition(Fade())
            addTransition(Explode())
            addTransition(ChangeBounds())
            addTransition(ChangeTransform())
            addTransition(ChangeClipBounds())
        }
        TransitionManager.beginDelayedTransition(layout_gift_content, set)
        layout_gift_content.forEach { it.visibility = View.VISIBLE }

    }

    private fun unreveal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            hideContent()
            val endRadius = 0f
            val startRadius = Math.hypot(width, height).toFloat()
            val anim = ViewAnimationUtils.createCircularReveal(root_details, x, y, startRadius, endRadius)
            anim.doOnEnd {
                root_details.visibility = View.INVISIBLE
                finish()
            }
            anim.start()
        } else{
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun hideContent() {
        val set = TransitionSet().apply {
            addTransition(Fade())
            addTransition(Explode())
            addTransition(ChangeBounds())
            addTransition(ChangeTransform())
            addTransition(ChangeClipBounds())
        }
        TransitionManager.beginDelayedTransition(layout_gift_content, set)
        layout_gift_content.forEach { it.visibility = View.GONE }
    }

}

