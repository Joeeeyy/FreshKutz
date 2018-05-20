package com.jjoey.freshkutz.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoey.freshkutz.R;

/**
 * Created by JosephJoey on 5/19/2018.
 */

public class InfoFragment extends DialogFragment {

    private TextView dev_email, dev_phone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_layout, container, false);

        dev_email = view.findViewById(R.id.dev_email);
        dev_phone = view.findViewById(R.id.dev_phone);

        dev_email.setAutoLinkMask(Linkify.EMAIL_ADDRESSES);
        dev_email.setLinksClickable(true);
        dev_email.setLinkTextColor(getResources().getColor(R.color.colorPrimary));

        dev_phone.setAutoLinkMask(Linkify.PHONE_NUMBERS);
        dev_phone.setLinksClickable(true);
        dev_phone.setLinkTextColor(getResources().getColor(R.color.colorPrimary));

        return view;
    }
}
