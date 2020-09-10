package com.kuyu.blockchain.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kuyu.blockchain.http.response.Chart

/**
 * created by wangguoqun at 2020-09-10
 */
class ChartViewModel : ViewModel() {
    var chart =  MutableLiveData<Chart>()

}