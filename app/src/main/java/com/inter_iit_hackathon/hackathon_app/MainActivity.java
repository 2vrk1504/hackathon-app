package com.inter_iit_hackathon.hackathon_app;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.inter_iit_hackathon.hackathon_app.adapters.MainActivityTabsPagerAdapter;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TabLayout tabs;
    ViewPager viewPager;
    MainActivityTabsPagerAdapter mainActivityTabsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityTabsPagerAdapter = new MainActivityTabsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(mainActivityTabsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        setUpTabIcons();

    }

    private void setUpTabIcons() {
        for(int i = 0; i < tabs.getTabCount(); i++)
            tabs.getTabAt(i).setIcon(MainActivityTabsPagerAdapter.TAB_ICONS[i]);
    }
}