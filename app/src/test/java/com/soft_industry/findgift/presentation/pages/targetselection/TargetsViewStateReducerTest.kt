package com.soft_industry.findgift.presentation.pages.targetselection

import com.soft_industry.findgift.domain.entities.GiftTarget
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


object TargetsViewStateReducerTest : Spek({

    given("Initial TargetSelectionState") {
        val items = listOf(GiftTarget(1L, 1,"label"))
        val initialState = TargetSelectionState.initial()
        on("Loading") {
            val loadingVs = TargetsViewStateReducer.Loading.reduce(initialState)
            it("Should have loading = true") {
                assertEquals(true, loadingVs.loading)
            }
        }
        on("Loaded") {
            val loadedVs = TargetsViewStateReducer.Loaded.reduce(initialState)
            it("Should have flag loaded") {
                assertEquals(false, loadedVs.loading)
            }
        }
        on("Editors Loaded") {
            val editorsLoadedVs = TargetsViewStateReducer.EditorsLoaded(items).reduce(initialState)
            it("Should have loaded editors items") {
                assertTrue(editorsLoadedVs.editors.isNotEmpty())
            }
        }
        on("Themed Loaded") {
            val editorsLoadedVs = TargetsViewStateReducer.Themed(items).reduce(initialState)
            it("Should have loaded Themed items") {
                assertTrue(editorsLoadedVs.thematic.isNotEmpty())
            }
        }
        on("ForMen Loaded") {
            val editorsLoadedVs = TargetsViewStateReducer.ForMen(items).reduce(initialState)
            it("Should have loaded ForMen items") {
                assertTrue(editorsLoadedVs.formen.isNotEmpty())
            }
        }
        on("ForWomen Loaded") {
            val editorsLoadedVs = TargetsViewStateReducer.ForWomen(items).reduce(initialState)
            it("Should have loaded ForWomen items") {
                assertTrue(editorsLoadedVs.forwomen.isNotEmpty())
            }
        }
        on("Content loaded") {
            val loaded = TargetsViewStateReducer.Loaded.reduce(initialState)
            it("should have  flag loading = false") {
                assertFalse(loaded.loading)
            }
        }
        on("Dismiss hint") {
            val dismiss = TargetsViewStateReducer.DismissHint.reduce(initialState)
            it("Should have flag showHint = false") {
                assertFalse(dismiss.showHint)
            }
        }
    }
})