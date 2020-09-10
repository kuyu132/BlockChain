package com.kuyu.blockchain.http;

import com.kuyu.blockchain.http.response.Chart;
import com.kuyu.blockchain.http.response.Pool;
import com.kuyu.blockchain.http.response.Stat;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * created by wangguoqun at 2020-09-09
 */
public interface ApiService {
    @GET("charts/transactions-per-second")
    Observable<Chart> getChartsTransactionsPerSecond(@Query("timespan") String timespan,
                                                     @Query("rollingAverage") String rollingAverage, @Query("start") String start,
                                                     @Query("format") String format, @Query("sampled") String sampled);

    @GET("stats")
    Observable<Stat> getStats();

    @GET("pools?timespan=5days")
    Observable<Pool> getPools(@Query("timespan") String timespan);
}
