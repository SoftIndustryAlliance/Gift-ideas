package java.androidx.core.animation

import android.view.animation.Animation

fun Animation.doOnRepeat(action: (animation: Animation) -> Unit) = addListener(onRepeat = action)
fun Animation.doOnEnd(action: (animation: Animation) -> Unit) = addListener(onEnd = action)
fun Animation.doOnStart(action: (animation: Animation) -> Unit) = addListener(onStart = action)

fun Animation.addListener(
        onRepeat: ((animation: Animation) -> Unit)?= null,
        onEnd: ((animation: Animation) -> Unit)?= null,
        onStart: ((animation: Animation) -> Unit)?= null
):Animation.AnimationListener {
    val listener = object : Animation.AnimationListener{
        override fun onAnimationRepeat(animation: Animation) {
            onRepeat?.invoke(animation)
        }

        override fun onAnimationEnd(animation: Animation) {
            onEnd?.invoke(animation)
        }

        override fun onAnimationStart(animation: Animation) {
            onStart?.invoke(animation)
        }

    }
    return listener
}