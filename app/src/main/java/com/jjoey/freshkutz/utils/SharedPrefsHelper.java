package com.jjoey.freshkutz.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by JosephJoey on 5/10/2018.
 */

public class SharedPrefsHelper {

    private Context context;
    private SharedPreferences appPreferences, imagePrefs;
    private SharedPreferences.Editor editor, imageEditor;

    public static final String KEY_APP_PREFS = "freshKutz";
    public static final String KEY_IMAGE_PREFS = "images";

    public static final String COVER_IMAGE = "cover_image";
    public static final String FRONT_IMAGE = "front_image";
    public static final String BACK_IMAGE = "back_image";
    public static final String SIDE_IMAGE = "side_image";

    public static final String SALON_NAME = "salon_city_name";
    public static final String STYLE_TITLE = "style_title";
    public static final String STYLE_DESCRIPTION = "style_description";
    public static final String DATE_CUT = "date_hair_cut";

    public SharedPrefsHelper() {
    }

    public SharedPrefsHelper(Context context) {
        this.context = context;

        appPreferences = context.getSharedPreferences(KEY_APP_PREFS, Context.MODE_PRIVATE);
        editor = appPreferences.edit();

        imagePrefs = context.getSharedPreferences(KEY_IMAGE_PREFS, Context.MODE_PRIVATE);
        imageEditor = imagePrefs.edit();
    }

    public void saveCoverImage(String coverImg){
        imageEditor.putString(COVER_IMAGE, coverImg);
        imageEditor.commit();
    }

    public String getCoverImage(){
        return imagePrefs.getString(COVER_IMAGE, null);
    }

    public void deleteCoverImage(){
        imageEditor.remove(COVER_IMAGE);
        imageEditor.apply();
    }

    public void saveFrontImage(String frontImg){
        imageEditor.putString(FRONT_IMAGE, frontImg);
        imageEditor.commit();
    }

    public String getFrontImage(){
        return imagePrefs.getString(FRONT_IMAGE, null);
    }

    public void deleteFrontImage(){
        imageEditor.remove(FRONT_IMAGE);
        imageEditor.apply();
    }

    public void saveBackImage(String backImg){
        imageEditor.putString(BACK_IMAGE, backImg);
        imageEditor.commit();
    }

    public String getBackImage(){
        return imagePrefs.getString(BACK_IMAGE, null);
    }

    public void deleteBackImage(){
        imageEditor.remove(BACK_IMAGE);
        imageEditor.apply();
    }

    public void saveSideImage(String sideImg){
        imageEditor.putString(SIDE_IMAGE, sideImg);
        imageEditor.commit();
    }

    public String getSideImage(){
        return imagePrefs.getString(SIDE_IMAGE, null);
    }

    public void deleteSideImage(){
        imageEditor.remove(SIDE_IMAGE);
        imageEditor.apply();
    }

    public void saveTitle(String title) {
        editor.putString(STYLE_TITLE, title);
        editor.commit();
    }

    public String getStyleTitle(){
        return appPreferences.getString(STYLE_TITLE, null);
    }

    public void deleteStyleTitle(){
        editor.remove(STYLE_TITLE);
        editor.apply();
    }

    public void saveDescription(String desc) {
        editor.putString(STYLE_DESCRIPTION, desc);
        editor.commit();
    }

    public String getStyleDescription(){
        return appPreferences.getString(STYLE_DESCRIPTION, null);
    }

    public void deleteDesc(){
        editor.remove(STYLE_DESCRIPTION);
        editor.apply();
    }

    public void saveDateCut(String date){
        editor.putString(DATE_CUT, date);
        editor.commit();
    }

    public String getDateCut(){
        return appPreferences.getString(DATE_CUT, null);
    }

    public void deleteDateCut(){
        editor.remove(DATE_CUT);
        editor.apply();
    }

    public void saveSalonName (String salonName){
        editor.putString(SALON_NAME, salonName);
        editor.commit();
    }

    public String getSalonName(){
        return appPreferences.getString(SALON_NAME, null);
    }

    public void deleteSalonName(){
        editor.remove(SALON_NAME);
        editor.apply();
    }

}
