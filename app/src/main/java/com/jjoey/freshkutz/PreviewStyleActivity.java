package com.jjoey.freshkutz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.jjoey.freshkutz.models.FreshKutz;
import com.jjoey.freshkutz.utils.Utils;

public class PreviewStyleActivity extends AppCompatActivity {

    private static final String TAG = PreviewStyleActivity.class.getSimpleName();

    private Toolbar toolbar;
    private TextView cancelTV;

    private RadioGroup coverGroup;
    private RadioButton radioFrontImg, radioSideImg, radioBackImg;
    private FloatingActionButton proceedFAB;

    private String current_id, frontBase64Image, sideBase64Image, backBase64Image;
    private Bitmap bitmap_front, side_bitmap, back_bitmap;

    private long kutz_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_style);

        current_id = getIntent().getExtras().getString("current_id");
        Log.d(TAG, "Curr id:\t" + current_id);

        kutz_id = getIntent().getExtras().getLong("kutz_id");
        Log.d(TAG, "Cutz id:\t" + kutz_id);

        init();
        setSupportActionBar(toolbar);

        queryBitmaps(current_id);

        cancelTV.setOnClickListener(v -> {
            startCancelMainActivity(kutz_id);
        });

        proceedFAB.setOnClickListener(v -> {
            if (radioFrontImg.isChecked() || radioSideImg.isChecked() || radioBackImg.isChecked()) {
                startMainActivity();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Select One Image To Proceed", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void startCancelMainActivity(long current_id) {
        FreshKutz.delete(FreshKutz.class, current_id);

        Intent mainIntent = new Intent(PreviewStyleActivity.this, MainActivity.class);
        startActivity(mainIntent);

    }

    private void queryBitmaps(String current_id) {
        FreshKutz im = new Select()
                .from(FreshKutz.class)
                .where("style_id = ? ", current_id)
                .executeSingle();
        Log.d(TAG, "Query:\t" + im);

        frontBase64Image = im.frontImage;
        bitmap_front = Utils.base64StringToBitmap(frontBase64Image);
        Drawable drawable = new BitmapDrawable(getResources(), bitmap_front);
        radioFrontImg.setButtonDrawable(drawable);

        sideBase64Image = im.sideImage;
        side_bitmap = Utils.base64StringToBitmap(sideBase64Image);
        Drawable drawable_side = new BitmapDrawable(getResources(), side_bitmap);
        radioSideImg.setButtonDrawable(drawable_side);

        backBase64Image = im.backImage;
        back_bitmap = Utils.base64StringToBitmap(backBase64Image);
        Drawable drawable_back = new BitmapDrawable(getResources(), back_bitmap);
        radioBackImg.setButtonDrawable(drawable_back);

    }

    private void startMainActivity() {

        FreshKutz singleKut = new Select()
                .from(FreshKutz.class)
                .where("style_id = ? ", current_id)
                .executeSingle();

        coverGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioFrontImg:
                        Log.d(TAG, "Front Checked");
                        singleKut.coverImage = frontBase64Image;
                        Log.d(TAG, "Cover Img:\t" + singleKut.coverImage);
                        break;
                    case R.id.radioBackImg:
                        singleKut.coverImage = backBase64Image;
                        Log.d(TAG, "Back Checked");
                        Log.d(TAG, "Cover Img:\t" + singleKut.coverImage);
                        break;
                    case R.id.radioSideImg:
                        singleKut.coverImage = sideBase64Image;
                        Log.d(TAG, "Side Checked");
                        Log.d(TAG, "Cover Img:\t" + singleKut.coverImage);
                        break;
                }
//                if (i == R.id.radioFrontImg) {
////                    freshKutz.setCoverImage(frontBase64Image);
//                    Log.d(TAG, "Front Checked");
//                    singleKut.coverImage = frontBase64Image;
//                    Log.d(TAG, "Cover Img:\t" + singleKut.coverImage);
//                    Snackbar.make(findViewById(android.R.id.content), "Front Image Will be used as Cover Image", Snackbar.LENGTH_LONG).show();
//                } else if (i == R.id.radioBackImg) {
////                    freshKutz.coverImage = backBase64Image;
//                    singleKut.coverImage = backBase64Image;
//                    Log.d(TAG, "Back Checked");
//                    Log.d(TAG, "Cover Img:\t" + singleKut.coverImage);
//                    Snackbar.make(findViewById(android.R.id.content), "Back Image Will be used as Cover Image", Snackbar.LENGTH_LONG).show();
//                } else if (i == R.id.radioSideImg) {
//                    singleKut.coverImage = sideBase64Image;
//                    Log.d(TAG, "Side Checked");
//                    Log.d(TAG, "Cover Img:\t" + singleKut.coverImage);
//                    Snackbar.make(findViewById(android.R.id.content), "Side Image Will be used as Cover Image", Snackbar.LENGTH_LONG).show();
//                }
            }
        });
        singleKut.save();
        Intent mainIntent = new Intent(PreviewStyleActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        cancelTV = findViewById(R.id.cancelTV);
        coverGroup = findViewById(R.id.coverGroup);
        radioFrontImg = findViewById(R.id.radioFrontImg);
        radioSideImg = findViewById(R.id.radioSideImg);
        radioBackImg = findViewById(R.id.radioBackImg);
        proceedFAB = findViewById(R.id.proceedFAB);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.ACTION_DOWN) {
            super.onKeyDown(keyCode, event);
            return true;
        }
        return false;
    }

}
