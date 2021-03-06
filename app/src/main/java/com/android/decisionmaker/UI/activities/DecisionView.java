package com.android.decisionmaker.UI.activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.decisionmaker.R;
import com.android.decisionmaker.database.models.Decision;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class DecisionView extends AppCompatActivity {

    private Decision decision;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            decision = (Decision) extras.get("Decision");
        }

        //Creates two tabs, one for results and one for the data
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Results"));
        tabLayout.addTab(tabLayout.newTab().setText("Data"));

        if(savedInstanceState != null) {
            tabLayout.selectTab(tabLayout.getTabAt(savedInstanceState.getInt("Position")));
        }

        //Adds Home Button on Header
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);

        //Based on Tab it creates the respectively fragment
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new ChartView(decision);
                    case 1:
                        return new DataView(decision);
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return tabLayout.getTabCount();
            }
        });

        //Changes Tab when the user swipe's left or right
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                tabLayout.selectTab(tabLayout.getTabAt(viewPager.getCurrentItem()));
            }
        });

        //Sets Tab in case of rotation
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("Position", tabLayout.getSelectedTabPosition());
        super.onSaveInstanceState(outState);
    }
}