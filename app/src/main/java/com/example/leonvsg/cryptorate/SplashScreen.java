package com.example.leonvsg.cryptorate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.leonvsg.cryptorate.api.CoinmarketcapApiFactory;
import com.example.leonvsg.cryptorate.api.CoinmarketcapService;
import com.example.leonvsg.cryptorate.db.CurrencyDAO;
import com.example.leonvsg.cryptorate.db.HelperFactory;
import com.example.leonvsg.cryptorate.models.CurrencyModel;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    private CoinmarketcapService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        try {
            TableUtils.clearTable(HelperFactory.getHelper().getConnectionSource(), CurrencyModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        service = CoinmarketcapApiFactory.getRetrofitInstance().create(CoinmarketcapService.class);

        new PrefetchData().execute();
    }

    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            Call<List<CurrencyModel>> call = service.getCurrencyList(10);

            call.enqueue(new Callback<List<CurrencyModel>>() {
                @Override
                public void onResponse(Call<List<CurrencyModel>> call,
                                       Response<List<CurrencyModel>> response) {
                    try {
                        CurrencyDAO currencyDAO = HelperFactory.getHelper().getCurrencyDAO();
                        List<CurrencyModel> currencies = response.body();
                        for (CurrencyModel currency : currencies) {
                            currencyDAO.create(currency);
                        }
                        Intent i = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(i);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<List<CurrencyModel>> call, Throwable t) {
                    Toast.makeText(SplashScreen.this, "Network error", Toast.LENGTH_SHORT).show();
                    Log.d("Error", t.getMessage());
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}