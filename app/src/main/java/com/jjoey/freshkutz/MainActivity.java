package com.jjoey.freshkutz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
            startActivity(new Intent(MainActivity.this, HelpActivity.class));
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

        initSwipe();

    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                FreshKutz kutz = list.get(position);
                long id = kutz.getId();
                Log.d(TAG, "Id at Position:\t" + id);

                if (direction == ItemTouchHelper.RIGHT ) {
                    adapter.removeAtPosition(position, id);
                }

            }


            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                Paint paint = new Paint();

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View view = viewHolder.itemView;
                    float height = view.getBottom() - view.getTop();
                    float width = height / 3;

                    if (dX < 0) {
                        paint.setColor(Color.parseColor("#D32F2F"));
                        RectF rectF = new RectF(view.getRight() + dX, view.getTop(), view.getRight(), view.getBottom());
                        c.drawRect(rectF, paint);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.delete_white);
                        RectF icon_dest = new RectF((float) view.getRight() - 2 * width ,(float) view.getTop() + width,(float) view.getRight() - width,(float)view.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, paint);
                    }

                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(savedHairStyleRV);

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
        showExitDialog();
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
