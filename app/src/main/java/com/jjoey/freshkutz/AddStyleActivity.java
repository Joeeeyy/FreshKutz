package com.jjoey.freshkutz;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jjoey.freshkutz.db.HairDB;
import com.jjoey.freshkutz.models.FreshKutz;
import com.jjoey.freshkutz.utils.SharedPrefsHelper;
import com.jjoey.freshkutz.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.Date;

public class AddStyleActivity extends AppCompatActivity {

    private static final String TAG = AddStyleActivity.class.getSimpleName();

    private Toolbar toolbar;
    private TextView cancelTV;

    private EditText captionET, salonET, dateET, descET;
    private ImageView frontIV, sideIV, backIV;
    private Button continueBtn;

    private String title, salon, date, description;
    private boolean isFrontImg = false, isBackImg = false, isSideImg = false;

    private static final int FRONT_CODE = 200;
    private static final int SIDE_CODE = 201;
    private static final int BACK_CODE = 202;
    private static final int PERMS_REQ_CODE = 203;

    private FreshKutz freshKutz;
    private String frontBase64Image, sideBase64Image, backBase64Image;

    private boolean isGranted = false;

    private SharedPrefsHelper prefsHelper;
    private HairDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefsHelper = new SharedPrefsHelper(this);
        setContentView(R.layout.activity_add_style);

        String path = getFilesDir().getPath();
        Log.d(TAG, "Files Dir:\t" + path);

//        String uid = UUID.randomUUID().toString();
//        Log.d(TAG, "UUID:\t" + uid);

        freshKutz = new FreshKutz();
        init();
        setSupportActionBar(toolbar);

        database = new HairDB(this);
        freshKutz = new FreshKutz();

        cancelTV.setOnClickListener(v -> {
            startMainActivity();
        });

        frontIV.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 23) {
                checkPerms();
                isFrontImg = true;
                Log.d(TAG, "isFrontImage 23");
                isBackImg = false;
                isSideImg = false;
            } else {
                isFrontImg = true;
                Log.d(TAG, "isFrontImage");
                isBackImg = false;
                isSideImg = false;
                if (isGranted == true){
                    showChooserDialog();
                } else {
                    showSnack();
                }
            }
        });

        sideIV.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 23) {
                isSideImg = true;
                Log.d(TAG, "isSideImage 23");
                isBackImg = false;
                isFrontImg = false;
                checkPerms();
            } else {
                isSideImg = true;
                Log.d(TAG, "isSideImage");
                isBackImg = false;
                isFrontImg = false;
                if (isGranted == true){
                    showChooserDialog();
                } else {
                    showSnack();
                }
            }
        });

        backIV.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 23) {
                Log.d(TAG, "isBackImage 23");
                isBackImg = true;
                isSideImg = false;
                isFrontImg = false;
                checkPerms();
            } else {
                isBackImg = true;
                isSideImg = false;
                isFrontImg = false;
                Log.d(TAG, "isBackImage");
                if (isGranted == true){
                    showChooserDialog();
                } else {
                    showSnack();
                }
            }
        });

        continueBtn.setOnClickListener(v -> {
            startPreviewActivity();
        });

    }

    private void checkPerms() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            askPermissions();
        } else {
            isGranted = true;
            showChooserDialog();
        }
    }

    private void askPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMS_REQ_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMS_REQ_CODE:
                for (int gr : grantResults) {
                    if (gr == PackageManager.PERMISSION_GRANTED) {
                        showChooserDialog();
                    } else {
                        if (gr == PackageManager.PERMISSION_DENIED) {
                            isGranted = false;
                            askPermissions();
                            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                                Log.d(TAG, "Camera Rationale");
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Enable Camera Permission in Settings for the app to work", Snackbar.LENGTH_INDEFINITE);
                                snackbar.setAction("GRANT", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent settingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), "");
                                        settingsIntent.setData(uri);
                                        startActivityForResult(settingsIntent, 0);
                                    }
                                });
                                snackbar.setActionTextColor(Color.GREEN);
                                snackbar.show();
                            } else if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                Log.d(TAG, "Storage Rationale");
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Enable Storage Permission in Settings for the app to work", Snackbar.LENGTH_INDEFINITE);
                                snackbar.setAction("GRANT", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent settingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), "");
                                        settingsIntent.setData(uri);
                                        startActivityForResult(settingsIntent, PERMS_REQ_CODE);
                                    }
                                });
                                snackbar.setActionTextColor(Color.GREEN);
                                snackbar.show();
                            }
                        }
                    }
                }
                break;
        }

    }

    private void showChooserDialog() {

        CharSequence[] items = {"Open From Camera"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Open From Camera")) {
                    if (isFrontImg && isGranted) {
                        launchCamera(FRONT_CODE);
                    } else if (isSideImg && isGranted) {
                        launchCamera(SIDE_CODE);
                    } else if (isBackImg && isGranted) {
                        launchCamera(BACK_CODE);
                    }
                }
            }
        });
        builder.show();

    }

    private void launchCamera(int req_code) {
        Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camIntent, req_code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case FRONT_CODE:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    frontBase64Image = Utils.bitmapToBase64String(bitmap);
//                    prefsHelper.saveFrontImage(frontBase64Image);
                    Bitmap bitmap_front = Utils.base64StringToBitmap(frontBase64Image);
                    frontIV.setImageBitmap(bitmap_front);
                }
                break;
            case SIDE_CODE:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    sideBase64Image = Utils.bitmapToBase64String(bitmap);
