package com.soft_industry.findgift.presentation.pages.randomgift

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.OvershootInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gojuno.koptional.Optional
import com.jakewharton.rxrelay2.PublishRelay
import com.soft_industry.findgift.App
import com.soft_industry.findgift.R
import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.utils.plusAssign
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_random_gift.*
import javax.inject.Inject

class RandomGiftActivity: AppCompatActivity(), Observer<RandomGiftState> {
    @Inject lateinit var viewModelFactory: RandomGiftViewModelFactory


    private lateinit var shakeHandler: ShakeHandler
    private val shakeIntent= PublishRelay.create<Boolean>()
    private val actions = PublishRelay.create<RandomGiftAction>()
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

    private var disposables = CompositeDisposable()

    //  Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App)
                .component
                .randomComponent(RandomModule(this))
                .inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_gift)
        fab_close_gift.setOnClickListener { finish() }

        initViewModel()
        disposables+= shakeIntent
                .take(1)
                .map { RandomGiftAction.ShakeAction }
                .subscribe(actions)
        shakeHandler = ShakeHandler(this)
        shakeHandler.setOnShakeListener { shakeIntent.accept(true) }

    }



    override fun onResume() {
        super.onResume()
        shakeHandler.registerListener()
    }

    override fun onPause() {
        super.onPause()
        shakeHandler.unregisterListener()
    }



    override fun onChanged(state: RandomGiftState) {
        renderLabel(state.targetLabel)
        if (state.animateShake) {
            shakeHandler.unregisterListener()
            animateShake()
        }

    }

    //   internal

    fun initViewModel() {
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RandomGiftViewModel::class.java)
        viewModel.state.observe(this, this)
        viewModel.state.value?.let { onChanged(it) }
        disposables += actions.subscribe(viewModel.input)

        actions.accept(loadGiftAction())
    }

    private fun loadGiftAction(): RandomGiftAction.LoadGiftAction {
        val value = intent.getParcelableExtra<GiftTarget>(GIFT_TARGET_ARG)
        return RandomGiftAction.LoadGiftAction(Optional.toOptional(value))
    }

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
                                     action: () -> Unit = {})
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