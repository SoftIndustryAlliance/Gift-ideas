package com.soft_industry.findgift.presentation.pages.targetselection

import com.soft_industry.findgift.domain.entities.GiftTarget
import org.junit.Assert
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object TargetsViewStateReducerTest : Spek({
    describe("Initial TargetSelectionState") {
        val items = listOf(GiftTarget(1L, 1,"label"))
        val initialState = TargetSelectionState(
                false,
                mutableListOf(),
                mutableListOf(),
                mutableListOf(),
                mutableListOf(),
                showHint = true)
        context("Loading") {
            val loadingVs = TargetsViewStateReducer.Loading.reduce(initialState)
            it("Should have loading = true") {
                Assert.assertEquals(true, loadingVs.loading)
            }
        }
        context("Loaded") {
            val loadedVs = TargetsViewStateReducer.Loaded.reduce(initialState)
            it("Should have flag loaded") {
                Assert.assertEquals(false, loadedVs.loading)
            }
        }
        context("EditorsLoaded") {

            val editorsLoadedVs = TargetsViewStateReducer.EditorsLoaded(items).reduce(initialState)
            it("Should have loaded editors items") {
                assert(editorsLoadedVs.editors.isNotEmpty())
            }
        }
    }
})