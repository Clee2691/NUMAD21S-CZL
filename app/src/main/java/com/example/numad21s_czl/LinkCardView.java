package com.example.numad21s_czl;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

public class LinkCardView {
    private int linkImage;
    private String linkName;
    private String linkTarget;
    private UUID cardUUID;

    public LinkCardView(int linkImage, String linkName, String linkTarget) {
        this.linkImage = linkImage;
        this.linkName = linkName;
        this.linkTarget = linkTarget;
        this.cardUUID = UUID.randomUUID();
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

    public String getCardUUID() {
        return this.cardUUID.toString();
    }
}
