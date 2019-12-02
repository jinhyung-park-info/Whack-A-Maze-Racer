package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.UserInfo.IUser;
import com.example.myapplication.UserInfo.IUserManager;

public class SaveScoreActivity extends AppCompatActivity {

    int racerStreak = 0;
    int moleScore = 0;
    int moleHigh = 0;
    private String incomingGame;
    IUserManager userManager;
    IUser user;
    int originalNumMazeGamesPlayed;
    int originalMazeItemsCollected;
    int originalOverallScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_score);

        Intent intent = getIntent();
        IUserManager user_1 = (IUserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null) {
            setUserManager(user_1);
            user = userManager.getUser();
        }
        incomingGame = (String) intent.getSerializableExtra(GameConstants.gameName);
        //if from MoleActivity
        if (incomingGame.equals(GameConstants.moleName)) {
            moleScore = (int) intent.getSerializableExtra(GameConstants.MoleScore);
            moleHigh = (int) intent.getSerializableExtra(GameConstants.MoleHigh);
        } else if (incomingGame.equals(GameConstants.mazeNameForIntent)) {
        } else if (incomingGame.equals(GameConstants.racerName)) {
            racerStreak = (int) intent.getSerializableExtra(GameConstants.TypeRacerStreak);
        }
        if(incomingGame.equals(GameConstants.mazeNameForIntent)){
            originalMazeItemsCollected = (int) intent.getSerializableExtra(GameConstants.NumCollectiblesCollectedMaze);
            originalNumMazeGamesPlayed = (int) intent.getSerializableExtra(GameConstants.NumMazeGamesPlayed);
            originalOverallScore = (int) intent.getSerializableExtra(GameConstants.overallScore);
        }

    }

    private void setUserManager(IUserManager newManager) {
        userManager = newManager;
    }

    public void save(View view) {
        user.setOverallScore(user.getOverallScore() + moleScore);
        user.setOverallScore(user.getOverallScore() + racerStreak * 10);
        moleHigh = Math.max(moleHigh, (int) user.getStatistic(GameConstants.WHACK_A_MOLE, GameConstants.MoleAllTimeHigh));
        user.setStatistic(GameConstants.WHACK_A_MOLE, GameConstants.MoleAllTimeHigh, moleHigh);
        userManager.setOrUpdateStatistics(this, user, GameConstants.update);
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    public void notSave(View view) {
        if(incomingGame.equals(GameConstants.mazeNameForIntent)){
            user.setStatistic(GameConstants.MAZE, GameConstants.NumMazeGamesPlayed, originalNumMazeGamesPlayed);
            user.setStatistic(GameConstants.MAZE, GameConstants.NumCollectiblesCollectedMaze, originalMazeItemsCollected);
            user.setOverallScore(originalOverallScore);
        }
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }
}
