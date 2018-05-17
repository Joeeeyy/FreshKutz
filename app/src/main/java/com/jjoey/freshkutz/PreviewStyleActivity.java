package com.jjoey.freshkutz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jjoey.freshkutz.db.HairDB;
import com.jjoey.freshkutz.utils.SharedPrefsHelper;
import com.jjoey.freshkutz.utils.Utils;

public class PreviewStyleActivity extends AppCompatActivity {

    private static final String TAG = PreviewStyleActivity.class.getSimpleName();

    private Toolbar toolbar;
    private TextView cancelTV;

    private ImageView frontShotIV, sideShotIV, backShotIV;
    private RadioGroup coverGroup;
    private RadioButton radioFrontImg, radioSideImg, radioBackImg;
    private FloatingActionButton proceedFAB;

    private SharedPrefsHelper prefsHelper;
    private String frontBase64Image, sideBase64Image, backBase64Image;
    private Bitmap bitmap_front, side_bitmap, back_bitmap;

    private HairDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_style);

        init();
        setSupportActionBar(toolbar);

        database = new HairDB(this);
        prefsHelper = new SharedPrefsHelper(this);

        frontBase64Image = prefsHelper.getFrontImage();
        bitmap_front = Utils.base64StringToBitmap(frontBase64Image);
//        frontShotIV.setImageBitmap(bitmap_front);
//        frontShotIV.setVisibility(View.GONE);

        Drawable drawable = new BitmapDrawable(getResources(), bitmap_front);
        radioFrontImg.setButtonDrawable(drawable);

        sideBase64Image = prefsHelper.getSideImage();
        side_bitmap = Utils.base64StringToBitmap(sideBase64Image);
//        sideShotIV.setImageBitmap(side_bitmap);
//        sideShotIV.setVisibility(View.GONE);
        Drawable drawable_side = new BitmapDrawable(getResources(), side_bitmap);
        radioSideImg.setButtonDrawable(drawable_side);

        backBase64Image = prefsHelper.getBackImage();
        back_bitmap = Utils.base64StringToBitmap(backBase64Image);
//        backShotIV.setImageBitmap(back_bitmap);
//        backShotIV.setVisibility(View.GONE);
        Drawable drawable_back = new BitmapDrawable(getResources(), back_bitmap);
        radioBackImg.setButtonDrawable(drawable_back);

        cancelTV.setOnClickListener( v -> {
            startMainActivity();
        });

        proceedFAB.setOnClickListener( v -> {
            if (radioFrontImg.isChecked() || radioSideImg.isChecked() || radioBackImg.isChecked()){
                startMainActivity();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Select One Image To Proceed", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void startMainActivity() {
        prefsHelper.deleteStyleTitle();
        prefsHelper.deleteSalonName();
        prefsHelper.deleteStyleTitle();
        prefsHelper.deleteDesc();
        prefsHelper.deleteDateCut();

        prefsHelper.deleteFrontImage();
        prefsHelper.deleteSideImage();
        prefsHelper.deleteBackImage();

        coverGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioFrontImg){
                    prefsHelper.saveCoverImage(frontBase64Image);
                    Snackbar.make(findViewById(android.R.id.content), "Front Image Will be used as Cover Image", Snackbar.LENGTH_LONG).show();
                } else if (i == R.id.radioBackImg){
                    prefsHelper.saveCoverImage(backBase64Image);
                    Snackbar.make(findViewById(android.R.id.content), "Back Image Will be used as Cover Image", Snackbar.LENGTH_LONG).show();
                } else if (i == R.id.radioSideImg){
                    prefsHelper.saveCoverImage(sideBase64Image);
                    Snackbar.make(findViewById(android.R.id.content), "Side Image Will be used as Cover Image", Snackbar.LENGTH_LONG).show();
                }
            }
        });

//        if (radioFrontImg.isChecked()){
//            prefsHelper.saveCoverImage(frontBase64Image);
//            Snackbar.make(findViewById(android.R.id.content), "Front Image Will be used as Cover Image", Snackbar.LENGTH_LONG).show();
//        } else if (radioBackImg.isChecked()){
//            prefsHelper.saveCoverImage(backBase64Image);
//            Snackbar.make(findViewById(android.R.id.content), "Back Image Will be used as Cover Image", Snackbar.LENGTH_LONG).show();
//        } else if (radioSideImg.isChecked()){
//            prefsHelper.saveCoverImage(sideBase64Image);
//            Snackbar.make(findViewById(android.R.id.content), "Side Image Will be used as Cover Image", Snackbar.LENGTH_LONG).show();
//        }

        Intent mainIntent = new Intent(PreviewStyleActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        cancelTV = findViewById(R.id.cancelTV);
//        frontShotIV = findViewById(R.id.frontShotIV);
//        sideShotIV = findViewById(R.id.sideShotIV);
//        backShotIV = findViewById(R.id.backShotIV);
        coverGroup = findViewById(R.id.coverGroup);
        radioFrontImg = findViewById(R.id.radioFrontImg);
        radioSideImg = findViewById(R.id.radioSideImg);
        radioBackImg = findViewById(R.id.radioBackImg);
        proceedFAB = findViewById(R.id.proceedFAB);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.ACTION_DOWN){
            super.onKeyDown(keyCode, event);
            return true;
        }
        return false;
    }

}
