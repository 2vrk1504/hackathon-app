package com.inter_iit_hackathon.hackathon_app.classes;

public class DashboardData {
    public String id;
    private String image;
    private String road_name;
    private String description;
    private String postedOn;
    private String putUserName;
    public String getImage() {
        return image;
    }

    public DashboardData(String id, String image, String road_name, String description, String postedOn, String putUserName) {
        this.id = id;
        this.image = image;
        this.road_name = road_name;
        this.description = description;
        this.postedOn = postedOn;
        this.putUserName = putUserName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRoad_name() {
        return road_name;
    }

    public void setRoad_name(String road_name) {
        this.road_name = road_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(String postedOn) {
        this.postedOn = postedOn;
    }

    public String getPutUserName() {
        return putUserName;
    }

    public void setPutUserName(String putUserName) {
        this.putUserName = putUserName;
    }
}
