package com.inter_iit_hackathon.hackathon_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.adapters.CardStackAdapter;
import com.inter_iit_hackathon.hackathon_app.classes.DashboardData;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.ArrayList;

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
        list.add(new DashboardData("https://source.unsplash.com/user/erondu/900x1600", "Sardar  Patel Road", "Very bad condition, pls halp", "22/12/1999", "LuckyMark123"));
        list.add(new DashboardData("https://source.unsplash.com/user/erondu/900x1600", "Sardar  Patel Road", "Very bad condition, pls halp", "22/12/1999", "LuckyMark123"));
        list.add(new DashboardData("https://source.unsplash.com/user/erondu/900x1600", "Sardar  Patel Road", "Very bad condition, pls halp", "22/12/1999", "LuckyMark123"));
        list.add(new DashboardData("https://source.unsplash.com/user/erondu/900x1600", "Sardar  Patel Road", "Very bad condition, pls halp", "22/12/1999","LuckyMark123"));
        list.add(new DashboardData("https://source.unsplash.com/user/erondu/900x1600", "Sardar  Patel Road", "Very bad condition, pls halp", "22/12/1999", "LuckyMark123"));
        final CardStackAdapter c = new CardStackAdapter(list, getContext());
        cardStackView.setAdapter(c);
        c.notifyDataSetChanged();
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