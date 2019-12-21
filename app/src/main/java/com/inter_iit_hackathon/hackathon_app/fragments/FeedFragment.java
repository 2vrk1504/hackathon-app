package com.inter_iit_hackathon.hackathon_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Query;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.snackbar.Snackbar;
import com.inter_iit_hackathon.hackathon_app.GetProjectsQuery;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.SignUpMutation;
import com.inter_iit_hackathon.hackathon_app.activities.MainActivity;
import com.inter_iit_hackathon.hackathon_app.activities.SignUpActivity;
import com.inter_iit_hackathon.hackathon_app.adapters.FeedAdapter;
import com.inter_iit_hackathon.hackathon_app.classes.FeedClass;
import com.inter_iit_hackathon.hackathon_app.graphql_client.MyClient;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

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

        FeedAdapter adapter = new FeedAdapter(f);
        rview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return root;
    }
}