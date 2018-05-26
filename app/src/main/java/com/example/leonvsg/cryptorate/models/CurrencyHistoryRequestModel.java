package com.example.leonvsg.cryptorate.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by leonvsg on 25.08.17.
 */

/*
{
    "Response": "Success",
    "Type": 100,
    "Aggregated": false,
    "Data": [ {CurrencyHistoryModel} ],
    "TimeTo": 1503619200,
    "TimeFrom": 1503014400,
    "FirstValueInArray": true,
    "ConversionType": {
        "type": "direct",
        "conversionSymbol": ""
    }
}
*/
public class CurrencyHistoryRequestModel {

    @SerializedName("Response")
    private String response;

    @SerializedName("Data")
    private List<CurrencyHistoryModel> currencyHistories;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<CurrencyHistoryModel> getCurrencyHistories() {
        return currencyHistories;
    }

    public void setCurrencyHistories(List<CurrencyHistoryModel> currencyHistories) {
        this.currencyHistories = currencyHistories;
    }
}
