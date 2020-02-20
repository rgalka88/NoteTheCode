package com.rgalka88.notethecode.feature.notelist

import android.os.Bundle
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.rgalka88.notethecode.MainActivity
import com.rgalka88.notethecode.R
import com.rgalka88.notethecode.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_note_list.*

class NoteListFragment : BaseFragment() {

    private val viewModel: NoteListViewModel by fragmentViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as? MainActivity)?.supportActionBar?.title = "NOTES"
    }

    override fun invalidate() = withState(viewModel) { state ->
        textView.text = state.text
    }

    override fun layoutId(): Int = R.layout.fragment_note_list
}