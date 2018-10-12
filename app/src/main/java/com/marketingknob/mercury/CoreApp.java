package com.marketingknob.mercury;

import android.app.Application;

/**
 * Created by Akshya on 5/10/2018.
 */


public class CoreApp extends Application {

    private CoreApp coreApp;

    @Override
    public void onCreate() {
        super.onCreate();

        coreApp = this;

    }

}
