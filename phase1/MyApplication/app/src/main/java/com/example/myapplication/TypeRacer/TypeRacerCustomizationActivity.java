package com.example.myapplication.TypeRacer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.myapplication.GameConstants;
import com.example.myapplication.Maze.MazeCustomizationActivity;
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
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_racer_customization);
        passed = false;
        Intent intent = getIntent();
        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null) {
            setUserManager(user_1);
            userManager.getUser().setLast_played_level(2);
            userManager.update_statistics(this, userManager.getUser());
        }
    }

    private void setUserManager(UserManager newManager){
        userManager = newManager;
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.hard:
                if (checked) {
                    d = 30;
                    numLives = 1;
                }
                break;
            case R.id.normal:
                if (checked) {
                    d = 15;
                    numLives = 3;
                }
                break;
            case R.id.easy:
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

    @Override
    public void onBackPressed(){}

    public void playL2(View view) {
        Intent intent = new Intent(this, typeRacerInstruction.class);
        intent.putExtra("backGroundColorKey", backGround);
        intent.putExtra("difficulty", d);
        intent.putExtra("textColorKey", textColor);
        intent.putExtra("lives", numLives);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

}
