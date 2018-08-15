package java.androidx.core.animation

import android.view.animation.Animation

inline fun Animation.doOnRepeat(crossinline action: (animation: Animation) -> Unit) = addListener(onRepeat = action)
inline fun Animation.doOnEnd(crossinline action: (animation: Animation) -> Unit) = addListener(onEnd = action)
inline fun Animation.doOnStart(crossinline action: (animation: Animation) -> Unit) = addListener(onStart = action)

inline fun Animation.addListener(
        crossinline onRepeat: ((animation: Animation) -> Unit) = {},
        crossinline onEnd: ((animation: Animation) -> Unit) = {},
        crossinline onStart: ((animation: Animation) -> Unit) = {}
):Animation.AnimationListener {
    val listener = object : Animation.AnimationListener{
        override fun onAnimationRepeat(animation: Animation) {
            onRepeat(animation)
        }

        override fun onAnimationEnd(animation: Animation) {
            onEnd(animation)
        }

        override fun onAnimationStart(animation: Animation) {
            onStart(animation)
        }

    }
    return listener
}