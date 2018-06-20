package com.soft_industry.findgift.utils

import android.os.Handler
import android.os.Looper
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

/**
 * Created by user on 3/21/18.
 */

class MainThreadScheduler : Executor {
    val handler = Handler(Looper.getMainLooper())
    override fun execute(command: Runnable) {
        handler.post(command)
    }
    companion object {
        @JvmStatic
        fun get() = Schedulers.from(MainThreadScheduler())
    }
}
