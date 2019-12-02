package com.example.myapplication.TypeRacer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.myapplication.GameConstants;
import com.example.myapplication.R;
import com.example.myapplication.UserInfo.IUserManager;

public class TypeRacerCustomizationActivity extends AppCompatActivity {

    static boolean passed;
    static int numLives, backGround, difficulty, textColor;
    private IUserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_racer_customization);

        // Set Default Values.
        passed = false;
        numLives = GameConstants.maxLife;
        backGround = GameConstants.backGroundDefault;
        difficulty = GameConstants.difficultyDefault;
        textColor = GameConstants.textColorDefault;

        Intent intent = getIntent();
        IUserManager user_1 = (IUserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null) {
            setUserManager(user_1);
            userManager.getUser().setLastPlayedLevel(GameConstants.typeRacerLevel);
            userManager.setOrUpdateStatistics(this, userManager.getUser(), GameConstants.update);
        }
    }

    private void setUserManager(IUserManager newManager){
        userManager = newManager;
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.hard:
                if (checked) {
                    difficulty = GameConstants.difficultyDefault * 6;
                    numLives = GameConstants.minLife;
                }
                break;
            case R.id.normal:
                if (checked) {
                    difficulty = GameConstants.difficultyDefault * 3;
                    numLives = (GameConstants.maxLife + GameConstants.minLife) / 2;
                }
                break;
            case R.id.easy:
                if (checked) {
                    difficulty = GameConstants.difficultyDefault;
                    numLives = GameConstants.maxLife;
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
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("textColorKey", textColor);
        intent.putExtra("lives", numLives);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

}