//                    prefsHelper.saveSideImage(sideBase64Image);
                    Bitmap bitmap_side = Utils.base64StringToBitmap(sideBase64Image);
                    sideIV.setImageBitmap(bitmap_side);
                }
                break;
            case BACK_CODE:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    backBase64Image = Utils.bitmapToBase64String(bitmap);
//                    prefsHelper.saveBackImage(backBase64Image);
                    Bitmap bitmap_back = Utils.base64StringToBitmap(backBase64Image);
                    backIV.setImageBitmap(bitmap_back);
                }
                break;
        }

    }

    private void startPreviewActivity() {

        title = captionET.getText().toString();
        salon = salonET.getText().toString();
        date = dateET.getText().toString();
        description = descET.getText().toString();

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(salon) && !TextUtils.isEmpty(date) && frontBase64Image != null && sideBase64Image != null) {
            Log.d(TAG, "Null Check Success");
            Intent intent = new Intent(AddStyleActivity.this, PreviewStyleActivity.class);
//            prefsHelper.saveTitle(title);
//            prefsHelper.saveDescription(description);
//            prefsHelper.saveSalonName(salon);
//            prefsHelper.saveDateCut(date);

            if (frontBase64Image != null && backBase64Image != null && sideBase64Image != null) {
                freshKutz.setFrontImage(frontBase64Image);
                freshKutz.setSideImage(sideBase64Image);
                freshKutz.setBackImage(backBase64Image);
            }

            freshKutz.setTitle(title);
            freshKutz.setSalon_City(salon);
            freshKutz.setDescription(description);

            if (date != null) {
                freshKutz.setDate(date);
            } else {
                freshKutz.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            }
            //boolean result = database.addNewStyle(freshKutz);
            if (database.addNewStyle(freshKutz)) {
                Log.d(TAG, "Single id:\t" + freshKutz.getFreshKutzId());
                Log.d(TAG, "Save Success");
            } else {
                Log.d(TAG, "Save Failed");
            }

            startActivity(intent);
        } else {
            Log.d(TAG, "Null Check Failure");
            Snackbar.make(findViewById(android.R.id.content), "Enter Title, Salon Name, Date and Capture At least One Image", Snackbar.LENGTH_LONG).show();
        }
    }

    private void showSnack() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Enable All Permissions in Settings for the app to work", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("GRANT", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), "");
                settingsIntent.setData(uri);
                startActivityForResult(settingsIntent, 0);
            }
        });
        snackbar.setActionTextColor(Color.GREEN);
        snackbar.show();
    }

    private void startMainActivity() {

        Log.d(TAG, "Single id:\t" + freshKutz.getFreshKutzId());
        if (freshKutz.getFreshKutzId() > 0) {
            database.deleteKut(freshKutz.getFreshKutzId());
            Intent mainIntent = new Intent(AddStyleActivity.this, MainActivity.class);
            startActivity(mainIntent);
        } else {
            Intent mainIntent = new Intent(AddStyleActivity.this, MainActivity.class);
            startActivity(mainIntent);
        }

//        prefsHelper.deleteStyleTitle();
//        prefsHelper.deleteSalonName();
//        prefsHelper.deleteStyleTitle();
//        prefsHelper.deleteDesc();
//        prefsHelper.deleteDateCut();
//
//        prefsHelper.deleteFrontImage();
//        prefsHelper.deleteSideImage();
//        prefsHelper.deleteBackImage();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        cancelTV = findViewById(R.id.cancelTV);
        captionET = findViewById(R.id.captionET);
        salonET = findViewById(R.id.salonET);
        dateET = findViewById(R.id.dateET);
        descET = findViewById(R.id.descET);
        frontIV = findViewById(R.id.frontIV);
        backIV = findViewById(R.id.backIV);
        sideIV = findViewById(R.id.sideIV);
        continueBtn = findViewById(R.id.continueBtn);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        prefsHelper.deleteStyleTitle();
        prefsHelper.deleteSalonName();
        prefsHelper.deleteStyleTitle();
        prefsHelper.deleteDesc();
        prefsHelper.deleteDateCut();

        prefsHelper.deleteFrontImage();
        prefsHelper.deleteSideImage();
        prefsHelper.deleteBackImage();

    }

}
