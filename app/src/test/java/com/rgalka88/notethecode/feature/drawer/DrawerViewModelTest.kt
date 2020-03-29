package com.rgalka88.notethecode.feature.drawer

import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.test.MvRxTestRule
import com.airbnb.mvrx.withState
import com.rgalka88.notethecode.UnitTestTransformer
import com.rgalka88.notethecode.core.rx.Transformer
import com.rgalka88.notethecode.feature.drawer.model.DrawerChildrenModel
import com.rgalka88.notethecode.feature.drawer.model.DrawerRowGroupModel
import com.rgalka88.notethecode.feature.drawer.model.DrawerRowModel
import com.rgalka88.notethecode.feature.drawer.usecase.LoadCloudSyncStatus
import com.rgalka88.notethecode.feature.drawer.usecase.LoadDrawerItems
import com.rgalka88.notethecode.feature.drawer.usecase.ToggleCloudSync
import com.rgalka88.notethecode.feature.drawer.usecase.ToggleDrawerItem
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DrawerViewModelTest {

    @get:Rule
    val mvRxRule = MvRxTestRule()

    private val loadCloudSyncStatus: LoadCloudSyncStatus = mockk()
    private val toggleCloudSync: ToggleCloudSync = mockk()
    private val loadDrawerItems: LoadDrawerItems = mockk()
    private val toggleDrawerItem: ToggleDrawerItem = mockk()
    private val rxTransformer: Transformer = UnitTestTransformer()

    @Before
    fun setup() {
        every { loadCloudSyncStatus(any()) } returns Observable.never()
        every { toggleCloudSync(any()) } returns Completable.never()
        every { loadDrawerItems(any()) } returns Observable.never()
        every { toggleDrawerItem(any()) } returns Completable.never()
    }

    @Test
    fun `init observe drawer items`() {
        val viewModel = createViewModel()

        withState(viewModel) { state ->
            assertEquals(Loading<Any>(), state.rowGroupList)
        }
    }

    @Test
    fun `init observe cloud sync status`() {
        val viewModel = createViewModel()

        withState(viewModel) { state ->
            assertEquals(Loading<Any>(), state.cloudSyncStatus)
        }
    }

    @Test
    fun `toggleCloudSync calls toggleCloudSyncUseCase and do not change state`() {
        val viewModel = createViewModel()

        withState(viewModel) { state ->
            viewModel.toggleCloudSync()

            verify { toggleCloudSync(any()) }

            withState(viewModel) { newState ->
                assertEquals(state, newState)
            }
        }
    }

    @Test
    fun `toggleRowExpand calls toggleDrawerItem and do not change state`() {
        val viewModel = createViewModel()
        val itemId = "itemId"
        withState(viewModel) { state ->
            viewModel.toggleRowExpand(itemId)

            verify { toggleDrawerItem(itemId) }

            withState(viewModel) { newState ->
                assertEquals(state, newState)
            }
        }
    }

    @Test
    fun `observeDrawerItems loads rowGroupList`() {
        val drawerRowGroups = getDrawerRowGroups()
        every { loadDrawerItems(any()) } returns Observable.just(drawerRowGroups)
        val viewModel = createViewModel()

        withState(viewModel) { state ->
            assertEquals(Success(drawerRowGroups), state.rowGroupList)
        }
    }

    @Test
    fun `observeCloudSyncStatus loads cloudSyncStatus`() {
        every { loadCloudSyncStatus(any()) } returns Observable.just(true)
        val viewModel = createViewModel()

        withState(viewModel) { state ->
            assertEquals(Success(true), state.cloudSyncStatus)
        }
    }

    private fun createViewModel(initialState: DrawerState = DrawerState()) =
        DrawerViewModel(
            initialState = initialState,
            loadCloudSyncStatus = loadCloudSyncStatus,
            toggleCloudSync = toggleCloudSync,
            loadDrawerItems = loadDrawerItems,
            toggleDrawerItem = toggleDrawerItem,
            rxTransformer = rxTransformer
        )

    private fun getDrawerRowGroups() = listOf(
        DrawerRowGroupModel(
            DrawerRowModel(
                drawableRes = 123,
                title = "HashTag1",
                hasChildren = true,
                childrenVisible = true,
                animateArrow = true
            ), DrawerChildrenModel(
                listOf(
                    DrawerRowGroupModel(
                        DrawerRowModel(title = "HashTagA", hasChildren = true),
                        DrawerChildrenModel(
                            listOf(
                                DrawerRowGroupModel(
                                    DrawerRowModel(title = "HashTagA1"),
                                    DrawerChildrenModel(emptyList())
                                )
                            )
                        )
                    ),
                    DrawerRowGroupModel(
                        DrawerRowModel(title = "HashTagB"),
                        DrawerChildrenModel(emptyList())
                    )
                )
            )
        ),
        DrawerRowGroupModel(
            DrawerRowModel(
                drawableRes = 124,
                title = "HashTag2",
                hasChildren = false,
                childrenVisible = false,
                animateArrow = true
            ),
            DrawerChildrenModel(emptyList())
        )
    )
}