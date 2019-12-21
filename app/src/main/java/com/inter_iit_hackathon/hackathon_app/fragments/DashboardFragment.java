package com.inter_iit_hackathon.hackathon_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inter_iit_hackathon.hackathon_app.GetProjectsQuery;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.activities.MainActivity;
import com.inter_iit_hackathon.hackathon_app.adapters.CardStackAdapter;
import com.inter_iit_hackathon.hackathon_app.classes.DashboardData;
import com.inter_iit_hackathon.hackathon_app.graphql_client.MyClient;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class DashboardFragment extends Fragment {

    private CardStackView cardStackView;
    private FloatingActionButton fab_thumbs_up,fab_thumbs_down;
    private ArrayList<DashboardData> list = new ArrayList<>();
    private CardStackLayoutManager cardStackLayoutManager;


    public static DashboardFragment newInstance() {
       return new DashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        cardStackView = root.findViewById(R.id.cardstackview);
        fab_thumbs_down = root.findViewById(R.id.fab_down);
        fab_thumbs_up = root.findViewById(R.id.fab_up);
        cardStackLayoutManager = new CardStackLayoutManager(getContext());
        cardStackView.setLayoutManager(cardStackLayoutManager);
        final CardStackAdapter c = new CardStackAdapter(list, getContext());
        cardStackView.setAdapter(c);
        MyClient.getClient().query(GetProjectsQuery.builder().build()).enqueue(new ApolloCall.Callback<GetProjectsQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetProjectsQuery.Data> response) {
                if(response.data()!=null) {
//                    getActivity().runOnUiThread(()

                    List<GetProjectsQuery.Project> project = response.data().projects();
                    for (int i = 0; i < project.size(); i++) {
                        list.add(new DashboardData(project.get(i).photo(), project.get(i).title(), project.get(i).description(), project.get(i).updatedAt(), project.get(i).author().name()));
//                    }
//                    c.notifyDataSetChanged();
                    }
                }
                else{
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(),"Sorry, data not available",Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                getActivity().runOnUiThread(() -> Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show());
            }
        });

        fab_thumbs_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder().setDirection(Direction.Left).build();
                cardStackLayoutManager.setSwipeAnimationSetting(setting);
                cardStackView.swipe();
            }
        });
        fab_thumbs_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder().setDirection(Direction.Right).build();
                cardStackLayoutManager.setSwipeAnimationSetting(setting);
                cardStackView.swipe();
            }
        });
        return root;
    }
}