package com.heixss.exchange.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.heixss.exchange.R

class ChartsFragment : BaseFragment(R.layout.charts_fragment) {

    private val viewModel: ChartsViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadHistoricResponse()
    }

}
