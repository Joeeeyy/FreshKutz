package com.jjoey.freshkutz.utils;

import java.net.PortUnreachableException;

/**
 * Created by JosephJoey on 5/9/2018.
 */

public class Constants {

    public static final String DB_NAME = "hair_styles.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "fresh_kutz";

    public static final String COL_TITLE = "style_title";
    public static final String COL_DESCRIPTION = "style_desc";
    public static final String COL_DATE = "date_cut";
    public static final String COL_SALON = "salon_or_city";

    public static final String COL_COVER_IMAGE = "cover_img_str";
    public static final String COL_FRONT_IMAGE = "front_img_str";
    public static final String COL_SIDE_IMAGE = "side_img_str";
    public static final String COL_BACK_IMAGE = "back_img_str";

    public static final String[] COLUMNS = {COL_TITLE, COL_DESCRIPTION, COL_DATE, COL_SALON, COL_FRONT_IMAGE, COL_SIDE_IMAGE, COL_BACK_IMAGE };

    public static final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME
            + " ( " + "ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TITLE +" text, "
            + COL_DESCRIPTION + " text, " + COL_DATE + " text, " + COL_SALON + " text, "
            + COL_FRONT_IMAGE + " text, " + COL_SIDE_IMAGE + " text, " + COL_BACK_IMAGE + " text, "
            + COL_COVER_IMAGE + " text )";

    public static final String SELECT_ALL_QUERY = "SELECT * FROM " + TABLE_NAME;

}
