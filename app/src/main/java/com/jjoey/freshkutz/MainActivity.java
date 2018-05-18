package com.jjoey.freshkutz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.jjoey.freshkutz.adapters.StylesAdapter;
import com.jjoey.freshkutz.models.FreshKutz;
import com.jjoey.freshkutz.utils.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView helpTV, emptyState;
    private EmptyRecyclerView savedHairStyleRV;

    private StylesAdapter adapter;
    private List<FreshKutz> list = new ArrayList<>();

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setSupportActionBar(toolbar);

        savedHairStyleRV.setEmptyView(emptyState);

        helpTV.setOnClickListener( v -> {
            // TODO: 5/9/2018 show help to use app
        });

        setUpLists();

    }

    private void setUpLists() {
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        savedHairStyleRV.setLayoutManager(llm);
        savedHairStyleRV.setHasFixedSize(true);

        list = getList();

        adapter = new StylesAdapter(this, list);
        savedHairStyleRV.setAdapter(adapter);
        savedHairStyleRV.setItemAnimator(new DefaultItemAnimator());
        adapter.notifyDataSetChanged();

    }

    public List<FreshKutz> getList() {
        return new Select()
                .from(FreshKutz.class)
                .orderBy("id ASC")
                .execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_style:
                startStyleActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startStyleActivity() {
        Intent styleIntent = new Intent(MainActivity.this, AddStyleActivity.class);
        startActivity(styleIntent);
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        helpTV = findViewById(R.id.helpTV);
        emptyState = findViewById(R.id.emptyState);
        savedHairStyleRV = findViewById(R.id.savedHairStyleRV);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showExitDialog();
    }

    private void showExitDialog() {
        new MaterialStyledDialog.Builder(this)
                .setTitle("Exit App")
                .setDescription("Are you Sure You Want to Close this Application?")
                .setIcon(R.drawable.ic_hair_tools)
                .show();
    }

}
