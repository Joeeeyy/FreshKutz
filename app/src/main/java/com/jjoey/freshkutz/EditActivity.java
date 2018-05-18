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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.jjoey.freshkutz.models.FreshKutz;
import com.jjoey.freshkutz.utils.Utils;

import java.text.DateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = EditActivity.class.getSimpleName();

    private Toolbar toolbar;
    private EditText edit_style_titleET, edit_style_descET, edit_stylist_nameET, edit_style_salonET;
    private ImageView checkImg, edit_frontIV, edit_sideIV, edit_backIV;
    private TextView cancelTV;

    private String kutz_id, title, desc, stylist_name, salon;

    private static final int FRONT_CODE = 200;
    private static final int SIDE_CODE = 201;
    private static final int BACK_CODE = 202;
    private static final int PERMS_REQ_CODE = 203;

    private boolean isFrontImg = false, isBackImg = false, isSideImg = false, isGranted = false;
    private String oldFrontImg, oldBackImg, oldSideImg;
    private String frontImg, sideImg, backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        kutz_id = getIntent().getExtras().getString("style_id");

        init();
        setSupportActionBar(toolbar);

        if (kutz_id != null) {
            Log.d(TAG, "Kutz id is:\t" + kutz_id);
            fetchSingleKutz(kutz_id);
            enableInputs();
        } else {
            Log.d(TAG, "Kutz id is:\t" + kutz_id);
        }

        cancelTV.setOnClickListener(v -> {
            startActivity(new Intent(EditActivity.this, MainActivity.class));
        });

        edit_frontIV.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 23) {
                checkPerms();
                isFrontImg = true;
                Log.d(TAG, "EDIT---isFrontImage 23");
                isBackImg = false;
                isSideImg = false;
            } else {
                isFrontImg = true;
                Log.d(TAG, "EDIT---isFrontImage");
                isBackImg = false;
                isSideImg = false;
                showChooserDialog();
            }
        });

        edit_sideIV.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 23) {
                isSideImg = true;
                Log.d(TAG, "EDIT---isSideImage 23");
                isBackImg = false;
                isFrontImg = false;
                checkPerms();
            } else {
                isSideImg = true;
                Log.d(TAG, "EDIT---isSideImage");
                isBackImg = false;
                isFrontImg = false;
                showChooserDialog();
            }
        });

        edit_backIV.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 23) {
                Log.d(TAG, "EDIT---isBackImage 23");
                isBackImg = true;
                isSideImg = false;
                isFrontImg = false;
                checkPerms();
            } else {
                isBackImg = true;
                isSideImg = false;
                isFrontImg = false;
                Log.d(TAG, "EDIT---isBackImage");
                showChooserDialog();
            }
        });

        checkImg.setOnClickListener(v -> {
            validate();
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
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (isFrontImg && isGranted) {
                            launchCamera(FRONT_CODE);
                        } else if (isSideImg && isGranted) {
                            launchCamera(SIDE_CODE);
                        } else if (isBackImg && isGranted) {
                            launchCamera(BACK_CODE);
                        }
                    } else {
                        if (isFrontImg) {
                            launchCamera(FRONT_CODE);
                        } else if (isSideImg) {
                            launchCamera(SIDE_CODE);
                        } else if (isBackImg) {
                            launchCamera(BACK_CODE);
                        }
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
                    frontImg = Utils.bitmapToBase64String(bitmap);
                    Bitmap bitmap_front = Utils.base64StringToBitmap(frontImg);
                    edit_frontIV.setImageBitmap(bitmap_front);
                }
                break;
            case SIDE_CODE:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    sideImg = Utils.bitmapToBase64String(bitmap);
                    Bitmap bitmap_side = Utils.base64StringToBitmap(sideImg);
                    edit_sideIV.setImageBitmap(bitmap_side);
                }
                break;
            case BACK_CODE:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    backImg = Utils.bitmapToBase64String(bitmap);
                    Bitmap bitmap_back = Utils.base64StringToBitmap(backImg);
                    edit_backIV.setImageBitmap(bitmap_back);
                }
                break;
        }

    }

    private void validate() {
        title = edit_style_titleET.getText().toString();
        Log.d(TAG, "Curr title:\t" + title);
        desc = edit_style_descET.getText().toString();
        stylist_name = edit_stylist_nameET.getText().toString();
        salon = edit_style_salonET.getText().toString();

//        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc) && !TextUtils.isEmpty(stylist_name) && !TextUtils.isEmpty(salon) && !TextUtils.isEmpty(frontImg) && !TextUtils.isEmpty(sideImg) && !TextUtils.isEmpty(backImg)) {
        if (title != "" && desc != "" && stylist_name != "" && salon != "" && frontImg != "" && sideImg != "" && backImg != ""){
            Log.d(TAG, "Curr title:\t" + title);
            saveNewDetails(title, desc, stylist_name, salon, frontImg, sideImg, backImg);
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Please Enter all Fields Correctly", Snackbar.LENGTH_LONG).show();
        }

    }

    private void saveNewDetails(String title, String desc, String stylist_name, String salon, String frontImg, String sideImg, String backImg) {
        if (kutz_id != null){
            FreshKutz singleKut = new Select()
                    .from(FreshKutz.class)
                    .where("style_id = ? ", kutz_id)
                    .executeSingle();

            Intent editIntent = new Intent(EditActivity.this, MainActivity.class);

            singleKut.title = title;
            singleKut.description = desc;
            singleKut.stylist_name = stylist_name;
            singleKut.salon_City = salon;
            singleKut.date = DateFormat.getDateTimeInstance().format(new Date());
            if (frontImg == null){
                singleKut.frontImage = oldFrontImg;
                Log.d(TAG, "Old Front Image:\t" + oldFrontImg);
            } else {
                singleKut.frontImage = frontImg;
                Log.d(TAG, "New Front Image:\t" + frontImg);
            }
            if (sideImg == null){
                singleKut.sideImage = oldSideImg;
                Log.d(TAG, "Old Side Image:\t" + oldSideImg);
            } else {
                singleKut.sideImage = sideImg;
                Log.d(TAG, "New Side Image:\t" + sideImg);
            }
            if (backImg== null){
                singleKut.backImage = oldBackImg;
                Log.d(TAG, "old Back Image:\t" + oldBackImg);
            } else {
                singleKut.backImage = backImg;
                Log.d(TAG, "New Back Image:\t" + backImg);
            }

            singleKut.save();

            startActivity(editIntent);

        }
    }

    private void enableInputs() {
        edit_style_titleET.setFocusable(true);
        edit_style_titleET.setEnabled(true);

        edit_style_descET.setFocusable(true);
        edit_style_descET.setEnabled(true);

        edit_stylist_nameET.setFocusable(true);
        edit_stylist_nameET.setEnabled(true);

        edit_style_salonET.setFocusable(true);
        edit_style_salonET.setEnabled(true);
    }

    private void fetchSingleKutz(String kutz_id) {
        FreshKutz singleKut = new Select()
                .from(FreshKutz.class)
                .where("style_id = ? ", kutz_id)
                .executeSingle();

        edit_style_titleET.setText(singleKut.title);
        edit_style_descET.setText(singleKut.description);
        edit_stylist_nameET.setText(singleKut.stylist_name);
        edit_style_salonET.setText(singleKut.salon_City);

        oldFrontImg = singleKut.frontImage;
        oldBackImg = singleKut.backImage;
        oldSideImg = singleKut.sideImage;

        edit_frontIV.setImageBitmap(Utils.base64StringToBitmap(oldFrontImg));
        edit_backIV.setImageBitmap(Utils.base64StringToBitmap(oldBackImg));
        edit_sideIV.setImageBitmap(Utils.base64StringToBitmap(oldSideImg));

    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        cancelTV = findViewById(R.id.cancelTV);
        checkImg = findViewById(R.id.checkImg);
        edit_frontIV = findViewById(R.id.edit_frontIV);
        edit_backIV = findViewById(R.id.edit_backIV);
        edit_sideIV = findViewById(R.id.edit_sideIV);
        edit_style_titleET = findViewById(R.id.edit_style_titleET);
        edit_style_descET = findViewById(R.id.edit_style_descET);
        edit_stylist_nameET = findViewById(R.id.edit_stylist_nameET);
        edit_style_salonET = findViewById(R.id.edit_style_salonET);
    }

}
