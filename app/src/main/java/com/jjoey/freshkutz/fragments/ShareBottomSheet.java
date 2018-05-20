package com.jjoey.freshkutz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoey.freshkutz.R;
import com.jjoey.freshkutz.adapters.ShareAdapter;
import com.jjoey.freshkutz.interfaces.RecyclerClickListener;
import com.jjoey.freshkutz.models.ShareOptions;
import com.jjoey.freshkutz.utils.RecyclerItemTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JosephJoey on 5/20/2018.
 */

public class ShareBottomSheet extends BottomSheetDialogFragment {

    private static final String TAG = ShareBottomSheet.class.getSimpleName();

    private RecyclerView actionsRV;

    private List<ShareOptions> optionsList = new ArrayList<>();
    private ShareAdapter shareAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.share_actions_layout, container, false);

        init(v);

        actionsRV.addOnItemTouchListener(new RecyclerItemTouchListener(getActivity(), actionsRV, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch (position){
                    case 0:
                        Log.d(TAG, "Whatsapp CLicked");
                        break;
                    case 1:
                        Log.d(TAG, "Facebook CLicked");
                        break;
                    case 2:
                        Log.d(TAG, "GMail CLicked");
                        break;
                }
            }
        }));

        return v;
    }

    private void init(View v) {
        actionsRV = v.findViewById(R.id.actionsRV);

        actionsRV.setHasFixedSize(true);
        actionsRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        ShareOptions options = new ShareOptions(R.drawable.ic_hair_tools, "Share on Whatsapp");
        optionsList.add(options);

        ShareOptions options1 = new ShareOptions(R.drawable.ic_hair_tools, "Share on Facebook");
        optionsList.add(options1);

        ShareOptions options2 = new ShareOptions(R.drawable.ic_hair_tools, "Share with Gmail");
        optionsList.add(options2);

        shareAdapter = new ShareAdapter(getActivity(), optionsList);
        actionsRV.setAdapter(shareAdapter);

    }

}
