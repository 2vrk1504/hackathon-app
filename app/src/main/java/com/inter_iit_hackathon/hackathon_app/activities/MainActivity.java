package com.inter_iit_hackathon.hackathon_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.adapters.MainActivityTabsPagerAdapter;
import com.inter_iit_hackathon.hackathon_app.helpers.SessionManager;

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
        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        setUpTabIcons();

    }

    private void setUpTabIcons() {
        for(int i = 0; i < tabs.getTabCount(); i++)
            tabs.getTabAt(i).setIcon(MainActivityTabsPagerAdapter.TAB_ICONS[i]);
    }
}