package com.jjoey.freshkutz.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.jjoey.freshkutz.R;
import com.jjoey.freshkutz.adapters.ImageSlideAdapter;
import com.jjoey.freshkutz.utils.Utils;
import com.tsurkis.timdicator.Timdicator;
import com.tsurkis.timdicator.TimdicatorBinder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JosephJoey on 5/18/2018.
 */

public class GalleryFragment extends DialogFragment {

    private static final String TAG = GalleryFragment.class.getSimpleName();

    private ImageView closeIV;
    private ViewPager viewPager;
    private ImageSlideAdapter slideAdapter;

    private FloatingActionMenu optionsFAB;
    private com.github.clans.fab.FloatingActionButton shareFAB, saveFAB;

    private Timdicator timDicator;

    private Bitmap[] bitmaps;
    private byte[] bytes;
    private List<Bitmap> list = new ArrayList<>();
    private static final int PERMS_CODE = 102;
    private Bitmap frontBitmap, backBitmap, sideBitmap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String front = bundle.getString("bitmap_front");
        String back = bundle.getString("bitmap_back");
        String side = bundle.getString("bitmap_side");
        int position = bundle.getInt("clicked_position");

        frontBitmap = Utils.base64StringToBitmap(front);
        backBitmap = Utils.base64StringToBitmap(back);
        sideBitmap = Utils.base64StringToBitmap(side);

        if (front != null && back != null && side != null) {
            bitmaps = new Bitmap[]{frontBitmap, sideBitmap, backBitmap};
        }

        View gallView = inflater.inflate(R.layout.gallery_fragment_layout, container, false);

        closeIV = gallView.findViewById(R.id.closeIV);
        viewPager = gallView.findViewById(R.id.galleryPager);
        timDicator = gallView.findViewById(R.id.timDicator);
        optionsFAB = gallView.findViewById(R.id.optionsFAB);
        saveFAB = gallView.findViewById(R.id.saveFAB);
        shareFAB = gallView.findViewById(R.id.shareFAB);

        for (int i = 0; i < bitmaps.length; i++) {
            list.add(bitmaps[i]);
        }

        slideAdapter = new ImageSlideAdapter(getActivity(), list);
        viewPager.setAdapter(slideAdapter);
        viewPager.setCurrentItem(position);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (optionsFAB.isOpened()) {
                    optionsFAB.close(true);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (optionsFAB.isOpened()) {
                    optionsFAB.close(true);
                }
            }
        });

        timDicator = new Timdicator(getActivity());
        timDicator.setChosenCircleColor(getActivity(), R.color.colorPrimaryDark);
        timDicator.setDefaultCircleColor(getActivity(), R.color.gray);
        timDicator.setCircleRadiusInDp(getActivity(), 20f);
        timDicator.setDistanceBetweenCircleInDp(getActivity(), 2f);
        TimdicatorBinder.attachViewPagerDynamically(timDicator, viewPager);

        closeIV.setOnClickListener(v -> {
            getDialog().dismiss();
        });

        checkPerms();

        saveFAB.setOnClickListener(v -> {
            switch (viewPager.getCurrentItem()) {
                case 0:
                    storeImage();
//                    if (Build.VERSION.SDK_INT >= 23) {
//                        checkPerms();
//                    } else {
////                        downloadImage(frontBitmap);
//                        Toast.makeText(getActivity(), "Image Saved to Gallery", Toast.LENGTH_SHORT).show();
//                    }
                    break;
                case 1:
//                    if (Build.VERSION.SDK_INT >= 23) {
//                        checkPerms();
//                    } else {
////                        downloadImage(sideBitmap);
//                        Toast.makeText(getActivity(), "Image Saved to Gallery", Toast.LENGTH_SHORT).show();
//                    }
                    break;
                case 2:
//                    if (Build.VERSION.SDK_INT >= 23) {
//                        checkPerms();
//                    } else {
////                        downloadImage(backBitmap);
//                        Toast.makeText(getActivity(), "Image Saved to Gallery", Toast.LENGTH_SHORT).show();
//                    }
                    break;
            }
        });

        return gallView;
    }

    @TargetApi(23)
    private void storeImage() {

    }

    private void downloadImage(Bitmap bitmap) {
        SaveParams saveParams = new SaveParams();
        bytes = Utils.bitmapToArray(bitmap);
        saveParams.inputs = bytes;
//        saveParams.title = "Hair_Style" + System.currentTimeMillis();
        saveParams.title = "Hair_Style" + new Timestamp(new Date().getTime()).toString();
        SavePhotoAsync photoAsync = new SavePhotoAsync();
        photoAsync.execute(saveParams);
    }

    private void checkPerms() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            askPerms();
        }
    }

    private void askPerms() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMS_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMS_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveFAB.setOnClickListener(v -> {
                        switch (viewPager.getCurrentItem()) {
                            case 0:
                                downloadImage(frontBitmap);
                                break;
                            case 1:
                                downloadImage(sideBitmap);
                                break;
                            case 2:
                                downloadImage(backBitmap);
                                break;
                        }
                    });
                } else {
                    getDialog().dismiss();
                }
                break;
        }
    }

    private class SaveParams {
        byte[] inputs;
        String title;

        public SaveParams() {
        }

        public SaveParams(byte[] inputs, String title) {
            this.inputs = inputs;
            this.title = title;
        }

    }

    private class SavePhotoAsync extends AsyncTask<SaveParams, Void, Void> {

        private File imageFile;
        private int rotation = 0;

        @Override
        protected Void doInBackground(SaveParams... saveParams) {
            Bitmap loadedImage = null;
            Bitmap rotatedBitmap = null;
            loadedImage = BitmapFactory.decodeByteArray(saveParams[0].inputs, 0,
                    saveParams[0].inputs.length);

            // rotate Image
            Matrix rotateMatrix = new Matrix();
            rotateMatrix.postRotate(rotation);
            rotatedBitmap = Bitmap.createBitmap(loadedImage, 0, 0,
                    loadedImage.getWidth(), loadedImage.getHeight(),
                    rotateMatrix, false);
            String state = Environment.getExternalStorageState();
            File folder = null;
            if (state.contains(Environment.MEDIA_MOUNTED)) {
                folder = new File(Environment
                        .getExternalStorageDirectory() + "/FreshKutz");
            } else {
                folder = new File(Environment
                        .getExternalStorageDirectory() + "/FreshKutz");
            }

            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdirs();
            }
            if (success) {
                java.util.Date date = new java.util.Date();
                imageFile = new File(folder.getAbsolutePath()
                        + File.separator
                        + saveParams[0].title);
//                        +new Timestamp(date.getTime()).toString()
//                        + "Image.jpg");

                try {
                    imageFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Image Saved to Gallery",
                                Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Image Not Saved",
                                Toast.LENGTH_LONG).show();
                    }
                });
                return null;
            }

            ByteArrayOutputStream ostream = new ByteArrayOutputStream();

            // save testImage into gallery
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);

            FileOutputStream fout = null;
            try {
                fout = new FileOutputStream(imageFile);
                fout.write(ostream.toByteArray());
                fout.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ContentValues values = new ContentValues();

            values.put(MediaStore.Images.Media.DATE_TAKEN,
                    System.currentTimeMillis());
            values.put(MediaStore.Images.Media.MIME_TYPE, "testImage/jpeg");
            values.put(MediaStore.MediaColumns.DATA,
                    imageFile.getAbsolutePath());

            getActivity().getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

//            return imageFile.getAbsolutePath();
            return null;
        }
    }

    @Override
    public void onStart() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        super.onStart();
    }

}
