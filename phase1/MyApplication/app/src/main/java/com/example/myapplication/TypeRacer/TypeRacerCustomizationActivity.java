package com.example.myapplication.TypeRacer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.myapplication.Maze.MazeGame;
import com.example.myapplication.R;

public class TypeRacerCustomizationActivity extends AppCompatActivity {

    static boolean passed;
    static int numLives = 5;
    static int streak = 0;
    static int trBC = Color.BLUE;
    static String d = "Mild";
    static int tC = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_racer_customization);
        passed = false;
    }

    public void onRadioButtonClicked(View view) {

        if (((RadioButton) view).isChecked()) {
            switch (view.getId()) {
                case R.id.hot:
                    numLives = 1;
                    break;
                case R.id.spicy:
                    numLives = 3;
                    break;
                case R.id.mild:
                    numLives = 5;
                    break;
                case R.id.textColorBlack:
                    tC = Color.BLACK;
                    break;
                case R.id.TextColorBlue:
                    tC = Color.BLUE;
                    break;
                case R.id.textColorMagenta:
                    tC = Color.MAGENTA;
                    break;
                case R.id.tr_white:
                    trBC = Color.WHITE;
                    break;
                case R.id.tr_green:
                    trBC = Color.GREEN;
                    break;

            }
        }
    }

    public void playL2(View view) {
        Intent intent = new Intent(this, TypeRacer.class);
        intent.putExtra("trBC", trBC);
        intent.putExtra("difficulty", d);
        intent.putExtra("textColor", tC);
        startActivity(intent);
    }

    public void goToL3(View view) {
        Button nextButton = findViewById(R.id.tr_button);
//        if (passed) {
//            passed = false;
//            Intent intent = new Intent(this, MazeGame.class);
//            startActivity(intent);
//        } else {
//            nextButton.setError("Please Pass This Level First");
//    }
        startActivity(new Intent(this, MazeGame.class));
    }
}
