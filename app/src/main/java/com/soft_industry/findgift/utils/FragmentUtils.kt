package com.soft_industry.findgift.utils

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by user on 3/28/18.
 */

val DEFAULT_ANIMATION_DURATION = 300L
fun <T: Fragment> T.addDefaultTransitions(): T {
    enterTransition = Transitions.createDefaultTransitionSet().apply {
        startDelay = DEFAULT_ANIMATION_DURATION
        duration = DEFAULT_ANIMATION_DURATION
    }
    exitTransition = Transitions.createDefaultTransitionSet().apply {
        startDelay = 0
        duration = DEFAULT_ANIMATION_DURATION
    }
    returnTransition = Transitions.createDefaultTransitionSet().apply {
        startDelay = 0
        duration = DEFAULT_ANIMATION_DURATION
    }
    return this
}

fun <T: Fragment> T.applyArguments(args: Bundle) = apply { arguments = args }