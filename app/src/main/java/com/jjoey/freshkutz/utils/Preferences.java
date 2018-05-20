package com.jjoey.freshkutz.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by JosephJoey on 9/17/2017.
 */

public class Preferences {

    public Context context;
    public SharedPreferences mPrefs;
    public SharedPreferences.Editor editor;

    private static final String PREF_NAME = "fresh_kutz_demo";
    private static final String FIRST_TIME = "is_first_time";

    public Preferences(Context context) {
        this.context = context;
        mPrefs = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
        editor = mPrefs.edit();
    }

    public void setFirstTimeLaunch(boolean firstTime){
        editor.putBoolean(FIRST_TIME, firstTime);
        editor.apply();
    }

    public boolean isFirstTimeLaunch(){
        return mPrefs.getBoolean(FIRST_TIME, true);
    }

}
