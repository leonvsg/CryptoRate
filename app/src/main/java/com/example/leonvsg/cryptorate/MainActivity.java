package com.example.leonvsg.cryptorate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.leonvsg.cryptorate.db.HelperFactory;
import com.example.leonvsg.cryptorate.models.CurrencyModel;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView currenciesRecycler;
    private ProgressDialog progressDialog;
    private List<CurrencyModel> currencyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressDialog = ProgressDialog.show(this, "", getString(R.string.loading), true);

        currenciesRecycler = (RecyclerView) findViewById(R.id.currencies_recycler);

        try {
            currencyList = HelperFactory.getHelper().getCurrencyDAO().getAllCurrencies();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        currenciesRecycler.setLayoutManager(layoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        currenciesRecycler.addItemDecoration(itemDecoration);

        CurrencyAdapter adapter = new CurrencyAdapter(currencyList);
        currenciesRecycler.setAdapter(adapter);

        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        try {
            TableUtils.clearTable(HelperFactory.getHelper().getConnectionSource(),
                    CurrencyModel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finishAffinity();
    }

    class CurrenciesViewHolder extends RecyclerView.ViewHolder {

        public ImageView currencyImage;
        public TextView currencyName, currencySymbol;
        public Button btn;

        public CurrenciesViewHolder(View itemView) {
            super(itemView);
            currencyImage = itemView.findViewById(R.id.currency_icon_in_list);
            currencyName = itemView.findViewById(R.id.currency_name_in_list);
            currencySymbol = itemView.findViewById(R.id.currency_symbol_in_list);
            btn = itemView.findViewById(R.id.btn_to_currency_detail);
        }
    }

    class CurrencyAdapter extends RecyclerView.Adapter<CurrenciesViewHolder> {

        private List<CurrencyModel> currencies;

        public CurrencyAdapter(List<CurrencyModel> currencies) {
            this.currencies = currencies;
        }

        @Override
        public CurrenciesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View rowView = layoutInflater.inflate(R.layout.currency_row, parent, false);
            return new CurrenciesViewHolder(rowView);
        }

        @Override
        public void onBindViewHolder(CurrenciesViewHolder holder, final int position) {
            Context context = getApplicationContext();
            String currencySymbol = currencies.get(position).getSymbol().toLowerCase();
            holder.currencyImage.setImageResource(context.getResources()
                    .getIdentifier(currencySymbol, "drawable", context.getPackageName()));
            holder.currencyName.setText(currencies.get(position).getName());
            holder.currencySymbol.setText(currencies.get(position).getSymbol());
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, CurrencyDetail.class);
                    intent.putExtra("currencySymbol", currencies.get(position).getSymbol());
                    intent.putExtra("currencyName", currencies.get(position).getName());
                    intent.putExtra("currencyPrice", currencies.get(position).getPriceUsd());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return currencies.size();
        }
    }
}
