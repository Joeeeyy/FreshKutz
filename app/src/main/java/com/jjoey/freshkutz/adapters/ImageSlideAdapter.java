package com.jjoey.freshkutz.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jjoey.freshkutz.R;
import com.jjoey.freshkutz.utils.TouchZoomImageView;

import java.util.List;

/**
 * Created by JosephJoey on 5/18/2018.
 */

public class ImageSlideAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Bitmap> bitmapList;

    public ImageSlideAdapter(Context context, List<Bitmap> bitmapList) {
        this.context = context;
        this.bitmapList = bitmapList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bitmapList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View sliderLayout = inflater.inflate(R.layout.item_gallery_layout, container, false);
        ImageView imageSliderIV = sliderLayout.findViewById(R.id.imageSliderIV);
        //TouchZoomImageView imageSliderIV = sliderLayout.findViewById(R.id.imageSliderIV);
        imageSliderIV.setImageBitmap(bitmapList.get(position));
        container.addView(sliderLayout, 0);
        return sliderLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
