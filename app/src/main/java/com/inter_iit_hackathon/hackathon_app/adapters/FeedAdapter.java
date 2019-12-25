package com.inter_iit_hackathon.hackathon_app.adapters;

import android.graphics.Color;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {


    private static final String NOT_NOTICED = "NOT_NOTICED";
    private static final String COMPLETE = "COMPLETE";
    private static final String IN_PROGRESS = "IN_PROGRESS";

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
        holder.issue.setText(feed.get(position).getStreet_issue());


        String msg = "In Progress";
        int color = Color.rgb(245, 212, 66);
        if(feed.get(position).getStreet_update().equals(NOT_NOTICED)) {
            msg = "Not Noticed";
            color = Color.rgb(153, 0, 0);
        }
        else if(feed.get(position).getStreet_update().equals(COMPLETE)){
            msg = "Complete";
            color = Color.rgb(0, 153, 0);
        }

        holder.govtUpdate.setText(msg);
        holder.govtUpdate.setTextColor(color);


        holder.profileName.setText(feed.get(position).getProfile_name());
        Glide.with(holder.profilePic).load(feed.get(position).getProfile_url()).into(holder.profilePic);

        String urlConst = "https://res.cloudinary.com/saarang-2020/image/upload/";
        String act = feed.get(position).getStreet_url().substring(urlConst.length()-1);

        Glide.with(holder.streetPic).load(urlConst + "a_-90/" + act).into(holder.streetPic);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String time = "";
        try {
            Date d = sdf.parse(feed.get(position).getStreet_upload_date());
            time = output.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.postedOn.setText(time);
        holder.likes.setText(feed.get(position).likes + "");
        holder.dislikes.setText(feed.get(position).dislikes + "");
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
        TextView likes;
        TextView dislikes;

        public ViewHolder(View itemView) {
            super(itemView);
            this.profilePic = itemView.findViewById(R.id.profile_pic);
            this.roadName = itemView.findViewById(R.id.street_name);
            this.postedOn = itemView.findViewById(R.id.upload_date);
            this.profileName = itemView.findViewById(R.id.profile_name);
            this.issue = itemView.findViewById(R.id.issue);
            this.streetPic = itemView.findViewById(R.id.street_image);
            this.govtUpdate = itemView.findViewById(R.id.government_update);
            this.likes = itemView.findViewById(R.id.likes);
            this.dislikes = itemView.findViewById(R.id.dislikes);
        }

    }
}
