package com.example.myapplication.TypeRacer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.myapplication.Maze.MazeCustomizationActivity;
import com.example.myapplication.Maze.MazeGame;
import com.example.myapplication.R;
import com.example.myapplication.User;

import static com.example.myapplication.MainActivity.USER;

public class TypeRacerCustomizationActivity extends AppCompatActivity {

    static boolean passed;
    static int numLives = 5;
    static int streak = 0;
    static int trBC = Color.BLUE;
    static String d = "Mild";
    static int tC = Color.BLACK;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_racer_customization);
        passed = false;
        Intent intent = getIntent();
        User user_1 = (User) intent.getSerializableExtra(USER);
        if (user_1 != null){
            setUser(user_1);
        }
    }

    private void setUser(User new_user){
        user = new_user;
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
        intent.putExtra(USER, user);
        startActivity(intent);
    }

    public void goToL3(View view) {
        Intent intent = new Intent(this, MazeCustomizationActivity.class);
        startActivity(intent);
    }
}