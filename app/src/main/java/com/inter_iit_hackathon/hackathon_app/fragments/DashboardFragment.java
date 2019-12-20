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
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class DashboardFragment extends Fragment {

    CardStackView cardStackView;
    FloatingActionButton fab_thumbs_up,fab_thumbs_down;
    ArrayList<String> list = new ArrayList<>();

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
        cardStackView.setLayoutManager(new CardStackLayoutManager(getContext()));
        list.add("https://source.unsplash.com/user/erondu/900x1600");
        list.add("https://source.unsplash.com/user/erondu/900x1600");
        CardStackAdapter c = new CardStackAdapter(list, getContext());
        cardStackView.setAdapter(c);
        c.notifyDataSetChanged();
        return root;
    }
}