package com.inter_iit_hackathon.hackathon_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.classes.DashboardData;

import java.util.ArrayList;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {
    private ArrayList<DashboardData> data;
    private Context context;


    public CardStackAdapter(ArrayList<DashboardData> i, Context c) {
        this.data = i;
        this.context = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View item = layoutInflater.inflate(R.layout.street_card, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.imageView).load(data.get(position).getImage()).placeholder(R.drawable.ic_launcher_foreground).into(holder.imageView);
        holder.roadName.setText(data.get(position).getRoad_name());
        holder.postedOn.setText(data.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView roadName;
        TextView postedOn;

        private static int shortAnimationDuration = -1;


        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_view);
            this.roadName = itemView.findViewById(R.id.road_name);
            this.postedOn = itemView.findViewById(R.id.postedOn);

        }

    }

}
