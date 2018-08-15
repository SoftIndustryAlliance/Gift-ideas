package com.soft_industry.findgift.utils.animations

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import java.androidx.core.animation.doOnEnd

class AnimationFlow private constructor(val view: View, var next: AnimationFlow? = null) {
    private val animations: AnimationSet = AnimationSet(false)
    companion object {
        fun start(animation: Animation, view: View) = AnimationFlow(view).apply { and(animation) }
    }

    fun and(animation: Animation, view: View = this.view): AnimationFlow {

        animations.addAnimation(animation)
        return this
    }
    fun then(nextAnimation: Animation, view:View = this.view): AnimationFlow {
        val nextFlow = AnimationFlow.start(nextAnimation, view)
        next = nextFlow
        return nextFlow
    }

    fun start() {
        animations.doOnEnd { next?.start() }
        view.startAnimation(animations)
    }
}