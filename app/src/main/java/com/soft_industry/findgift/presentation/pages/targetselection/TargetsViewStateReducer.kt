package com.soft_industry.findgift.presentation.pages.targetselection

import com.soft_industry.findgift.domain.entities.GiftTarget
import com.soft_industry.findgift.presentation.StateReducer

/**
 * Created by user on 3/26/18.
 */
sealed class TargetsViewStateReducer : StateReducer<TargetSelectionViewState> {
    class Loading: TargetsViewStateReducer() {
        override fun reduce(old: TargetSelectionViewState) = old.copy(loading = true)
//                old.apply { TargetSelectionViewState(true, editors, thematic, forwomen, formen) }
    }

    class Loaded: TargetsViewStateReducer() {
        override fun reduce(oldVS: TargetSelectionViewState) = oldVS.copy(loading = false)
    }

    class EditorsLoaded(val data: List<GiftTarget>): TargetsViewStateReducer() {
        override fun reduce(old: TargetSelectionViewState) = old.copy(editors = data)
//                TargetSelectionViewState(false, data, old.thematic, old.forwomen, old.formen)
    }

    class Themed(val data: List<GiftTarget>): TargetsViewStateReducer() {
        override fun reduce(old: TargetSelectionViewState) = old.copy(thematic = data)
//                TargetSelectionViewState(false, old.editors, data, old.forwomen, old.formen)
    }

    class ForWomen(val data: List<GiftTarget>): TargetsViewStateReducer() {
        override fun reduce(old: TargetSelectionViewState) = old.copy(forwomen = data)
//                TargetSelectionViewState(false, old.editors, old.thematic, data, old.formen)
    }
    class ForMen(val data: List<GiftTarget>): TargetsViewStateReducer() {
        override fun reduce(old: TargetSelectionViewState) = old.copy(formen = data)
//                TargetSelectionViewState(false,  old.editors, old.thematic, old.forwomen, data)
    }

    class DismissHint:TargetsViewStateReducer() {
        override fun reduce(oldVs: TargetSelectionViewState) = oldVs.copy(showHint = false)
    }

    class Error(val error: Throwable) : TargetsViewStateReducer() {
        override fun reduce(old: TargetSelectionViewState) = old.copy(error = error)
//                old.apply {
//                    TargetSelectionViewState(false,
//                            editors,
//                            thematic,
//                            forwomen,
//                            formen, error)
//                }
    }

}
