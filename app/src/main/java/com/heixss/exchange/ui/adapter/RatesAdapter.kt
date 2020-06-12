package com.heixss.exchange.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.heixss.exchange.R
import com.heixss.exchange.model.local.Rate
import com.heixss.exchange.ui.adapter.diffutil.RatesDiffUtil

class RatesAdapter(private val rates: ArrayList<Rate>) : RecyclerView.Adapter<RateViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        return RateViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rate_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return rates.size
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.setup(rates[position], position % 2 == 0)
    }

    fun updateList(newRates: List<Rate>) {
        val diffResult = DiffUtil.calculateDiff(RatesDiffUtil(this.rates, newRates))
        this.rates.clear()
        this.rates.addAll(newRates)
        diffResult.dispatchUpdatesTo(this)
    }
}

class RateViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val tvCurrency = view.findViewById<TextView>(R.id.tv_currency)
    private val tvValue = view.findViewById<TextView>(R.id.tv_value)

    fun setup(rate: Rate, darkColor: Boolean) {
        tvCurrency.text = rate.currency
        tvValue.text = rate.value
        if (darkColor)
            view.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
        else
            view.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorPrimary))
    }

}