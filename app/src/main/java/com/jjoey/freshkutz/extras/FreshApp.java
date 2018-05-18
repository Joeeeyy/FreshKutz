package com.jjoey.freshkutz.extras;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by JosephJoey on 5/17/2018.
 */

public class FreshApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
