package com.soft_industry.findgift.presentation.pages.splash

import android.content.Intent
import android.os.Handler
import android.support.v4.app.ActivityOptionsCompat
import com.soft_industry.findgift.presentation.MainActivity
import javax.inject.Inject

class SplashFlowCoordinator @Inject constructor (private val activity: SplashActivity) {
    private val handler = Handler()

    fun openMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        val toBundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity).toBundle()
        handler.post { activity.startActivity(intent, toBundle) }
        handler.post { activity.finish() }

    }
}