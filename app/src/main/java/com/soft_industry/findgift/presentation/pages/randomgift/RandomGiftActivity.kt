package com.soft_industry.findgift.presentation.pages.randomgift

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.util.Log
import android.view.animation.Animation
import android.view.animation.OvershootInterpolator
import android.view.animation.RotateAnimation
import com.gojuno.koptional.Optional
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
            val anim = ActivityOptionsCompat.makeCustomAnimation(context, android.R.anim.fade_in, android.R.anim.fade_out)
                    .toBundle()
            val intent=  Intent(context, RandomGiftActivity::class.java)
                    .apply {
                        giftTarget?.let { putExtra(GIFT_TARGET_ARG, giftTarget) }
                    }
            context.startActivity(intent, anim)
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
        return Observable.just(Optional.toOptional(value))
    }

    override fun shakeIntent() = shakeRelay
            .doOnNext { Log.e("RandomGiftActivity", "Got shake event") }

    override fun render(randomGiftViewState: RandomGiftViewState) {
        renderLabel(randomGiftViewState.targetLabel)
        if (randomGiftViewState.animateShake) {
            shakeHandler.unregisterListener()

            animateShake()
        }

    }
    //   internal
    private fun animateShake() {
        val pivotX = image_phone_container.width / 2
        val pivotY = image_phone_container.height
        val degree = 3f
        val endAnimation = createShakeAnimation(-degree, 0f, pivotX, pivotY) {}
        val from = -degree
        val to = degree
        val animation = createShakeAnimation(from, to, pivotX, pivotY) {
            image_phone_container.startAnimation(endAnimation)
        }
        animation.repeatCount = 3
        image_phone_container.startAnimation(animation)
    }

    private fun createShakeAnimation(from: Float,
                                     to: Float,
                                     pivotX: Int,
                                     pivotY: Int,
                                     action: () -> Unit)
            : Animation {
        return RotateAnimation(from, to, pivotX.toFloat(), pivotY.toFloat())
                .apply {
                    repeatCount = Animation.INFINITE
                    repeatMode = Animation.REVERSE
                    duration = 400
                    interpolator = OvershootInterpolator()
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