package com.example.numad21s_czl;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class activity_click_letters extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_letters);
    }

    public void displayLetterClicked(View view){
        TextView displayBtnPress = findViewById(R.id.btn_pressed_textview);
        String letter = "";
        switch(view.getId()) {
            case R.id.btn_a:
                letter = getString(R.string.a_string);
                break;
            case R.id.btn_b:
                letter = getString(R.string.b_string);
                break;
            case R.id.btn_c:
                letter = getString(R.string.c_string);
                break;
            case R.id.btn_d:
                letter = getString(R.string.d_string);
                break;
            case R.id.btn_e:
                letter = getString(R.string.e_string);
                break;
            case R.id.btn_f:
                letter = getString(R.string.f_string);
                break;
        }
        displayBtnPress.setText(String.format("Pressed: %s", letter));

    }
}