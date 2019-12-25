package com.inter_iit_hackathon.hackathon_app.classes;

import com.inter_iit_hackathon.hackathon_app.type.Status;

public class FeedClass {
    public String profile_url;
    public String profile_name;
    public String street_url;
    public String street_name;
    public String street_issue;
    public String street_update;
    public String street_upload_date;
    public int likes;
    public int dislikes;

    public FeedClass(String profile_url, String profile_name, String street_url, String street_name, String street_issue, String street_update, String street_upload_date, int likes, int dislikes) {
        this.profile_url = profile_url;
        this.profile_name = profile_name;
        this.street_url = street_url;
        this.street_name = street_name;
        this.street_issue = street_issue;
        this.street_update = street_update;
        this.street_upload_date = street_upload_date;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public String getProfile_url() {
        return profile_url;
    }

    public void setProfile_url(String profile_url) {
        this.profile_url = profile_url;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public String getStreet_url() {
        return street_url;
    }

    public void setStreet_url(String street_url) {
        this.street_url = street_url;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getStreet_issue() {
        return street_issue;
    }

    public void setStreet_issue(String street_issue) {
        this.street_issue = street_issue;
    }

    public String getStreet_update() {
        return street_update;
    }

    public void setStreet_update(String street_update) {
        this.street_update = street_update;
    }

    public String getStreet_upload_date() {
        return street_upload_date;
    }

    public void setStreet_upload_date(String street_upload_date) {
        this.street_upload_date = street_upload_date;
    }
}
