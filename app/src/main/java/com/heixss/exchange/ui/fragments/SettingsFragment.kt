package com.heixss.exchange.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.heixss.exchange.R
import com.heixss.exchange.viewmodels.SettingsViewModel
import kotlinx.android.synthetic.main.settings_fragment.*

class SettingsFragment : BaseFragment(R.layout.settings_fragment) {

    private val viewModel: SettingsViewModel by viewModels { viewModelFactory }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.liveCurrencies().observe(viewLifecycleOwner, Observer { currencies ->
            configureCurrenciesSpinner(currencies)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRefreshRateSpinner()
    }

    private fun configureRefreshRateSpinner() {
        val refreshRateArray = requireActivity().resources.getStringArray(R.array.refresh_rate)
        spn_refresh_rate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setHomeRefreshTime(refreshRateArray[position].toInt())
            }
        }
        spn_refresh_rate.setSelection(
            refreshRateArray.indexOf(
                viewModel.getHomeRefreshTime().toString()
            )
        )
    }

    private fun configureCurrenciesSpinner(currencies: List<String>) {
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            currencies
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_base_currency.adapter = adapter
        spn_base_currency.setSelection(currencies.indexOf(viewModel.getBaseCurrency()))
        spn_base_currency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setBaseCurrency(currencies[position])
            }
        }
    }
}
