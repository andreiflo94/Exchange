package com.heixss.exchange.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.heixss.exchange.R
import com.heixss.exchange.ui.adapter.RatesAdapter
import com.heixss.exchange.viewmodels.HomeViewModel
import kotlinx.android.synthetic.main.home_screen_fragment.*

class HomeFragment : BaseFragment(R.layout.home_screen_fragment) {

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }
    private lateinit var ratesAdapter: RatesAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.liveRates().observe(viewLifecycleOwner, Observer {
            ratesAdapter.updateList(it)
            tv_last_updated.text =
                String.format(getString(R.string.last_updated), System.currentTimeMillis())
            tv_base_currency.text =
                String.format(getString(R.string.base_currency), viewModel.getBaseCurrency())
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configUi()
    }

    override fun onResume() {
        super.onResume()
        observeRefreshSubject()
        viewModel.loadRates()
    }

    override fun onPause() {
        viewModel.stopLoading()
        super.onPause()
    }

    private fun configUi() {
        ratesAdapter = RatesAdapter(arrayListOf())
        rv_rates.adapter = ratesAdapter
        ib_settings.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
        }
        ib_charts.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChartsFragment())
        }
    }

    private fun observeRefreshSubject() {
        disposables.add(viewModel.refreshSubject.subscribe {
            //todo show a loading something
        })
    }
}
