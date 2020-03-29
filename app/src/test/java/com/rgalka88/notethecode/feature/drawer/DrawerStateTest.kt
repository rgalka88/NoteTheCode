package com.rgalka88.notethecode.feature.drawer

import com.airbnb.mvrx.Uninitialized
import org.junit.Assert.assertEquals
import org.junit.Test

class DrawerStateTest {

    @Test
    fun `state has expected default values`() {
        val state = DrawerState()
        assertEquals(Uninitialized, state.cloudSyncStatus)
        assertEquals(Uninitialized, state.rowGroupList)
    }
}