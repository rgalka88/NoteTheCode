package com.rgalka88.notethecode.feature.drawer

import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.rgalka88.notethecode.core.UseCase.None
import com.rgalka88.notethecode.core.platform.BaseViewModel
import com.rgalka88.notethecode.data.hashtag.HashTagModel
import com.rgalka88.notethecode.feature.drawer.usecase.LoadCloudSyncStatus
import com.rgalka88.notethecode.feature.drawer.usecase.LoadHashTags
import com.rgalka88.notethecode.feature.drawer.usecase.ToggleCloudSync
import com.rgalka88.notethecode.feature.drawer.usecase.ToggleHashTag
import com.rgalka88.notethecode.models.DrawerChildrenModel
import com.rgalka88.notethecode.models.DrawerRowGroupModel
import com.rgalka88.notethecode.models.DrawerRowModel
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DrawerViewModel @AssistedInject constructor(
    @Assisted initialState: DrawerState,
    private val loadCloudSyncStatus: LoadCloudSyncStatus,
    private val loadHashTags: LoadHashTags,
    private val toggleCloudSync: ToggleCloudSync,
    private val toggleHashTag: ToggleHashTag
) : BaseViewModel<DrawerState>(initialState) {

    init {
        observeTags()
        observeCloudSyncStatus()
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: DrawerState): DrawerViewModel
    }

    fun toggleCloudSync() {
        toggleCloudSync(None())
            .execute { this }
    }

    fun toggleRowExpand(hashTagId: String) {
        toggleHashTag(hashTagId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .execute { this }
    }

    private fun observeTags() {
        loadHashTags(None())
            .subscribeOn(Schedulers.io())
            .map { hashTagModelList ->
                createDrawerRowGroupModels("", hashTagModelList.groupBy { it.parent })
            }
            .observeOn(AndroidSchedulers.mainThread())
            .execute { copy(rowGroupList = it) }
    }

    private fun observeCloudSyncStatus() {
        loadCloudSyncStatus(None())
            .execute { copy(cloudSyncStatus = it) }
    }

    private fun createDrawerRowGroupModels(
        parent: String,
        hashTagModelsByParent: Map<String, List<HashTagModel>>
    ): List<DrawerRowGroupModel> =
        hashTagModelsByParent[parent]?.map { hashTagModel ->
            DrawerRowGroupModel(
                drawerRowModel = DrawerRowModel(
                    title = hashTagModel.title,
                    childrenVisible = hashTagModel.expanded,
                    animateArrow = hashTagModel.animateArrow
                ),
                drawerChildrenModel = DrawerChildrenModel(
                    createDrawerRowGroupModels(
                        hashTagModel.title,
                        hashTagModelsByParent.filterKeys { it != parent }
                    )
                ),
                childrenVisible = hashTagModel.expanded
            )
        } ?: emptyList()

    companion object : MvRxViewModelFactory<DrawerViewModel, DrawerState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: DrawerState
        ): DrawerViewModel? {
            val fragment: DrawerFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.drawerViewModelFactory.create(state)
        }
    }
}