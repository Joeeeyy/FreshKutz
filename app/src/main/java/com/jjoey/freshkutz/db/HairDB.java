package com.jjoey.freshkutz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jjoey.freshkutz.models.FreshKutz;
import com.jjoey.freshkutz.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JosephJoey on 5/14/2018.
 */

public class HairDB extends SQLiteOpenHelper {

    public HairDB(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(Constants.CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addNewStyle(FreshKutz kutz) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.COL_TITLE, kutz.getTitle());
        values.put(Constants.COL_DESCRIPTION, kutz.getDescription());
        values.put(Constants.COL_DATE, kutz.getDescription());
        values.put(Constants.COL_SALON, kutz.getSalon_City());
        values.put(Constants.COL_FRONT_IMAGE, kutz.getFrontImage());
        values.put(Constants.COL_SIDE_IMAGE, kutz.getSideImage());
        values.put(Constants.COL_BACK_IMAGE, kutz.getBackImage());
        values.put(Constants.COL_COVER_IMAGE, kutz.getCoverImage());

        if (db.insert(Constants.TABLE_NAME, null, values) != -1){
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    public List<FreshKutz> getAllKutz() {
        List<FreshKutz> kutzList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Constants.SELECT_ALL_QUERY, null);
        FreshKutz freshKutz = null;

        while (cursor.moveToFirst()){
            do {
                freshKutz = new FreshKutz();
                freshKutz.setFreshKutzId(cursor.getInt(0));
                freshKutz.setTitle(cursor.getString(1));
                freshKutz.setDescription(cursor.getString(2));
                freshKutz.setDate(cursor.getString(4));
                freshKutz.setSalon_City(cursor.getString(5));
                freshKutz.setFrontImage(cursor.getString(6));
                freshKutz.setSideImage(cursor.getString(7));
                freshKutz.setBackImage(cursor.getString(8));
                freshKutz.setCoverImage(cursor.getString(9));

                kutzList.add(freshKutz);

            } while (cursor.moveToNext());
        }
        return kutzList;
    }

    public int upDateKut(FreshKutz kutz){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.COL_TITLE, kutz.getTitle());
        values.put(Constants.COL_DESCRIPTION, kutz.getDescription());
        values.put(Constants.COL_DATE, kutz.getDescription());
        values.put(Constants.COL_SALON, kutz.getSalon_City());
        values.put(Constants.COL_FRONT_IMAGE, kutz.getFrontImage());
        values.put(Constants.COL_SIDE_IMAGE, kutz.getSideImage());
        values.put(Constants.COL_BACK_IMAGE, kutz.getBackImage());
        values.put(Constants.COL_COVER_IMAGE, kutz.getCoverImage());

        int result = db.update(Constants.TABLE_NAME, values, " id = ?",
                new String[]{String.valueOf(kutz.getFreshKutzId())});

        db.close();
        return result;
    }

    public FreshKutz getSingleKut(int kutId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME, Constants.COLUMNS, " id = ?", new String[]{String.valueOf(kutId)}, null, null, null, null);

        FreshKutz freshKutz = null;

        if (cursor != null) {
            cursor.moveToFirst();
        }

        freshKutz = new FreshKutz();
        freshKutz.setFreshKutzId(cursor.getColumnIndex("ID"));
        freshKutz.setTitle(cursor.getString(1));
        freshKutz.setDescription(cursor.getString(2));
        freshKutz.setDate(cursor.getString(4));
        freshKutz.setSalon_City(cursor.getString(5));
        freshKutz.setFrontImage(cursor.getString(6));
        freshKutz.setSideImage(cursor.getString(7));
        freshKutz.setBackImage(cursor.getString(8));
//        freshKutz.setCoverImage(cursor.getString(9));

        return freshKutz;
    }

    public void deleteKut(int kutzId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, " id = ?", new String[]{String.valueOf(kutzId)});
        db.close();
    }

}
