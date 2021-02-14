package com.example.numad21s_czl;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class LinkRecyclerViewHolder extends RecyclerView.ViewHolder {
    public ImageView linkImage;
    public TextView linkName;
    public TextView linkTarget;

    public LinkRecyclerViewHolder(View view) {
        super(view);
        this.linkImage = view.findViewById(R.id.iv_link);
        this.linkName = view.findViewById(R.id.tv_link_name);
        this.linkTarget = view.findViewById(R.id.tv_link_target);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                Uri uri = Uri.parse((String) linkTarget.getText());
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
    }

    /*
     * Sets the data for the current card view to display in the RecyclerView
     * @param cardView The current card whose data we want to use
     */
    public void setViewData(LinkCardView cardView) {
        this.linkImage.setImageResource(cardView.getImage());
        this.linkName.setText(cardView.getLinkName());
        this.linkTarget.setText(cardView.getLinkTarget());
    }
}
