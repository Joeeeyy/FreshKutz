package com.jjoey.freshkutz.extras;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.google.android.gms.ads.MobileAds;
import com.jjoey.freshkutz.utils.Constants;
import com.jjoey.freshkutz.utils.Preferences;

/**
 * Created by JosephJoey on 5/17/2018.
 */

public class FreshApp extends Application {

    private static FreshApp app;
    public Preferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
        preferences = new Preferences(this);

        ActiveAndroid.initialize(this);
        MobileAds.initialize(this, Constants.SAMPLE_APP_ID); // TODO: 5/20/2018 Change Later
    }

    public static FreshApp getFreshInstance() {
        return app;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

}
