package com.example.numad21s_czl;

import java.util.ArrayList;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class LinkRecyclerAdapter extends RecyclerView.Adapter<LinkRecyclerViewHolder> {
    private ArrayList<LinkCardView> cardListArray;
    public LinkRecyclerAdapter(ArrayList<LinkCardView> cardList) {
        this.cardListArray = cardList;
    }
    @Override
    public LinkRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.link_card_layout, parent, false);
        return new LinkRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LinkRecyclerViewHolder holder, int pos) {
        LinkCardView currCard = cardListArray.get(pos);
        // Set the card data from the view holder instead
        holder.setViewData(currCard);
    }
    @Override
    public int getItemCount() {
        return cardListArray.size();
    }
}
