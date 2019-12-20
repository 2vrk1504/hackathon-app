package com.inter_iit_hackathon.hackathon_app.adapters;

import android.animation.Animator;
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

import java.util.ArrayList;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {
    private ArrayList<String> image_list;
    private Context context;


    public CardStackAdapter(ArrayList<String> i,Context c){
        this.image_list = i;
        this.context = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View item = layoutInflater.inflate(R.layout.street_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.imageView).load(image_list.get(position)).placeholder(R.drawable.ic_launcher_foreground).into(holder.imageView);
        holder.roadName.setText("Sardar Patel Road");
        holder.postedOn.setText("21st Dec 2019");
        holder.postedBy.setText("Anbudan Siva");
    }

    @Override
    public int getItemCount() {
        return image_list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        RelativeLayout infoGroup;
        TextView roadName;
        TextView postedOn;
        TextView postedBy;
        ImageView close;
        ImageView moreInfo;

        private static int shortAnimationDuration = -1;


        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_view);
            this.infoGroup = itemView.findViewById(R.id.info_group);
            this.roadName = itemView.findViewById(R.id.road_name);
            this.postedBy = itemView.findViewById(R.id.postedBy);
            this.postedOn = itemView.findViewById(R.id.postedOn);
            this.close = itemView.findViewById(R.id.close);
            this.moreInfo = itemView.findViewById(R.id.more_info);

            if(shortAnimationDuration == -1)
                shortAnimationDuration = itemView.getContext().getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

            moreInfo.setOnClickListener(view-> showInfoGroup());
            close.setOnClickListener(view-> hideInfoGroup());
        }
        private void showInfoGroup() {
            infoGroup.setAlpha(0f);
            infoGroup.setVisibility(View.VISIBLE);

            moreInfo.setVisibility(View.GONE);
            infoGroup.animate()
                    .alpha(0.9f)
                    .setDuration(shortAnimationDuration)
                    .setListener(null);
        }
        private void hideInfoGroup() {
            infoGroup.setAlpha(0.9f);
            infoGroup.setVisibility(View.VISIBLE);

            moreInfo.setVisibility(View.GONE);
            infoGroup.animate()
                    .alpha(0f)
                    .setDuration(shortAnimationDuration)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {

                        }
                        @Override
                        public void onAnimationEnd(Animator animator) {
                            infoGroup.setVisibility(View.GONE);
                            moreInfo.setVisibility(View.VISIBLE);
                        }
                        @Override
                        public void onAnimationCancel(Animator animator) {

                        }
                        @Override
                        public void onAnimationRepeat(Animator animator) {

                        }
                    });
        }
    }

}
