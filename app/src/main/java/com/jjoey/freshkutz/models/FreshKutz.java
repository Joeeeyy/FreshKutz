package com.jjoey.freshkutz.models;

/**
 * Created by JosephJoey on 5/9/2018.
 */

public class FreshKutz {

    private int freshKutzId;
    private String coverImage;
    private String frontImage;
    private String sideImage;
    private String backImage;
    private String title;
    private String date;
    private String salon_City;
    private String description;

    public FreshKutz() {
    }

    public FreshKutz(String coverImage, String frontImage, String sideImage, String backImage, String title, String date, String salon_City, String description) {
        this.coverImage = coverImage;
        this.frontImage = frontImage;
        this.sideImage = sideImage;
        this.backImage = backImage;
        this.title = title;
        this.date = date;
        this.salon_City = salon_City;
        this.description = description;
    }

    public FreshKutz(int freshKutzId, String coverImage, String frontImage, String sideImage, String backImage, String title, String date, String salon_City, String description) {
        this.freshKutzId = freshKutzId;
        this.coverImage = coverImage;
        this.frontImage = frontImage;
        this.sideImage = sideImage;
        this.backImage = backImage;
        this.title = title;
        this.date = date;
        this.salon_City = salon_City;
        this.description = description;
    }

    public int getFreshKutzId() {
        return freshKutzId;
    }

    public void setFreshKutzId(int freshKutzId) {
        this.freshKutzId = freshKutzId;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getFrontImage() {
        return frontImage;
    }

    public void setFrontImage(String frontImage) {
        this.frontImage = frontImage;
    }

    public String getSideImage() {
        return sideImage;
    }

    public void setSideImage(String sideImage) {
        this.sideImage = sideImage;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSalon_City() {
        return salon_City;
    }

    public void setSalon_City(String salon_City) {
        this.salon_City = salon_City;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
