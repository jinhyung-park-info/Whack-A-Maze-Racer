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
import com.example.myapplication.UserManager;

import static com.example.myapplication.MainActivity.USER;

public class TypeRacerCustomizationActivity extends AppCompatActivity {

    static boolean passed;
    static int numLives = 5;
    static int backGround = Color.WHITE;
    static int d = 5;
    static int textColor = Color.BLACK;
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
        user.setLast_played_level(2);
        UserManager.update_statistics(this, user, user.getScore(), user.getStreaks(), user.getNum_maze_games_played(), user.getLast_played_level());
    }

    private void setUser(User new_user){
        user = new_user;
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.hot:
                if (checked) {
                    d = 30;
                    numLives = 1;
                }
                break;
            case R.id.spicy:
                if (checked) {
                    d = 15;
                    numLives = 3;
                }
                break;
            case R.id.mild:
                if (checked) {
                    d = 5;
                    numLives = 5;
                }
                break;
            case R.id.textColorBlack:
                if (checked) {
                    textColor = Color.BLACK;
                }
                break;
            case R.id.TextColorBlue:
                if (checked) {
                    textColor = Color.BLUE;
                }
                break;
            case R.id.textColorMagenta:
                if (checked) {
                    textColor = Color.MAGENTA;
                }
                break;
            case R.id.tr_white:
                if (checked) {
                    backGround = Color.WHITE;
                }
                break;
            case R.id.tr_green:
                if (checked) {
                    backGround = Color.GREEN;
                }
                break;

            }
    }

    public void playL2(View view) {
        Intent intent = new Intent(this, TypeRacer.class);
        intent.putExtra("backGroundColorKey", backGround);
        intent.putExtra("difficulty", d);
        intent.putExtra("textColorKey", textColor);
        intent.putExtra("lives", numLives);
        intent.putExtra(USER, user);
        startActivity(intent);
    }

    public void goToL3(View view) {
        Intent intent = new Intent(this, MazeCustomizationActivity.class);
        intent.putExtra(USER, user);
        startActivity(intent);
    }
}
