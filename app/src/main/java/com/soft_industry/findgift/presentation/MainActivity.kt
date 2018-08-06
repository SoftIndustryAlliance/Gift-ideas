package com.soft_industry.findgift.presentation

import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.transition.Fade
import android.view.View
import android.view.ViewAnimationUtils
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import com.soft_industry.findgift.R
import com.soft_industry.findgift.presentation.pages.map.MapActivity
import com.soft_industry.findgift.presentation.pages.targetselection.TargetSelectionFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val TAG = MapActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
                enterTransition = Explode()
                exitTransition = Fade()
            }
        }
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) {
            supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, TargetSelectionFragment.create())
                commit()
            }
        }

    }


    override fun onResume() {
        super.onResume()
        if (reveal_view.isVisible) {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                val cx = reveal_view.getWidth() / 2
                val cy = reveal_view.getHeight() / 2

                val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()
                val anim = ViewAnimationUtils.createCircularReveal(reveal_view, cx, cy, finalRadius, 0f)
                anim.doOnEnd { reveal_view.visibility = View.GONE }
                anim.start()
            } else {
                reveal_view.visibility = View.GONE
            }
        }
    }
}
