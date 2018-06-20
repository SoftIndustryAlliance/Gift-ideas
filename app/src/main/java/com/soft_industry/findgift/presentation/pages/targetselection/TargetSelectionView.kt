package com.soft_industry.findgift.presentation.pages.targetselection

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable


interface TargetSelectionView : MvpView {
    fun loadTargetListIntent(): Observable<Boolean>
    fun render(state: TargetSelectionViewState)
}


