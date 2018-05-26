package com.example.leonvsg.cryptorate.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by leonvsg on 25.08.17.
 */

/*
    {
            "time": 1503014400,
            "close": 4105.37,
            "high": 4362.73,
            "low": 3978.28,
            "open": 4278.92,
            "volumefrom": 144398.64,
            "volumeto": 603898315.45
        }
*/

public class CurrencyHistoryModel {

    private long time;
    @SerializedName("close")
    private float rate;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
