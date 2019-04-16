package com.goodcompany.group5_finalproject_w2019_mad3125.Config;

import android.app.Application;

import com.google.firebase.FirebaseApp;

/**
 * Created by iapp on 27/6/18.
 */

public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static App getInstance() {
        return instance;
    }

}
