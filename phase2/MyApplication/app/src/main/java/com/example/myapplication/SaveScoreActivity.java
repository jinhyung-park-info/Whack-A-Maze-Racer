package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.UserInfo.IUser;
import com.example.myapplication.UserInfo.UserManager;

public class SaveScoreActivity extends AppCompatActivity {

    int racerStreak = 0;
    int moleScore = 0;
    int moleHigh = 0;
    private String incomingGame;
    UserManager userManager;
    IUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_score);

        Intent intent = getIntent();
        incomingGame = (String) intent.getSerializableExtra(GameConstants.gameName);
        //if from MoleActivity
        if (incomingGame.equals(GameConstants.moleName)) {
            moleScore = (int) intent.getSerializableExtra(GameConstants.MoleScore);
            moleHigh = (int) intent.getSerializableExtra(GameConstants.MoleHigh);
        } else if (incomingGame.equals(GameConstants.mazeName)) {
        } else if (incomingGame.equals(GameConstants.racerName)) {
            racerStreak = (int) intent.getSerializableExtra(GameConstants.TypeRacerStreak);
        }

        //if from MazeCustomizationActivity
        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null) {
            setUserManager(user_1);
            user = userManager.getUser();
        }
    }

    private void setUserManager(UserManager newManager) {
        userManager = newManager;
    }

    public void save(View view) {
        user.setOverallScore(user.getOverallScore() + moleScore);
        user.setOverallScore(user.getOverallScore() + racerStreak * 10);
        moleHigh = Math.max(moleHigh, (int) user.getStatistic(GameConstants.NameGame1, GameConstants.MoleAllTimeHigh));
        user.setStatistic(GameConstants.NameGame1, GameConstants.MoleAllTimeHigh, moleHigh);
        userManager.setOrUpdateStatistics(this, user, GameConstants.update);
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    public void notSave(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }
}
