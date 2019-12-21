package com.inter_iit_hackathon.hackathon_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.adapters.FeedAdapter;
import com.inter_iit_hackathon.hackathon_app.classes.FeedClass;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileFragment extends Fragment {

    public static ProfileFragment newInstance() {
       return new ProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        return root;
    }
}