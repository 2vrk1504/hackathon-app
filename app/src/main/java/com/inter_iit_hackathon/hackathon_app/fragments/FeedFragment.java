package com.inter_iit_hackathon.hackathon_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.inter_iit_hackathon.hackathon_app.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class FeedFragment extends Fragment {

    public static FeedFragment newInstance() {
       return new FeedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_feed, container, false);
        return root;
    }
}