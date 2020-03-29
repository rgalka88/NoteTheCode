package com.rgalka88.notethecode.feature.drawer

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rgalka88.notethecode.BaseFragmentTest
import com.rgalka88.notethecode.R
import com.rgalka88.notethecode.di.module.DrawerTestModule
import com.rgalka88.notethecode.feature.drawer.model.DrawerChildrenModel
import com.rgalka88.notethecode.feature.drawer.model.DrawerRowGroupModel
import com.rgalka88.notethecode.feature.drawer.model.DrawerRowModel
import com.rgalka88.notethecode.feature.drawer.usecase.LoadCloudSyncStatus
import com.rgalka88.notethecode.feature.drawer.usecase.LoadDrawerItems
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DrawerFragmentTest : BaseFragmentTest() {

    private val loadCloudSyncStatus: LoadCloudSyncStatus = mockk()
    private val loadDrawerItems: LoadDrawerItems = mockk()

    private lateinit var scenario: FragmentScenario<DrawerFragment>

    init {
        buildTestAppComponent {
            drawerTestModule(
                DrawerTestModule(
                    loadCloudSyncStatus,
                    loadDrawerItems
                )
            )
        }
    }

    @Before
    fun setup() {
        every { loadCloudSyncStatus(any()) } returns Observable.never()
        every { loadDrawerItems(any()) } returns Observable.never()
    }

    @Test
    fun layout_id_is_equals_fragment_drawer() {
        launchFragment()
        DrawerFragmentScreen {
            assertEquals(R.layout.fragment_drawer, layoutId)
        }
    }

    @Test
    fun appNameTextView_contains_expected_string() {
        launchFragment()
        DrawerFragmentScreen {
            appNameTextView {
                isVisible()
                hasText("NoteTheCode")
            }
        }
    }

    @Test
    fun cloudIcon_contains_expected_drawable_when_sync_status_is_false() {
        every { loadCloudSyncStatus(any()) } returns Observable.just(false)
        launchFragment()
        DrawerFragmentScreen {
            cloudIcon {
                isVisible()
                hasDrawableWithTint(R.drawable.ic_cloud_off, R.color.iconColor)
            }
        }
    }

    @Test
    fun cloudIcon_contains_expected_drawable_when_sync_status_is_true() {
        every { loadCloudSyncStatus(any()) } returns Observable.just(true)
        launchFragment()
        DrawerFragmentScreen {
            cloudIcon {
                isVisible()
                hasDrawableWithTint(R.drawable.ic_cloud_done, R.color.iconColor)
            }
        }
    }

    @Test
    fun settingsButton_contains_expected_drawable() {
        launchFragment()
        DrawerFragmentScreen {
            settingsButton {
                isVisible()
                hasDrawableWithTint(R.drawable.ic_settings, R.color.iconColor)
            }
        }
    }

    @Test
    fun recyclerView_contains_expected_items() {
        every { loadDrawerItems(any()) } returns Observable.just(getTestDrawerItems())
        launchFragment()
        DrawerFragmentScreen {
            recyclerView {
                firstChild<DrawerFragmentScreen.Item> {
                    isVisible()

                    title(0)() { hasText("Notes") }
                    rowIcon(0)() { hasDrawableWithTint(R.drawable.ic_note, R.color.iconColor) }
                    arrowIcon(0)() { hasDrawableWithTint(R.drawable.ic_arrow, R.color.iconColor) }

                    title(1)() { hasText("Child1") }
                    rowIcon(1)() { hasDrawableWithTint(R.drawable.ic_hashtag, R.color.iconColor) }
                    arrowIcon(1)() { isGone() }

                    title(2)() { hasText("Child2") }
                    rowIcon(2)() { hasDrawableWithTint(R.drawable.ic_hashtag, R.color.iconColor) }
                    arrowIcon(2)() { isGone() }

                    title(3)() { hasText("Child3") }
                    rowIcon(3)() { hasDrawableWithTint(R.drawable.ic_hashtag, R.color.iconColor) }
                    arrowIcon(3)() { isGone() }

                }

                childAt<DrawerFragmentScreen.Item>(1) {
                    isVisible()

                    title(0)() { hasText("Trash") }
                    rowIcon(0)() { hasDrawableWithTint(R.drawable.ic_trash, R.color.iconColor) }
                    arrowIcon(0)() { hasDrawableWithTint(R.drawable.ic_arrow, R.color.iconColor) }
                }
            }
        }
    }

    private fun launchFragment() {
        scenario = launchFragmentInContainer(themeResId = R.style.AppTheme)
    }

    private fun getTestDrawerItems() =
        listOf(
            DrawerRowGroupModel(
                drawerRowModel = DrawerRowModel(
                    drawableRes = R.drawable.ic_note,
                    title = "Notes",
                    hasChildren = true,
                    animateArrow = false,
                    childrenVisible = true
                ),
                drawerChildrenModel = DrawerChildrenModel(
                    listOf(
                        DrawerRowGroupModel(
                            drawerRowModel = DrawerRowModel(
                                drawableRes = R.drawable.ic_hashtag,
                                title = "Child1",
                                hasChildren = false,
                                animateArrow = false,
                                childrenVisible = false
                            ),
                            drawerChildrenModel = DrawerChildrenModel(emptyList())
                        ),
                        DrawerRowGroupModel(
                            drawerRowModel = DrawerRowModel(
                                drawableRes = R.drawable.ic_hashtag,
                                title = "Child2",
                                hasChildren = false,
                                animateArrow = false,
                                childrenVisible = false
                            ),
                            drawerChildrenModel = DrawerChildrenModel(emptyList())
                        ),
                        DrawerRowGroupModel(
                            drawerRowModel = DrawerRowModel(
                                drawableRes = R.drawable.ic_hashtag,
                                title = "Child3",
                                hasChildren = false,
                                animateArrow = false,
                                childrenVisible = false
                            ),
                            drawerChildrenModel = DrawerChildrenModel(emptyList())
                        )
                    )
                )
            ),
            DrawerRowGroupModel(
                drawerRowModel = DrawerRowModel(
                    drawableRes = R.drawable.ic_trash,
                    title = "Trash",
                    hasChildren = true,
                    animateArrow = false,
                    childrenVisible = false
                ),
                drawerChildrenModel = DrawerChildrenModel(
                    listOf(
                        DrawerRowGroupModel(
                            drawerRowModel = DrawerRowModel(
                                drawableRes = R.drawable.ic_hashtag,
                                title = "Child4",
                                hasChildren = false,
                                animateArrow = false,
                                childrenVisible = false
                            ),
                            drawerChildrenModel = DrawerChildrenModel(emptyList())
                        )
                    )
                )
            )

        )
}