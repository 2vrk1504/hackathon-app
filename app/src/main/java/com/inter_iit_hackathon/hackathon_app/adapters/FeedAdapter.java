package com.inter_iit_hackathon.hackathon_app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.classes.FeedClass;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private ArrayList<FeedClass> feed;

    public FeedAdapter(ArrayList<FeedClass> feed) {
        this.feed = feed;
    }

    @NonNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.feed_card, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {
        holder.roadName.setText(feed.get(position).getStreet_name());
        holder.govtUpdate.setText(feed.get(position).getStreet_update());
        holder.issue.setText(feed.get(position).getStreet_issue());
        holder.postedOn.setText(feed.get(position).getStreet_upload_date());
        holder.profileName.setText(feed.get(position).getProfile_name());
        Glide.with(holder.profilePic).load(feed.get(position).getProfile_url()).into(holder.profilePic);
        Glide.with(holder.streetPic).load(feed.get(position).getStreet_url()).into(holder.streetPic);
    }

    @Override
    public int getItemCount() {
        return feed.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic;
        ImageView streetPic;
        TextView roadName;
        TextView profileName;
        TextView postedOn;
        TextView issue;
        TextView govtUpdate;

        public ViewHolder(View itemView) {
            super(itemView);
            this.profilePic = itemView.findViewById(R.id.profile_pic);
            this.roadName = itemView.findViewById(R.id.street_name);
            this.postedOn = itemView.findViewById(R.id.upload_date);
            this.profileName = itemView.findViewById(R.id.profile_name);
            this.issue = itemView.findViewById(R.id.issue);
            this.streetPic = itemView.findViewById(R.id.street_image);
            this.govtUpdate = itemView.findViewById(R.id.government_update);
        }

    }
}
