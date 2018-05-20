package com.jjoey.freshkutz;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.jjoey.freshkutz.fragments.InfoFragment;

public class HelpActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView backImg;

    private FloatingActionButton infoFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        init();
        setSupportActionBar(toolbar);

        backImg.setOnClickListener( v -> {
            startActivity(new Intent(HelpActivity.this, MainActivity.class));
        });

        infoFAB.setOnClickListener( v -> {
            InfoFragment infoFragment = new InfoFragment();
            infoFragment.show(getFragmentManager(), "InfoFragment");
        });

    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        backImg = findViewById(R.id.backImg);
        infoFAB = findViewById(R.id.infoFAB);
    }
}
