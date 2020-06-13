package com.heixss.exchange.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.heixss.exchange.R
import com.heixss.exchange.model.local.ChartData
import com.heixss.exchange.viewmodels.ChartDisplayValue
import com.heixss.exchange.viewmodels.ChartsViewModel
import kotlinx.android.synthetic.main.charts_fragment.*


class ChartsFragment : BaseFragment(R.layout.charts_fragment) {

    private val viewModel: ChartsViewModel by viewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spn_currency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val arrayDisplayValues = resources.getStringArray(R.array.historic_currencies)
                viewModel.chartDisplayValue.onNext(ChartDisplayValue.valueOf(arrayDisplayValues[position]))
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.liveChartData.observe(viewLifecycleOwner, Observer {
            setLineChart(it)
        })
    }

    private fun setLineChart(list: List<ChartData>) {
        val chartEntries = ArrayList<Entry>()
        var i = 0
        list.forEach {
            chartEntries.add(Entry(i++.toFloat(), it.value.toFloat()))
        }
        val lineDataSet = LineDataSet(chartEntries, "chartEntries")
        val lineData = LineData(lineDataSet)
        lc_currencies.data = lineData
        lc_currencies.invalidate()
    }

    override fun onResume() {
        super.onResume()
        observeChartDisplayValue()
    }

    private fun observeChartDisplayValue() {
        disposables.add(viewModel.chartDisplayValue.subscribe {
            val displayValue = (it as ChartDisplayValue)
            val arrayDisplayValues = resources.getStringArray(R.array.historic_currencies)
            spn_currency.setSelection(arrayDisplayValues.indexOf(displayValue.name))
            viewModel.loadChartResponse(displayValue.name)
        })
    }

}
