package com.example.numad21s_czl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class ActivityLinkCollector extends AppCompatActivity
        implements EnterLinkDialogFragment.addLinkListener {
    private ArrayList<LinkCardView> cardListArray = new ArrayList<>();
    private ArrayList<String> cardUUIDs;

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
            for (String cardUUID : cardIDs) {
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
            DialogFragment addLinkDialog = new EnterLinkDialogFragment();
            addLinkDialog.show(getSupportFragmentManager(), "addLink");
        }
    }

    protected void createRecycleView() {
        rvLayoutManager = new LinearLayoutManager(this);
        lRAdapter = new LinkRecyclerAdapter(cardListArray);
        recyclerView = findViewById(R.id.recycle_view_links);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(lRAdapter);
        recyclerView.setLayoutManager(rvLayoutManager);

        ItemTouchHelper swipeCard = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView rview, RecyclerView.ViewHolder rViewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder vHolder, int direction) {
                View v = findViewById(R.id.link_collect_activity);
                int pos = vHolder.getLayoutPosition();
                deleteOnSwipe(v, pos);
            }
        });
        swipeCard.attachToRecyclerView(recyclerView);
    }

    public void deleteOnSwipe(View v, int pos) {
        LinkCardView deletedItem = cardListArray.get(pos);
        cardListArray.remove(pos);
        lRAdapter.notifyItemRemoved(pos);

        Snackbar.make(v, "Deleted Item", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
            @Override
            // Allow undo delete of item
            public void onClick(View v) {
                cardListArray.add(pos, deletedItem);
                lRAdapter.notifyItemInserted(pos);
            }
        }).show();

    }

    @Override
    public void addCard(String linkName, String linkTarget) {
        int pos = 0;
        View view = findViewById(R.id.link_collect_activity);
        // Strip whitespace from entered link
        String cleanTarget = linkTarget.replaceAll("\\s+", "").toLowerCase();

        // Preemptively add https to link
        if ((!cleanTarget.contains("http")) && (!cleanTarget.contains("https"))) {
            cleanTarget = "https://" + cleanTarget;
        }

        boolean validURL;
        try {
            validURL = assessURL(cleanTarget);
        } catch (MalformedURLException mE) {
            validURL = false;
        }

        if (validURL) {
            int icon;
            if (cleanTarget.contains("gmail")) {
                icon = R.drawable.ic_gmail_icon;
            } else if (cleanTarget.contains("google")) {
                icon = R.drawable.ic_google_icon;
            } else if (cleanTarget.contains("linkedin")) {
                icon = R.drawable.ic_linkedin_icon;
            } else if (cleanTarget.contains("youtube")) {
                icon = R.drawable.ic_youtube_icon;
            } else {
                icon = R.drawable.ic_default_icon;
            }
            cardListArray.add(pos,
                    new LinkCardView(icon,
                            linkName, cleanTarget));

            lRAdapter.notifyItemInserted(pos);
            Snackbar.make(view, String.format("%s created!", linkName),
                    Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(view,
                    "Link not added. Invalid format. It must have a domain (.com, .net, etc.)",
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    private boolean assessURL(@NonNull String cardUrl) throws MalformedURLException {
        URL parsedURL = new URL(cardUrl);
        // Crude way of checking if link contains a domain or not
        if (parsedURL.getHost().contains(".")) {
            return true;
        }
        return false;
    }
}