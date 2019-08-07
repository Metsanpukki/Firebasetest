package com.jussi.firebasetest;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private int[] tabIcons = {
            R.drawable.ic_tab_upstairs,
            R.drawable.ic_tab_upstairs,
            R.drawable.ic_tab_upstairs,
            R.drawable.ic_tab_upstairs
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new UpstairsFragment(), "Upstairs");
        viewPagerAdapter.addFragment(new DownstairsFragment(), "Downstairs");
        viewPagerAdapter.addFragment(new StreamFragment(),"RTSP");

        viewPager.setOffscreenPageLimit(1); //fragments cache
        viewPager.setAdapter(viewPagerAdapter);



        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Upstairs");
        tabOne.setGravity(Gravity.CENTER);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_upstairs, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Downstairs");
        tabTwo.setGravity(Gravity.CENTER);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_downstairs, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("RTSP!");
        tabThree.setGravity(Gravity.CENTER);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_downstairs, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);


    }
}
