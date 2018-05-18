package com.jjoey.freshkutz.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by JosephJoey on 5/9/2018.
 */

@Table(name = "fresh_cuts")
public class FreshKutz extends Model {

    @Column(name = "style_id")
    public String freshKutzId;

    @Column(name = "coverImage")
    public String coverImage;

    @Column(name = "frontImage")
    public String frontImage;

    @Column(name = "sideImage")
    public String sideImage;

    @Column(name = "backImage")
    public String backImage;

    @Column(name = "title_style")
    public String title;

    @Column(name = "date")
    public String date;

    @Column(name = "salonOR_CityName")
    public String salon_City;

    @Column(name = "desc")
    public String description;

    @Column(name = "stylist_name")
    public String stylist_name;

    public FreshKutz() {
    }

    public FreshKutz(String coverImage, String frontImage, String sideImage, String backImage, String title, String date, String salon_City, String description, String stylist_name) {
        this.coverImage = coverImage;
        this.frontImage = frontImage;
        this.sideImage = sideImage;
        this.backImage = backImage;
        this.title = title;
        this.date = date;
        this.salon_City = salon_City;
        this.description = description;
        this.stylist_name = stylist_name;
    }

    public FreshKutz(String freshKutzId, String coverImage, String frontImage, String sideImage, String backImage, String title, String date, String salon_City, String description, String stylist_name) {
        this.freshKutzId = freshKutzId;
        this.coverImage = coverImage;
        this.frontImage = frontImage;
        this.sideImage = sideImage;
        this.backImage = backImage;
        this.title = title;
        this.date = date;
        this.salon_City = salon_City;
        this.description = description;
        this.stylist_name = stylist_name;
    }

    public String getFreshKutzId() {
        return freshKutzId;
    }

    public void setFreshKutzId(String freshKutzId) {
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

    public String getStylist_name() {
        return stylist_name;
    }

    public void setStylist_name(String stylist_name) {
        this.stylist_name = stylist_name;
    }

}
