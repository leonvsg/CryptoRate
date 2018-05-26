package com.example.leonvsg.cryptorate.api;

import com.example.leonvsg.cryptorate.models.CurrencyModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by leonvsg on 24.08.17.
 */

public interface CoinmarketcapService {

    @GET("ticker/")
    Call<List<CurrencyModel>> getCurrencyList(@Query("limit") int limit);
}
