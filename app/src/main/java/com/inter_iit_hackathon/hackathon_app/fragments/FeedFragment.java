package com.inter_iit_hackathon.hackathon_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollographql.apollo.api.Query;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.adapters.FeedAdapter;
import com.inter_iit_hackathon.hackathon_app.classes.FeedClass;
import com.inter_iit_hackathon.hackathon_app.graphql_client.MyClient;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class FeedFragment extends Fragment {

    RecyclerView rview;

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
        rview = root.findViewById(R.id.recycler);
        rview.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<FeedClass> f = new ArrayList<FeedClass>();
//        MyClient.getClient().query().build();
        FeedAdapter adapter = new FeedAdapter(f);
        rview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return root;
    }
}