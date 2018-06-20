package com.soft_industry.findgift.presentation.pages.randomgift

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.Some
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.jakewharton.rxrelay2.PublishRelay
import com.soft_industry.findgift.App
import com.soft_industry.findgift.R
import com.soft_industry.findgift.domain.entities.GiftTarget
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_random_gift.*
import javax.inject.Inject

class RandomGiftActivity: MviActivity<RandomGiftView, RandomGiftPresenter>(), RandomGiftView {


    @Inject lateinit var presenter: RandomGiftPresenter
    private val shakeRelay = PublishRelay.create<Boolean>()
    private lateinit var shakeHandler: ShakeHandler

    companion object {
        val GIFT_TARGET_ARG = ""
        fun start(context: Context, giftTarget: GiftTarget?) {
            val intent=  Intent(context, RandomGiftActivity::class.java)
                    .apply { giftTarget?.let { putExtra(GIFT_TARGET_ARG, giftTarget) } }
            context.startActivity(intent)
        }

    }

    //  Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App)
                .component
                .randomComponent(RandomModule(this))
                .inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_gift)
        fab_close_gift.setOnClickListener { finish() }
        shakeHandler = ShakeHandler(this)
        shakeHandler.setOnShakeListener { shakeRelay.accept(true) }

    }

    override fun onResume() {
        super.onResume()
        shakeHandler.registerListener()
    }

    override fun onPause() {
        super.onPause()
        shakeHandler.unregisterListener()
    }

    //  MVI
    override fun createPresenter() = presenter

    override fun loadGiftIntent(): Observable<Optional<GiftTarget>> {
        val value = intent.getParcelableExtra<GiftTarget>(GIFT_TARGET_ARG)
        return Observable.just(if (value == null) {
            None
        } else {
            Some(value)
        })
    }

    override fun shakeIntent() = shakeRelay
            .doOnNext { Log.e("RandomGiftActivity", "Got shake event") }

    override fun render(randomGiftViewState: RandomGiftViewState) {
        renderLabel(randomGiftViewState.targetLabel)
        if (randomGiftViewState.animateShake) {
            shakeHandler.unregisterListener()
            handleAnimation()
        }

    }
    //   internal
    private fun handleAnimation() {
        val pivotX = image_phone_container.width / 2
        val pivotY = image_phone_container.height
        val endAnimation = createAnimation(-10f, 0f, pivotX, pivotY) {}
        val from = -10f
        val to = 10f
        val animation = createAnimation(from, to, pivotX, pivotY) {
            image_phone_container.startAnimation(endAnimation)
        }
        animation.repeatCount = 3
        image_phone_container.startAnimation(animation)
    }

    private fun createAnimation(from: Float,
                                to: Float,
                                pivotX: Int,
                                pivotY: Int,
                                action: () -> Unit)
            : Animation {
        return RotateAnimation(from, to, pivotX.toFloat(), pivotY.toFloat())
                .apply {
                    repeatCount = Animation.INFINITE
                    repeatMode = Animation.REVERSE
                    duration = 300
                    interpolator = AccelerateInterpolator()
                    setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(animation: Animation?) {}
                        override fun onAnimationStart(animation: Animation?) {}
                        override fun onAnimationEnd(animation: Animation?) {
                            action()
                        }

                    })
                }
    }

    private fun renderLabel(targetLabel: String) {
        val label = getString(R.string.title_random_idea)
        label_random_title.text = String.format(label, targetLabel)
    }

}