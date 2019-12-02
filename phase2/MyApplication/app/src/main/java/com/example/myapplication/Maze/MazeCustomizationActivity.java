package com.example.myapplication.Maze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.myapplication.GameConstants;
import com.example.myapplication.R;
import com.example.myapplication.SaveScoreActivity;
import com.example.myapplication.UserInfo.IUserManager;

public class MazeCustomizationActivity extends AppCompatActivity {

    private static final String TAG = "MazeCustomizationActivity";

    /**
     * background colour of the screen. by default this is green
     */
    private int bgColour = Color.GREEN;

    /**
     * difficulty of the maze. by default this is normal
     */
    private GameConstants.Difficulty difficulty = GameConstants.Difficulty.NORMAL;

    /**
     * the player type. by default this is lindsey, which is represented by 0. Paul is 1
     */
    private int playerType = 0;

    private IUserManager usermanager;


    static boolean passed = false;

    MazeView maze;

    private int originalNumMazeGamesPlayed;

    private int originalNumMazeItemsCollected;

    private int originalOverallScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        IUserManager user_1 = (IUserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null) {
            setUserManager(user_1);
            originalNumMazeGamesPlayed = (int) usermanager.getUser().getStatistic(GameConstants.MAZE,
                    GameConstants.NumMazeGamesPlayed);
            originalNumMazeItemsCollected = (int) usermanager.getUser().getStatistic(GameConstants.MAZE,
                    GameConstants.NumCollectiblesCollectedMaze);
            originalOverallScore = usermanager.getUser().getOverallScore();

        }
        setContentView(R.layout.activity_maze_customization);

        usermanager.getUser().setLastPlayedLevel(GameConstants.mazeLevel);
        usermanager.setOrUpdateStatistics(getApplicationContext(), usermanager.getUser(), GameConstants.update);

        //to reset customization after game is finished
        reset();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MazeInstructionsActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, usermanager);
        startActivity(intent);
    }

    /**
     * Updates the customization according to the given view.
     * Code borrowed from https://developer.android.com/guide/topics/ui/controls/radiobutton
     *
     * @param view the radio button
     */
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (!(checked)) {
            return;
        }

        switch (view.getId()) {
            case R.id.radioButtonGreen:
                bgColour = Color.GREEN;
                break;
            case R.id.radioButtonBlue:
                bgColour = Color.BLUE;
                break;
            case R.id.radioButtonRed:
                bgColour = Color.RED;
                break;
            case R.id.radioButtonYellow:
                bgColour = Color.YELLOW;
                break;
            case R.id.radioButtonWhite:
                bgColour = Color.WHITE;
                break;
            case R.id.radioButtonHard:
                difficulty = GameConstants.Difficulty.HARD;
                break;
            case R.id.radioButtonNormal:
                difficulty = GameConstants.Difficulty.NORMAL;
                break;
            case R.id.radioButtonEasy:
                difficulty = GameConstants.Difficulty.EASY;
                break;
            case R.id.radioButtonChar1:
                playerType = 0;
                break;
            case R.id.radioButtonCharTwo:
                playerType = 1;
                break;
        }

    }

    private void setUserManager(IUserManager newManager) {
        usermanager = newManager;
    }

    public void startMazeGame(View view) {
        setupMaze();
    }

    private void setupMaze() {
        maze = new MazeView(this, bgColour, difficulty, playerType, usermanager);
        setContentView(maze);
    }

    public void finishButton(View view) {
        if (passed) {
            passed = false;
            Intent intent = new Intent(this, SaveScoreActivity.class);
            intent.putExtra(GameConstants.USERMANAGER, usermanager);
            intent.putExtra(GameConstants.gameName, GameConstants.mazeNameForIntent);
            intent.putExtra(GameConstants.NumMazeGamesPlayed, originalNumMazeGamesPlayed);
            intent.putExtra(GameConstants.NumCollectiblesCollectedMaze, originalNumMazeItemsCollected);
            intent.putExtra(GameConstants.overallScore, originalOverallScore);
            startActivity(intent);
        } else
            Toast.makeText(getApplicationContext(), "Please pass this level first",
                    Toast.LENGTH_LONG).show();
        {

        }
    }

    public void reset() {
        bgColour = Color.GREEN;
        difficulty = GameConstants.Difficulty.NORMAL;
        playerType = 0;
    }
}
