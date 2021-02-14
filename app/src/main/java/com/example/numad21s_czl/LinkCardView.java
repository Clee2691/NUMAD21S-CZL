package com.example.numad21s_czl;

import android.widget.ImageView;
import android.widget.TextView;

public class LinkCardView {
    private int linkImage;
    private String linkName;
    private String linkTarget;

    public LinkCardView(int linkImage, String linkName, String linkTarget) {
        this.linkImage = linkImage;
        this.linkName = linkName;
        this.linkTarget = linkTarget;
    }

    public int getImage() {
        return this.linkImage;
    }
    public String getLinkName() {
        return this.linkName;
    }
    public String getLinkTarget() {
        return this.linkTarget;
    }
}
