package com.jjoey.freshkutz.models;

/**
 * Created by JosephJoey on 5/20/2018.
 */

public class ShareOptions {

    private int icon;
    private String text;

    public ShareOptions() {
    }

    public ShareOptions(int icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
