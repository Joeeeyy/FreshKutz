package com.jjoey.freshkutz.fragments;

import android.app.DialogFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jjoey.freshkutz.R;
import com.jjoey.freshkutz.adapters.ImageSlideAdapter;
import com.jjoey.freshkutz.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JosephJoey on 5/18/2018.
 */

public class GalleryFragment extends DialogFragment {

    private static final String TAG = GalleryFragment.class.getSimpleName();

    private ImageView closeIV;
    private ViewPager viewPager;
    private ImageSlideAdapter slideAdapter;

    private static final int NUM_PAGES = 0;
    private static final int CURR_PAGE = 0;

    private Bitmap[] bitmaps;
    private List<Bitmap> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String front = bundle.getString("bitmap_front");
        String back = bundle.getString("bitmap_back");
        String side = bundle.getString("bitmap_side");
        int position = bundle.getInt("clicked_position");

        Bitmap frontBitmap = Utils.base64StringToBitmap(front);
        Bitmap backBitmap = Utils.base64StringToBitmap(back);
        Bitmap sideBitmap = Utils.base64StringToBitmap(side);

        if (front != null && back != null && side != null){
            bitmaps = new Bitmap[]{frontBitmap, sideBitmap, backBitmap};
            Log.d(TAG, "Bitmaps Array length:\t" + bitmaps.length);
        }

//        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog_NoTitle);
        View gallView = inflater.inflate(R.layout.gallery_fragment_layout, container, false);

        closeIV = gallView.findViewById(R.id.closeIV);
        viewPager = gallView.findViewById(R.id.galleryPager);

        for (int i = 0; i < bitmaps.length; i++) {
            list.add(bitmaps[i]);
            Log.d(TAG, "List Size:\t" + list.size());
        }

        slideAdapter = new ImageSlideAdapter(getActivity(), list);
        viewPager.setAdapter(slideAdapter);
        viewPager.setCurrentItem(position);

        closeIV.setOnClickListener( v -> {
            getDialog().dismiss();
        });

        return gallView;
    }
}
