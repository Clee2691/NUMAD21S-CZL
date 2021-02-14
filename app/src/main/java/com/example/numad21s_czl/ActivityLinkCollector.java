package com.example.numad21s_czl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class ActivityLinkCollector extends AppCompatActivity {
    private ArrayList<LinkCardView> cardListArray = new ArrayList<>();
    private ArrayList<String> cardUUIDs;

    private static final String STATE_CARD = "cardItem";
    private static final String NUM_ITEMS = "numItems";

    // Recycler view setup
    private RecyclerView recyclerView;
    private LinkRecyclerAdapter lRAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        if (savedInstanceState != null) {
            restoreData(savedInstanceState);
        }
        createRecycleView();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        cardUUIDs = new ArrayList<String>();

        for (LinkCardView cardItem : cardListArray) {
            savedInstanceState.putInt(cardItem.getCardUUID() + "_linkImage",
                    cardItem.getImage());
            savedInstanceState.putString(cardItem.getCardUUID() + "_linkName",
                    cardItem.getLinkName());
            savedInstanceState.putString(cardItem.getCardUUID() + "_linkTarget",
                    cardItem.getLinkTarget());
            cardUUIDs.add(cardItem.getCardUUID());
        }
        savedInstanceState.putStringArrayList("cardUUIDArray", cardUUIDs);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void restoreData(Bundle savedInstanceState) {
        ArrayList<String> cardIDs = savedInstanceState.getStringArrayList("cardUUIDArray");
        if (cardListArray.size() == 0 || cardListArray == null) {
            for (String cardUUID: cardIDs) {
                int cardImage = savedInstanceState.getInt(cardUUID + "_linkImage");
                String cardName = savedInstanceState.getString(cardUUID + "_linkName");
                String cardTarget = savedInstanceState.getString(cardUUID + "_linkTarget");
                LinkCardView aCard = new LinkCardView(cardImage, cardName, cardTarget);
                cardListArray.add(aCard);
            }
        }
    }

    public void clickedAddLink(View view) {
        if (view.getId() == R.id.btn_float_add_link) {
            int pos = 0;
            addCard(pos);
        }
    }

    protected void createRecycleView() {
        rvLayoutManager = new LinearLayoutManager(this);
        lRAdapter = new LinkRecyclerAdapter(cardListArray);
        recyclerView = findViewById(R.id.recycle_view_links);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(lRAdapter);
        recyclerView.setLayoutManager(rvLayoutManager);
    }

    public void addCard(int pos) {
        cardListArray.add(pos,
                new LinkCardView(R.drawable.ic_launcher_czl_background,
                        "Test 1", "google.com"));

        lRAdapter.notifyItemInserted(pos);
    }

}