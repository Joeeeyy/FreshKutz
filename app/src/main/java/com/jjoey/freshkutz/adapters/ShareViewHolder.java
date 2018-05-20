package com.jjoey.freshkutz.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoey.freshkutz.R;

/**
 * Created by JosephJoey on 5/20/2018.
 */

public class ShareViewHolder extends RecyclerView.ViewHolder {

    public ImageView iconImg;
    public TextView titleTxt;

    public ShareViewHolder(View itemView) {
        super(itemView);

        iconImg = itemView.findViewById(R.id.iconImg);
        titleTxt = itemView.findViewById(R.id.titleTxt);

    }
}
