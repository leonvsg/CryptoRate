package com.example.leonvsg.cryptorate.api;

import com.example.leonvsg.cryptorate.models.CurrencyHistoryModel;
import com.example.leonvsg.cryptorate.models.CurrencyHistoryRequestModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by leonvsg on 24.08.17.
 */

public interface CryptocompareService {

    //https://min-api.cryptocompare.com/data/histoday?fsym=BTC&tsym=USD&limit=7
    @GET("{period}")
    Call<CurrencyHistoryRequestModel> getCurrencyHistory(@Path("period") String period,
                                                         @Query("fsym") String fromCurrency,
                                                         @Query("tsym") String toCurrency,
                                                         @Query("limit") int limit);

}
