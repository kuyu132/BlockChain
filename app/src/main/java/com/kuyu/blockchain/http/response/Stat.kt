package com.kuyu.blockchain.http.response

/**
 * created by wangguoqun at 2020-09-09
 */
data class Stat(
    var market_price_usd: Double = 0.toDouble(),
    var hash_rate: Double = 0.toDouble(),
    var total_fees_btc: Long = 0L,
    var n_btc_mined: Long = 0L,
    var n_tx: Int = 0,
    var n_blocks_mined: Int = 0,
    var minutes_between_blocks: Double = 0.toDouble(),
    var totalbc: Long = 0L,
    var n_blocks_total: Int = 0,
    var estimated_transaction_volume_usd: Double = 0.toDouble(),
    var blocks_size: Int = 0,
    var miners_revenue_usd: Double = 0.toDouble(),
    var nextretarget: Long = 0L,
    var difficulty: Long = 0L,
    var estimated_btc_sent: Long = 0L,
    var miners_revenue_btc: Int = 0,
    var total_btc_sent: Long = 0L,
    var trade_volume_btc: Double = 0.toDouble(),
    var trade_volume_usd: Double = 0.toDouble(),
    var timestamp: Long = 0L
)
