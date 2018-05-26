package com.example.leonvsg.cryptorate.models;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by leonvsg on 24.08.17.
 */

/*
data model
    {
        "id": "bitcoin",
        "name": "Bitcoin",
        "symbol": "BTC",
        "rank": "1",
        "price_usd": "4219.36",
        "price_btc": "1.0",
        "24h_volume_usd": "2099970000.0",
        "market_cap_usd": "69719227864.0",
        "available_supply": "16523650.0",
        "total_supply": "16523650.0",
        "percent_change_1h": "-0.05",
        "percent_change_24h": "0.88",
        "percent_change_7d": "-3.16",
        "last_updated": "1503599377"
    }
 */

@DatabaseTable(tableName = "currencies")
public class CurrencyModel {

    @DatabaseField()
    private String name;

    @DatabaseField()
    private String symbol;

    @DatabaseField()
    @SerializedName("price_usd")
    private String priceUsd;

    public CurrencyModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(String priceUsd) {
        this.priceUsd = priceUsd;
    }
}
