package com.inter_iit_hackathon.hackathon_app.classes;

public class DashboardData {
    private String image;
    private String road_name;
    private String description;

    public String getImage() {
        return image;
    }

    public DashboardData(String image, String road_name, String description) {
        this.image = image;
        this.road_name = road_name;
        this.description = description;
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
}
