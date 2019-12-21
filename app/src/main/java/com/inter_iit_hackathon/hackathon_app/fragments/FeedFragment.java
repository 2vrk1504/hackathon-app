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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.inter_iit_hackathon.hackathon_app.GetPostsQuery;
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


/**
 * A placeholder fragment containing a simple view.
 */
public class FeedFragment extends Fragment {

    RecyclerView rview;
    FloatingActionButton floatingActionButton;
    Boolean his = Boolean.TRUE;
    ArrayList<FeedClass> f;
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
        floatingActionButton = root.findViewById(R.id.change_fab);
        rview.setLayoutManager(new LinearLayoutManager(getContext()));
        f = new ArrayList<FeedClass>();
        FeedAdapter adapter = new FeedAdapter(f);
        rview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        MyClient.getClient().query(GetPostsQuery.builder().build()).enqueue(new ApolloCall.Callback<GetPostsQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetPostsQuery.Data> response) {
                if(response.data()!=null){
                    getActivity().runOnUiThread(()-> Toast.makeText(getContext(),response.data().toString()
                            ,Toast.LENGTH_SHORT).show());

                    GetPostsQuery.GetPosts getPosts = response.data().getPosts();
                    f.add(new FeedClass("https://source.unsplash.com/random/800x800",getPosts.author().name(),getPosts.photo(),getPosts.title(),getPosts.content(),getPosts.status().rawValue(),getPosts.createdAt()));
//                    adapter.notifyDataSetChanged();
                }
                else{

                    getActivity().runOnUiThread(()-> Toast.makeText(getContext(),"Null Bruh",Toast.LENGTH_SHORT).show());

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
}