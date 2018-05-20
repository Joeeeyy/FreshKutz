package com.jjoey.freshkutz.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoey.freshkutz.FullDetailsActivity;
import com.jjoey.freshkutz.R;
import com.jjoey.freshkutz.models.FreshKutz;
import com.jjoey.freshkutz.utils.Utils;

import java.util.List;

/**
 * Created by JosephJoey on 5/9/2018.
 */

public class StylesAdapter extends RecyclerView.Adapter<StylesViewHolder> {

    private static final String TAG = StylesAdapter.class.getSimpleName();

    private final Context context;
    private List<FreshKutz> itemsList;

    private long kutz_id;

    public StylesAdapter(Context context, List<FreshKutz> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public StylesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fresh_kutz_row_layout, parent, false);
        return new StylesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StylesViewHolder viewholder, int position) {
        FreshKutz kutz = itemsList.get(position);

        kutz_id = kutz.getId();
        Log.d(TAG, "Id from Position:\t" + kutz_id);
        Log.d(TAG, "Kutz-Id from Position:\t" + kutz.freshKutzId);

        viewholder.froshTitle.setText(kutz.title);
        viewholder.froshLocation.setText(kutz.getSalon_City());
        viewholder.froshDate.setText(kutz.getDate());
        viewholder.coverImg.setImageBitmap(Utils.base64StringToBitmap(kutz.getCoverImage()));

        viewholder.cardStyle.setOnClickListener( v -> {
            Intent viewIntent = new Intent(context, FullDetailsActivity.class);
            viewIntent.putExtra("style_id", kutz.freshKutzId);
            context.startActivity(viewIntent);
        });

    }

    public void removeAtPosition(int position, long id){
        FreshKutz.delete(FreshKutz.class, id);
        itemsList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemsList.size());
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        }
        return itemsList.size();
    }
}