package com.example.numad21s_czl;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ActivityGithub extends AppCompatActivity {
    private final String gitHubAPI = "https://api.github.com/users/";
    private Handler uiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
    }

    public void fetchData(View view) {
        GetUserData runFetch = new GetUserData();
        new Thread(runFetch).start();
    }

    class GetUserData implements Runnable {
        @Override
        public void run() {
            EditText eTUsername = findViewById(R.id.editTextUserName);
            View view = findViewById(R.id.github_Activity);
            String userName = eTUsername.getText().toString();
            HashMap<String, String> userData = null;
            InputStream gitResponse = null;
            TextView status = findViewById(R.id.textViewStatusIndicator);

            uiHandler.post(()->{
                status.setText("Getting data from Github...Please wait...");
            });

            // Check input from user
            if ((userName.equals("")) || (userName == null)) {
                // Display a toast
                Snackbar.make(view, "Please enter a valid username!", Snackbar.LENGTH_LONG).show();
            } else {

                try {
                    URL gitURL = new URL(String.format("%s%s", gitHubAPI, userName));
                    gitResponse = getResponse(gitURL);

                } catch (IOException e) {
                    Snackbar.make(view, "Not a valid URL", Snackbar.LENGTH_LONG).show();
                }

                if (gitResponse == null) {
                    Snackbar.make(view, "An error occurred fetching data. Try again.",
                            Snackbar.LENGTH_LONG).show();
                } else {
                    try {
                        userData = parseData(gitResponse);

                    } catch (IOException e) {
                        Snackbar.make(view, "Error processing user data.",
                                Snackbar.LENGTH_LONG).show();
                    }
                }

                if (userData != null) {
                    try {
                        updateUI(userData);
                    } catch (ParseException pe) {
                        Log.d("Date", "Date could not be parsed!");
                    }

                } else {
                    Snackbar.make(view, "There is no such user! Enter a valid Github username",
                            Snackbar.LENGTH_LONG).show();
                }
            }
        }

        public InputStream getResponse(URL queryURL) {
            try {
                HttpURLConnection connUrl = (HttpURLConnection) queryURL.openConnection();
                connUrl.setRequestMethod("GET");
                connUrl.connect();
                return connUrl.getInputStream();
            } catch (IOException e) {
                return null;
            }
        }

        public HashMap<String, String> parseData(InputStream gitInputStream) throws IOException {

            List<String> fieldsToKeep = Arrays.asList("html_url", "email", "name",
                    "public_repos", "created_at");

            HashMap<String, String> userDataHash = new HashMap<String, String>();

            JsonReader readResponse = new JsonReader(new InputStreamReader(gitInputStream));
            readResponse.beginObject();

            while(readResponse.hasNext()) {
                String fieldName = readResponse.nextName();

                if (fieldsToKeep.contains(fieldName)) {
                    if (readResponse.peek() != JsonToken.NULL) {
                        userDataHash.put(fieldName, readResponse.nextString());
                    } else {
                        readResponse.skipValue();
                        userDataHash.put(fieldName, "No data");
                    }
                } else {
                    readResponse.skipValue();
                }
            }
            readResponse.endObject();

            return userDataHash;
        }

        public void updateUI(HashMap<String, String> userData) throws ParseException {
            TextView userNameOut = findViewById(R.id.textViewNameOutput);
            TextView emailOut = findViewById(R.id.textViewUserEmailOutput);
            TextView gitURLOut = findViewById(R.id.textViewGHUrlOutput);
            TextView publicRepoOut = findViewById(R.id.textViewPublicReposOutput);
            TextView dateCreated = findViewById(R.id.textViewDateCreatedOutput);

            // Formatter to parse the input date
            SimpleDateFormat parserFormat = new SimpleDateFormat("yyyy-MM-dd");
            // Formatter for the date to display
            SimpleDateFormat formatter = new SimpleDateFormat("ddMMMYYY");
            // Parsing ISO8601 format
            Date parsedDate = parserFormat.parse(userData.get("created_at"));
            // Display date parsed as string
            String formatted = formatter.format(parsedDate);

            uiHandler.post(() -> {
                userNameOut.setText(userData.get("name"));
                emailOut.setText(userData.get("email"));
                gitURLOut.setText(userData.get("html_url"));
                publicRepoOut.setText(userData.get("public_repos"));
                dateCreated.setText(formatted);
            });
        }

    }
}