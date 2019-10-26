package com.example.myapplication.WhackAMole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;


import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.GameActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class MoleActivity extends AppCompatActivity {
    static boolean passed;
    static int numLives = 5;
    static int numColumns = 2;
    static int numRows = 2;
    static int backgroundID = R.drawable.game_background;;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mole);
        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.Username);
        String password = intent.getStringExtra(MainActivity.Password);
        passed = false;
    }

    public void onRadioButtonClicked(View view) {

        if (((RadioButton) view).isChecked()) {
            switch (view.getId()) {
                case R.id.hardcore:
                    numLives = 1;
                    break;
                case R.id.difficult:
                    numLives = 3;
                    break;
                case R.id.noraml:
                    numLives = 5;
                    break;
                case R.id.easy:
                    numLives = 10;
                    break;
                case R.id.c1:
                    numColumns = 1;
                    break;
                case R.id.c2:
                    numColumns = 2;
                    break;
                case R.id.c3:
                    numColumns = 3;
                    break;
                case R.id.c4:
                    numColumns = 4;
                    break;
                case R.id.r1:
                    numRows = 1;
                    break;
                case R.id.r2:
                    numRows = 2;
                    break;
                case R.id.r3:
                    numRows = 3;
                    break;
                case R.id.r4:
                    numRows = 4;
                    break;
                case R.id.beach:
                    backgroundID = R.drawable.game_background;
                    break;
                case R.id.grass:
                    backgroundID = R.drawable.game_background_grass;
                    break;

            }
        }
    }

    public void play(View view) {
        WamView wamView = new WamView(this);
        setContentView(wamView);
    }

    public void next(View view) {
        Button nextButton = findViewById(R.id.button);
        if (passed) {
            passed = false;
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        } else {
            nextButton.setError("Please Pass This Level First");
        }

    }
}
