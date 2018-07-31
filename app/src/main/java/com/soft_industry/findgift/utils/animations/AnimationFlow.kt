package com.soft_industry.findgift.utils.animations

import android.view.View
import android.view.animation.Animation
import java.androidx.core.animation.doOnEnd

class AnimationFlow private constructor(val animation: Animation, val view: View, var next: AnimationFlow? = null) {
    companion object {
        fun start(animation: Animation, view: View) = AnimationFlow(animation, view)
    }
    fun then(nextAnimation: Animation, view:View): AnimationFlow {
        val nextFlow = AnimationFlow(nextAnimation, view)
        next = nextFlow
        return nextFlow
    }

    fun start() {
        animation.doOnEnd { next?.start() }
        view.startAnimation(animation)
    }
}