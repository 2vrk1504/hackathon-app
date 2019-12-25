package com.inter_iit_hackathon.hackathon_app.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Query;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.inter_iit_hackathon.hackathon_app.GetFeedQuery;
import com.inter_iit_hackathon.hackathon_app.GetPostsQuery;
import com.inter_iit_hackathon.hackathon_app.GetProjectsQuery;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.SignUpMutation;
import com.inter_iit_hackathon.hackathon_app.activities.MainActivity;
import com.inter_iit_hackathon.hackathon_app.activities.SignUpActivity;
import com.inter_iit_hackathon.hackathon_app.adapters.FeedAdapter;
import com.inter_iit_hackathon.hackathon_app.classes.FeedClass;
import com.inter_iit_hackathon.hackathon_app.fragments.interfaces.FragmentLifecycle;
import com.inter_iit_hackathon.hackathon_app.graphql_client.MyClient;
import com.inter_iit_hackathon.hackathon_app.helpers.SessionManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class FeedFragment extends Fragment implements FragmentLifecycle {

    RecyclerView rview;
    FloatingActionButton floatingActionButton;
    Boolean his = Boolean.TRUE;
    ArrayList<FeedClass> f;
    SessionManager sessionManager;
    TextView noNew;


    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_feed, container, false);
        rview = root.findViewById(R.id.recycler);
        floatingActionButton = root.findViewById(R.id.change_fab);
        rview.setLayoutManager(new LinearLayoutManager(getContext()));
        f = new ArrayList<FeedClass>();
        FeedAdapter adapter = new FeedAdapter(f);
        rview.setAdapter(adapter);
        noNew = root.findViewById(R.id.no_new_posts1);
        adapter.notifyDataSetChanged();

        MyClient.getClient(sessionManager.getToken()).query(GetFeedQuery.builder().build()).enqueue(new ApolloCall.Callback<GetFeedQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetFeedQuery.Data> response) {

                if(response.data()!=null){

//                    getActivity().runOnUiThread(()-> Toast.makeText(getContext(),response.data().toString(),Toast.LENGTH_SHORT).show());

                    List<GetFeedQuery.GetFeed> getFeed = response.data().getFeed();
                    for(int i=0; i < getFeed.size(); i++){
                        f.add(new FeedClass(
                                "https://source.unsplash.com/random/800x800",
                                getFeed.get(i).author().name(),
                                getFeed.get(i).photo(),
                                getFeed.get(i).title(),
                                getFeed.get(i).content(),
                                getFeed.get(i).status().toString(),
                                getFeed.get(i).updatedAt(),
                                getFeed.get(i).numberOfSerious(),
                                getFeed.get(i).numberOfTrivial()
                            ));
                    }
                    getActivity().runOnUiThread(() -> {
                        noNew.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    });
                }
                else{

                    getActivity().runOnUiThread(() -> noNew.setVisibility(View.VISIBLE));

                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                getActivity().runOnUiThread(()->{
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
                });
            }
        });

        floatingActionButton.setOnClickListener(view -> {


        });
        return root;
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }
}