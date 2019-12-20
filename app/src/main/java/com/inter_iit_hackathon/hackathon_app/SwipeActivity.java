package com.inter_iit_hackathon.hackathon_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inter_iit_hackathon.hackathon_app.adapters.CardStackAdapter;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;

import java.util.ArrayList;

public class SwipeActivity extends AppCompatActivity {
    CardStackView cardStackView;
    FloatingActionButton fab_thumbs_up,fab_thumbs_down;
    ArrayList<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        cardStackView = findViewById(R.id.cardstackview);
        fab_thumbs_down = findViewById(R.id.fab_down);
        fab_thumbs_up = findViewById(R.id.fab_up);


        cardStackView.setLayoutManager(new CardStackLayoutManager(getApplicationContext()));
        list.add("https://source.unsplash.com/user/erondu/900x1600");
        list.add("https://source.unsplash.com/user/erondu/900x1600");
        CardStackAdapter c = new CardStackAdapter(list,getApplicationContext());
        cardStackView.setAdapter(c);
        c.notifyDataSetChanged();
    }
}
