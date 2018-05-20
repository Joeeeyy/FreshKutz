package com.jjoey.freshkutz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jjoey.freshkutz.extras.FreshApp;
import com.jjoey.freshkutz.utils.Preferences;

public class IntroSliderActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private IntroPagerAdapter pagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button skip, next;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         *  Check first time launch before setting the view
         * */
        preferences = FreshApp.getFreshInstance().getPreferences();
        if (!preferences.isFirstTimeLaunch()){
            launchHomePage();
            finish();
        }

        /**
         *  Make status bar transparent
         * */

        if (Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_intro_slider);

        viewPager = (ViewPager) findViewById(R.id.introPager);
        dotsLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        next = (Button) findViewById(R.id.nextBtn);
        skip = (Button) findViewById(R.id.skipBtn);

        /**
         *  Adding the layouts
         * */

        layouts = new int[]{ R.layout.welcome_screen1, R.layout.welcome_screen2, R.layout.welcome_screen3};

        /**
         *  Adding bottom indicator dots
         * */

        addBottomIndicators(0);

        changeStatusBarColor();
        pagerAdapter = new IntroPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchHomePage();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *  Check if viewpager is on last page
                 * */

                int currentPage = getItem(+1);
                if (currentPage < layouts.length){
                    viewPager.setCurrentItem(currentPage);
                } else {
                    launchHomePage();
                }

            }
        });

    }

    private void addBottomIndicators (int currentPage){

        dots = new TextView[layouts.length];
        int[] activeColors = getResources().getIntArray(R.array.array_pager_active);
        int[] inactiveColors = getResources().getIntArray(R.array.array_pager_inactive);
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(inactiveColors[currentPage]);

            // add to linear layout

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0 ){
            dots[currentPage].setTextColor(activeColors[currentPage]);
        }

    }

    private int getItem(int i){
        return viewPager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomIndicators(position);

            // if on the last page
            if (position == layouts.length - 1){
                next.setText(getString(R.string.done));
                skip.setVisibility(View.GONE);
            } else {
                next.setText(getString(R.string.next));
                skip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void launchHomePage() {
        preferences.setFirstTimeLaunch(false);
        startActivity(new Intent(IntroSliderActivity.this, SplashActivity.class));
        finish();
    }

    public class IntroPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public IntroPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}
