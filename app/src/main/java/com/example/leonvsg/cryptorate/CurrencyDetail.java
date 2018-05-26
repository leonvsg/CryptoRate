package com.example.leonvsg.cryptorate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leonvsg.cryptorate.api.CryptocompareApiFactory;
import com.example.leonvsg.cryptorate.api.CryptocompareService;
import com.example.leonvsg.cryptorate.models.CurrencyHistoryModel;
import com.example.leonvsg.cryptorate.models.CurrencyHistoryRequestModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrencyDetail extends AppCompatActivity {

    private String currencySymbol;
    private String currencyName;
    private String currencyPrice;
    private TextView tvPrice;
    private ProgressDialog progressDialog;
    private LineChart chart;
    private CryptocompareService service;
    private Button chartFor7Day;
    private Button chartFor30Day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_detail);

        progressDialog = ProgressDialog.show(this, "", getString(R.string.loading), true);
        tvPrice = (TextView) findViewById(R.id.course);
        chart = (LineChart) findViewById(R.id.chart);
        chartFor7Day = (Button) findViewById(R.id.btn_fo_7_day);
        chartFor30Day = (Button) findViewById(R.id.btn_for_30_day);

        chartFor7Day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chartCreate("USD", 7);
            }
        });

        chartFor30Day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chartCreate("USD", 30);
            }
        });

        Intent intent = getIntent();
        currencySymbol = intent.getStringExtra("currencySymbol");
        currencyName = intent.getStringExtra("currencyName");
        currencyPrice = intent.getStringExtra("currencyPrice");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_24);
        toolbar.setTitle(currencyName + " (" + currencySymbol + ")");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvPrice.setText(currencyPrice);

        service = CryptocompareApiFactory.getRetrofitInstance().create(CryptocompareService.class);

        chartCreate("USD", 7);

        progressDialog.dismiss();
    }

    private void chartCreate(String toCur, final int dayCount){
        progressDialog.show();
        Call<CurrencyHistoryRequestModel> call =
                service.getCurrencyHistory("histoday", currencySymbol, toCur, dayCount);
        call.enqueue(new Callback<CurrencyHistoryRequestModel>() {
            @Override
            public void onResponse(Call<CurrencyHistoryRequestModel> call,
                                   Response<CurrencyHistoryRequestModel> response) {
                if (!response.body().getResponse().equals("Error")) {
                    List<CurrencyHistoryModel> history = response.body().getCurrencyHistories();
                    ArrayList<Entry> entries = new ArrayList<>();
                    final ArrayList<String> labels = new ArrayList<>();
                    int t = 0;
                    for (CurrencyHistoryModel data : history) {
                        entries.add(new BarEntry((float) t, data.getRate()));
                        t++;
                        String result = new java.text.SimpleDateFormat("dd-MMM")
                                .format(new Date(data.getTime() * 1000));
                        labels.add(result);
                    }
                    //TODO add labels

                    IAxisValueFormatter formatter = new IAxisValueFormatter() {

                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return labels.get((int) value);
                        }
                    };

                    XAxis xAxis = chart.getXAxis();
                    xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                    xAxis.setValueFormatter(formatter);

                    LineDataSet dataSet = new LineDataSet(entries, "USD за 1 " + currencyName);
                    dataSet.setDrawFilled(true);
                    LineData data = new LineData(dataSet);
                    chart.setData(data);
                    Description desc = new Description();
                    desc.setText("Курс за "+dayCount+" дней");
                    chart.setDescription(desc);
                    chart.invalidate();
                }
                else {
                    Toast.makeText(CurrencyDetail.this, "Для данной валюты не доступны исторические данные", Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CurrencyHistoryRequestModel> call, Throwable t) {
                Toast.makeText(CurrencyDetail.this, "Network error", Toast.LENGTH_SHORT).show();
                Log.d("Error", t.getMessage());

                progressDialog.dismiss();
            }
        });
    }

}
