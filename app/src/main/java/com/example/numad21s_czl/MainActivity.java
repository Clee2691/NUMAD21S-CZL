package com.example.numad21s_czl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void aboutMe(View view) {
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView emailTextView = findViewById(R.id.emailTextView);
        String name = "";
        String email = "";

        switch(view.getId()) {
            case R.id.clearButton:
                break;
            case R.id.aboutButton:
                name = "Calvin Lee";
                email = "lee.calv@northeastern.edu";
                break;
        }
        nameTextView.setText(name);
        emailTextView.setText(email);
    }

    public void activityButtons(View view) {
        int currResource = view.getId();
        if (currResource == R.id.activity_click_btn) {
            Intent clickActivity = new Intent(this, ActivityClickLetters.class);
            startActivity(clickActivity);
        } else if (currResource == R.id.btn_link_collector) {
            Intent link_collect_activity = new Intent(this, ActivityLinkCollector.class);
            startActivity(link_collect_activity);
        }
    }
}