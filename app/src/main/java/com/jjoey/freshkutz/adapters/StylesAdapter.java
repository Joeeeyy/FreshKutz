package com.jjoey.freshkutz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoey.freshkutz.R;
import com.jjoey.freshkutz.models.FreshKutz;
import com.jjoey.freshkutz.utils.SharedPrefsHelper;
import com.jjoey.freshkutz.utils.Utils;

import java.util.List;

/**
 * Created by JosephJoey on 5/9/2018.
 */

public class StylesAdapter extends RecyclerView.Adapter<StylesViewHolder> {

    private static final String TAG = StylesAdapter.class.getSimpleName();

    private final Context context;
    private List<FreshKutz> itemsList;
    private SharedPrefsHelper prefsHelper;

    public StylesAdapter(Context context, List<FreshKutz> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        prefsHelper = new SharedPrefsHelper(context);
    }

    @Override
    public StylesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fresh_kutz_row_layout, parent, false);
        return new StylesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StylesViewHolder viewholder, int position) {
        FreshKutz kutz = itemsList.get(position);
        String title = prefsHelper.getStyleTitle();
        String date = prefsHelper.getDateCut();
        String salonName = prefsHelper.getSalonName();
        String coverImg = prefsHelper.getCoverImage();

        kutz.setCoverImage(coverImg);
        kutz.setTitle(title);
        kutz.setSalon_City(salonName);
        kutz.setDate(date);
        kutz.setCoverImage(coverImg);

        viewholder.froshTitle.setText(kutz.getTitle());
        viewholder.froshLocation.setText(kutz.getSalon_City());
        viewholder.froshDate.setText(kutz.getDate());
        viewholder.coverImg.setImageBitmap(Utils.base64StringToBitmap(kutz.getCoverImage()));

    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        }
        return itemsList.size();
    }
}