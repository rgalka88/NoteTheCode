package com.rgalka88.notethecode.feature.drawer

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.bumptech.glide.Glide
import com.rgalka88.notethecode.R
import com.rgalka88.notethecode.core.platform.BaseFragment
import com.rgalka88.notethecode.core.utils.drawerGroupRow
import com.rgalka88.notethecode.core.utils.simpleController
import com.rgalka88.notethecode.feature.drawer.views.AdapterCallbacks
import kotlinx.android.synthetic.main.fragment_drawer.*
import javax.inject.Inject

class DrawerFragment : BaseFragment(), AdapterCallbacks {

    @Inject
    lateinit var drawerViewModelFactory: DrawerViewModel.Factory

    private val viewModel: DrawerViewModel by fragmentViewModel()

    private val epoxyController by lazy { epoxyController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToRowGroupList()
        subscribeToCloudSyncStatus()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cloudIcon.setOnClickListener { viewModel.toggleCloudSync() }
        recyclerView.setControllerAndBuildModels(epoxyController)
    }

    override fun layoutId(): Int = R.layout.fragment_drawer

    override fun invalidate(): Unit = withState(viewModel) { state ->
        state.cloudSyncStatus()?.let { renderCloudStatus(it) }
    }

    override fun onArrowClick(drawerRowId: String) = viewModel.toggleRowExpand(drawerRowId)

    private fun epoxyController() =
        simpleController(viewModel) { state ->
            state.rowGroupList()?.forEach { drawerRowGroupModel ->
                drawerGroupRow(drawerRowGroupModel, this@DrawerFragment) {
                    id(drawerRowGroupModel.drawerRowModel.title)
                }
            }
        }

    private fun subscribeToCloudSyncStatus() =
        viewModel.asyncSubscribe(DrawerState::cloudSyncStatus) { cloudStatus ->
            renderCloudStatus(cloudStatus)
        }

    private fun subscribeToRowGroupList() =
        viewModel.asyncSubscribe(DrawerState::rowGroupList) {
            recyclerView.requestModelBuild()
        }

    private fun renderCloudStatus(cloudStatus: Boolean) {
        if (cloudStatus) Glide.with(requireContext()).load(R.drawable.ic_cloud_done).into(cloudIcon)
        else Glide.with(requireContext()).load(R.drawable.ic_cloud_off).into(cloudIcon)
    }
}