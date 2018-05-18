package com.jjoey.freshkutz.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jjoey.freshkutz.R;

/**
 * Created by JosephJoey on 5/9/2018.
 */

public class StylesViewHolder extends RecyclerView.ViewHolder {

    public ImageView coverImg;
    public TextView froshTitle, froshDate, froshLocation;
    public CardView cardStyle;

    public StylesViewHolder(View itemView) {
        super(itemView);

        coverImg = itemView.findViewById(R.id.coverImg);
        froshTitle = itemView.findViewById(R.id.froshTitle);
        froshDate = itemView.findViewById(R.id.froshDate);
        froshLocation = itemView.findViewById(R.id.froshLocation);
        cardStyle = itemView.findViewById(R.id.cardStyle);

    }
}
