package com.soft_industry.findgift.utils

import android.os.Build
import android.transition.*

/**
 * Created by user on 4/12/18.
 */
class Transitions {
    companion object {
        @JvmStatic
        fun createDefaultTransitionSet():Transition = TransitionSet().apply {
            addTransition(Fade())
            addTransition(ChangeBounds())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addTransition(Explode())
                addTransition(ChangeTransform())
                addTransition(ChangeClipBounds())
            }
        }
    }

}
