package com.rgalka88.notethecode.feature.drawer

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.rgalka88.notethecode.R
import com.rgalka88.notethecode.core.platform.BaseFragment
import com.rgalka88.notethecode.core.utils.drawerGroupRow
import com.rgalka88.notethecode.core.utils.simpleController
import com.rgalka88.notethecode.feature.drawer.views.AdapterCallbacks
import kotlinx.android.synthetic.main.fragment_drawer.*

class DrawerFragment : BaseFragment(), AdapterCallbacks {

    private val viewModel: DrawerViewModel by fragmentViewModel()

    private val epoxyController by lazy { epoxyController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToRowGroupList()
        subscribeToCloudSyncStatus()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cloudIcon.setOnClickListener { viewModel.toggleCloudSync() }
        super.onViewCreated(view, savedInstanceState)
        recyclerView.setControllerAndBuildModels(epoxyController)
    }

    override fun layoutId(): Int = R.layout.fragment_drawer

    override fun onArrowClick(drawerRowId: String) = viewModel.toggleRowExpand(drawerRowId)

    override fun onRowClick(drawerRowId: String) = Unit

    private fun epoxyController() =
        simpleController(viewModel) { state ->
            state.rowGroupList()?.forEach { drawerRowGroupModel ->
                drawerGroupRow(drawerRowGroupModel, this@DrawerFragment)
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
        if (cloudStatus) cloudIcon.setImageResource(R.drawable.ic_cloud_done)
        else cloudIcon.setImageResource(R.drawable.ic_cloud_off)
    }
}