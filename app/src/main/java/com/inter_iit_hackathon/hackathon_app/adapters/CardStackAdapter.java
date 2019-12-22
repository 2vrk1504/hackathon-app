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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            holder.postedOn.setText(dateFormat.format(format.parse(data.get(position).getPostedOn())) );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.desc.setText(data.get(position).getDescription());
        holder.user.setText(data.get(position).getPutUserName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public DashboardData getItem(int id) {
        return data.get(id);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView roadName;
        TextView postedOn;
        TextView desc;
        TextView user;


        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_view);
            this.roadName = itemView.findViewById(R.id.road_name);
            this.postedOn = itemView.findViewById(R.id.postedOn);
            this.desc = itemView.findViewById(R.id.desc);
            this.user = itemView.findViewById(R.id.userPut);
        }

    }

}
