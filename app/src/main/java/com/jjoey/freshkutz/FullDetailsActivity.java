package com.jjoey.freshkutz;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.jjoey.freshkutz.fragments.GalleryFragment;
import com.jjoey.freshkutz.models.FreshKutz;
import com.jjoey.freshkutz.utils.Constants;
import com.jjoey.freshkutz.utils.TouchZoomImageView;
import com.jjoey.freshkutz.utils.Utils;

public class FullDetailsActivity extends AppCompatActivity {

    private static final String TAG = FullDetailsActivity.class.getSimpleName();

    private Toolbar toolbar;
    private EditText details_style_titleET, detail_style_descET, detail_stylist_nameET, detail_style_salonET;
    private ImageView backImg, details_frontIV, details_sideIV, details_backIV;
    private TextView editTV;

    private String kutz_id;
    private String frontBitmapString;
    private String sideBitmapString;
    private String backImageBitmapString;
    private int isFrontClicked = 0, isSideClicked = 1, isBackClicked = 2;

    private InterstitialAd ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_details);

        kutz_id = getIntent().getExtras().getString("style_id");

        init();
        setSupportActionBar(toolbar);

        if (kutz_id != null){
            fetchSingleKutz(kutz_id);
            disableInputs();
        } else {
            Log.d(TAG, "Kutz id is NULL:\t" + kutz_id);
        }

        backImg.setOnClickListener( v -> {
            startActivity(new Intent(FullDetailsActivity.this, MainActivity.class));
        });

        editTV.setOnClickListener( v -> {
            Intent editIntent = new Intent(FullDetailsActivity.this, EditActivity.class);
            editIntent.putExtra("style_id", kutz_id);
            startActivity(editIntent);
        });

        initAds();

    }

    private void initAds() {
        ad = new InterstitialAd(this);
        ad.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        ad.loadAd(new AdRequest.Builder().build());
    }

    private void disableInputs() {
        details_style_titleET.setFocusable(false);
        details_style_titleET.setEnabled(false);

        detail_style_descET.setFocusable(false);
        detail_style_descET.setEnabled(false);

        detail_stylist_nameET.setFocusable(false);
        detail_stylist_nameET.setEnabled(false);

        detail_style_salonET.setFocusable(false);
        detail_style_salonET.setEnabled(false);
    }

    private void fetchSingleKutz(String kutz_id) {
        FreshKutz singleKut = new Select()
                .from(FreshKutz.class)
                .where("style_id = ? ", kutz_id)
                .executeSingle();

        details_style_titleET.setText(singleKut.title);
        detail_style_descET.setText(singleKut.description);
        detail_stylist_nameET.setText(singleKut.stylist_name);
        detail_style_salonET.setText(singleKut.salon_City);

        frontBitmapString = singleKut.frontImage;
        sideBitmapString = singleKut.sideImage;
        backImageBitmapString = singleKut.backImage;

        details_frontIV.setImageBitmap(Utils.base64StringToBitmap(frontBitmapString));
        details_backIV.setImageBitmap(Utils.base64StringToBitmap(backImageBitmapString));
        details_sideIV.setImageBitmap(Utils.base64StringToBitmap(sideBitmapString));

        details_frontIV.setOnClickListener( v -> {
            if (ad.isLoaded()) {
                ad.show();
            }
            startGalleryFragment(frontBitmapString, backImageBitmapString, sideBitmapString, isFrontClicked);
        });

        details_backIV.setOnClickListener( v -> {
            if (ad.isLoaded()) {
                ad.show();
            }
            startGalleryFragment(frontBitmapString, backImageBitmapString, sideBitmapString, isBackClicked);
        });

        details_sideIV.setOnClickListener( v -> {
            if (ad.isLoaded()) {
                ad.show();
            }
            startGalleryFragment(frontBitmapString, backImageBitmapString, sideBitmapString, isSideClicked);
        });

    }

    private void startGalleryFragment(String frontBitmapString, String backImageBitmapString, String sideBitmapString, int positionClicked) {
        GalleryFragment galleryFragment = new GalleryFragment();
        Bundle bitmaps = new Bundle();
        bitmaps.putString("bitmap_front", frontBitmapString);
        bitmaps.putString("bitmap_back", backImageBitmapString);
        bitmaps.putString("bitmap_side", sideBitmapString);
        bitmaps.putInt("clicked_position", positionClicked);
        galleryFragment.setArguments(bitmaps);
        galleryFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        galleryFragment.show(getFragmentManager(), "GalleryFragment");
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        editTV = findViewById(R.id.editTV);
        backImg = findViewById(R.id.backImg);
        details_frontIV = findViewById(R.id.details_frontIV);
        details_backIV = findViewById(R.id.details_backIV);
        details_sideIV = findViewById(R.id.details_sideIV);
        details_style_titleET = findViewById(R.id.details_style_titleET);
        detail_style_descET = findViewById(R.id.detail_style_descET);
        detail_stylist_nameET = findViewById(R.id.detail_stylist_nameET);
        detail_style_salonET = findViewById(R.id.detail_style_salonET);
    }

}
