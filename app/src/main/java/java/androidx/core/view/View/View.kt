package java.androidx.core.view.View

import android.view.View
import android.view.ViewTreeObserver

/**
 * Created by user on 4/12/18.
 */
inline fun View.doOnGlobalLayout(
        crossinline action: () ->Unit
) {
    if (viewTreeObserver.isAlive) {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                action()
                viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }
}