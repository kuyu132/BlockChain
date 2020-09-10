package com.kuyu.blockchain.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.kuyu.blockchain.R
import com.kuyu.blockchain.http.ApiManager
import com.kuyu.blockchain.http.response.Chart
import com.kuyu.blockchain.http.response.Values
import com.kuyu.blockchain.rxutils.AutoDisposable
import com.kuyu.blockchain.rxutils.add
import com.kuyu.blockchain.ui.viewmodel.ChartViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ComponentActivity() {
    lateinit var chartViewModel: ChartViewModel
    var autoDisposable = AutoDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        autoDisposable.bindTo(this.lifecycle)
        initView()
        initData()
    }

    private fun initView() {
        line_chart.also {
            it.setTouchEnabled(true)
            it.setPinchZoom(true)
            it.isScaleYEnabled = false
            it.description.isEnabled = false
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initData() {
        //use viewmodel
        chartViewModel = ViewModelProvider(this).get(ChartViewModel::class.java)
        ApiManager.apiService
            .getChartsTransactionsPerSecond("5weeks", "8hours", "", "json", "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ chart ->
                chartViewModel.chart.value = chart
            }, { t ->
                Toast.makeText(this@MainActivity, "get market price failed: ${t.message ?: ""}",
                    Toast.LENGTH_SHORT).show()
            }).add(autoDisposable)
        chartViewModel.chart.observe(this, Observer<Chart> { chart ->
            chart.values?.let { refreshChat(it) }
        })

        //don't use viewmodel
        ApiManager.apiService.stats.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ stat ->
                tv_rate.text = "${stat.market_price_usd}usd"
            }, { t ->
                t.message?.let {
                    Toast.makeText(this@MainActivity, "get exchange rate data failed: ${t.message ?: ""}",
                        Toast.LENGTH_SHORT).show()
                }
            }).add(autoDisposable)
    }

    private fun refreshChat(values: List<Values>) {
        val poitList = ArrayList<Entry>()

        for (value in values) {
            poitList.add(Entry(value.x.toFloat(), value.y.toFloat()))
        }

        val dataSet = LineDataSet(poitList, "data")
        dataSet.axisDependency = YAxis.AxisDependency.LEFT
        dataSet.highLightColor = Color.RED
        dataSet.setDrawValues(true)
        dataSet.valueTextSize = 12f

        val data = LineData(dataSet)
        line_chart.data = data
        line_chart.invalidate()
    }

}
