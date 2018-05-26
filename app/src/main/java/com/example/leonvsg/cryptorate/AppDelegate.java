package com.example.leonvsg.cryptorate;

import android.app.Application;

import com.example.leonvsg.cryptorate.db.HelperFactory;

/**
 * Created by leonvsg on 24.08.17.
 */

public class AppDelegate extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
    }
    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }

}
