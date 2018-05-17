package com.jjoey.freshkutz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class FullDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView backImg;
    private TextView editTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_details);

        init();
        setSupportActionBar(toolbar);

        backImg.setOnClickListener( v -> {
            startActivity(new Intent(FullDetailsActivity.this, MainActivity.class));
        });

        editTV.setOnClickListener( v -> {
            startActivity(new Intent(FullDetailsActivity.this, EditActivity.class));
        });

    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        editTV = findViewById(R.id.editTV);
        backImg = findViewById(R.id.backImg);
    }
}
