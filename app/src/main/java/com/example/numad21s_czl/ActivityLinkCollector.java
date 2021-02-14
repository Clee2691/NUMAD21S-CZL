package com.example.numad21s_czl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class ActivityLinkCollector extends AppCompatActivity {
    private ArrayList<LinkCardView> cardListArray = new ArrayList<>();

    // Recycler view setup
    private RecyclerView recyclerView;
    private LinkRecyclerAdapter lRAdapter;
    private RecyclerView.LayoutManager rvLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);
        createRecycleView();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

    }
//
//    private void saveItemsOnChange() {
//
//    }

    public void clickedAddLink(View view) {
        if (view.getId() == R.id.btn_float_add_link) {
            int pos = 0;
            addCard(pos);
        }
    }

    protected void createRecycleView(){
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