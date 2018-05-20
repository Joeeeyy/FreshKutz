package com.jjoey.freshkutz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoey.freshkutz.R;
import com.jjoey.freshkutz.models.ShareOptions;

import java.util.List;

/**
 * Created by JosephJoey on 5/20/2018.
 */

public class ShareAdapter extends RecyclerView.Adapter<ShareViewHolder> {

    private final Context context;
    private List<ShareOptions> itemsList;

    public ShareAdapter(Context context, List<ShareOptions> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @Override
    public ShareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.share_items_layout, parent, false);
        return new ShareViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShareViewHolder viewholder, int position) {
        ShareOptions options = itemsList.get(position);

    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        }
        return itemsList.size();
    }
}