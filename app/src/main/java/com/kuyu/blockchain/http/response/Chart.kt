package com.kuyu.blockchain.http.response

import androidx.lifecycle.ViewModel

/**
 * created by wangguoqun at 2020-09-09
 */
data class Chart(
    var status: String = "",
    var name: String = "",
    var unit: String = "",
    var period: String = "",
    var description: String = "",
    var values: List<Values> = listOf()
)

data class Values(
    var x: Long = 0,
    var y: Double = 0.toDouble()
)
