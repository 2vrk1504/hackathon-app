package com.inter_iit_hackathon.hackathon_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.adapters.MainActivityTabsPagerAdapter;
import com.inter_iit_hackathon.hackathon_app.fragments.interfaces.FragmentLifecycle;
import com.inter_iit_hackathon.hackathon_app.helpers.SessionManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TabLayout tabs;
    ViewPager viewPager;
    MainActivityTabsPagerAdapter mainActivityTabsPagerAdapter;

    SessionManager sess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sess = new SessionManager(this);
        Log.d("Vallabh", "yoyoyo");

        if(!sess.isLoggedIn()){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }


        mainActivityTabsPagerAdapter = new MainActivityTabsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(mainActivityTabsPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int currPosition = 0;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                FragmentLifecycle newFragment = (FragmentLifecycle) ((FragmentPagerAdapter) viewPager.getAdapter()).getItem(position);
                FragmentLifecycle oldFragment = (FragmentLifecycle) ((FragmentPagerAdapter) viewPager.getAdapter()).getItem(currPosition);
                newFragment.onResumeFragment();
                oldFragment.onPauseFragment();
                currPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        setUpTabIcons();
    }

    private void setUpTabIcons() {
        for(int i = 0; i < tabs.getTabCount(); i++)
            tabs.getTabAt(i).setIcon(MainActivityTabsPagerAdapter.TAB_ICONS[i]);
    }
}