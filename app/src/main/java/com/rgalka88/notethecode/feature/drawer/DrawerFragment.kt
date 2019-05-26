package com.rgalka88.notethecode.feature.drawer

import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.bumptech.glide.Glide
import com.rgalka88.notethecode.R
import com.rgalka88.notethecode.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_drawer.*
import javax.inject.Inject

class DrawerFragment : BaseFragment() {

    @Inject
    lateinit var draweViewModelFactory: DrawerViewModel.Factory

    private val viewModel: DrawerViewModel by fragmentViewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cloudIcon.setOnClickListener { viewModel.toggleCloudSync() }
    }

    override fun layoutId(): Int = R.layout.fragment_drawer

    override fun invalidate(): Unit = withState(viewModel) { state ->
        state.cloudSyncStatus()?.let { renderCloudStatus(it) }
    }

    private fun renderCloudStatus(cloudStatus: Boolean) {
        if (cloudStatus) Glide.with(requireContext()).load(R.drawable.ic_cloud_done).into(cloudIcon)
        else Glide.with(requireContext()).load(R.drawable.ic_cloud_off).into(cloudIcon)
    }
}