package com.soft_industry.findgift.utils

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by user on 4/13/18.
 */
operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}