package com.inter_iit_hackathon.hackathon_app.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.fragments.DashboardFragment;
import com.inter_iit_hackathon.hackathon_app.fragments.FeedFragment;
import com.inter_iit_hackathon.hackathon_app.fragments.MyMapFragment;
import com.inter_iit_hackathon.hackathon_app.fragments.ProfileFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class MainActivityTabsPagerAdapter extends FragmentPagerAdapter {

    public static final int[] TAB_ICONS = new int[]{R.drawable.ic_profile, R.drawable.ic_dashboard, R.drawable.ic_map, R.drawable.ic_feedback};
    public static final int PROFILE_FRAGMENT_POS = 0;
    public static final int DASHBOARD_FRAGMENT_POS = 1;
    public static final int MAP_FRAGMENT_POS = 2;
    public static final int FEEDBACK_FRAGMENT_POS = 3;
    private final Context mContext;
    private Fragment[] fragments;

    public MainActivityTabsPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        fragments = new Fragment[TAB_ICONS.length];
        fragments[PROFILE_FRAGMENT_POS] = ProfileFragment.newInstance();
        fragments[DASHBOARD_FRAGMENT_POS] = DashboardFragment.newInstance();
        fragments[MAP_FRAGMENT_POS] = MyMapFragment.newInstance();
        fragments[FEEDBACK_FRAGMENT_POS] = FeedFragment.newInstance();
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a ProfileFragment (defined as a static inner class below).
        return fragments[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return TAB_ICONS.length;
    }
}