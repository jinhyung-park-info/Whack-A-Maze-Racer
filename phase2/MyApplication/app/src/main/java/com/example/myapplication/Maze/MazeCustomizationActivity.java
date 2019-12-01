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
import com.example.myapplication.UserInfo.UserManager;

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

    private UserManager usermanager;

    static boolean passed = false;

    MazeView maze;

    //private MazeLoader mazeLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null) {
            setUserManager(user_1);
        }
        setContentView(R.layout.activity_maze_customization);

        /*mazeSaveStateFileName = usermanager.getUser().getEmail() + "_maze_save_state.txt";
        startedMaze = false;*/
        //mazeLoader = new MazeLoader(getApplicationContext(), usermanager);
        //usermanager.getUser().setLastPlayedLevel(3);

        //if they clicked start game instead of load game and properly went through each level
        /*if (usermanager.getUser().getLastPlayedLevel() != 3) {
            File mazeFile = new File(getApplicationContext().getFilesDir(), mazeSaveStateFileName);
            if (mazeFile.exists()) {
                mazeFile.delete();
            }
            setContentView(R.layout.activity_maze_customization);
        } else {
            //startedMaze = true;
            setContentView(R.layout.activity_maze_customization);
        }*/
        usermanager.getUser().setLastPlayedLevel(3);
        usermanager.setOrUpdateStatistics(getApplicationContext(), usermanager.getUser(), GameConstants.update);
        reset();

    }

    @Override
    public void onBackPressed(){}

    /**
     * Updates the customization according to the given view.
     * Code borrowed from https://developer.android.com/guide/topics/ui/controls/radiobutton
     *
     * @param view the radio button
     */
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        if(!(checked)){
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

    private void setUserManager(UserManager newManager){
        usermanager = newManager;
    }

    public void startMazeGame(View view) {
        setupMaze();
    }

    private void setupMaze() {
        //maze = mazeLoader.startNewMaze(bgColour, difficulty, playerType);
        //setContentView(maze);
        maze = new MazeView(this, bgColour, difficulty, playerType, usermanager);
        setContentView(maze);
    }

    public void finishButton(View view) {
        if (passed) {
            passed = false;
            Intent intent = new Intent(this, SaveScoreActivity.class);
            intent.putExtra(GameConstants.USERMANAGER, usermanager);
            intent.putExtra(GameConstants.gameName, GameConstants.mazeName);
            startActivity(intent);
        } else
            Toast.makeText(getApplicationContext(), "Please pass this level first",
                    Toast.LENGTH_LONG).show();
        {

        }
    }

   /* *//**
     * Saves the maze when activity is interrupted, if the player already started playing the maze.
     *//*
    @Override
    protected void onPause() {
        super.onPause();
        usermanager.setOrUpdateStatistics(getApplicationContext(), usermanager.getUser());
        if (startedMaze) {
            ArrayList<StringBuilder> savedMaze = mazeLoader.saveMaze();
            FileOutputStream fos = null;
            //overwrites or creates new file
            try {
                fos = getApplicationContext().openFileOutput(mazeSaveStateFileName, MODE_PRIVATE);
                try {
                    for (StringBuilder s : savedMaze) {
                        fos.write(s.toString().getBytes());
                        fos.write("\n".getBytes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (startedMaze) {
            ArrayList<StringBuilder> savedMaze = new ArrayList<>();
            //open the file [username]_maze_save_state.txt
            File mazeFile = new File(getApplicationContext().getFilesDir(), mazeSaveStateFileName);
            try {
                Scanner input = new Scanner(mazeFile);
                while (input.hasNextLine()) {
                    savedMaze.add(new StringBuilder(input.nextLine().trim()));
                }
                input.close();

                maze = new MazeView(getApplicationContext(), usermanager);
                maze = mazeLoader.loadMaze(savedMaze, maze);
                *//*maze = maze.setupOldMaze(mazeAttributes.getCols(), mazeAttributes.getRows(),
                        mazeAttributes.getBgColour(), mazeAttributes.getPlayerType(),
                        mazeAttributes.getPlayerCol(), mazeAttributes.getPlayerRow(),
                        mazeAttributes.getCells());
*//*
                if (maze == null)
                    setContentView(R.layout.activity_maze_customization);
                else
                    setContentView(maze);

                mazeFile.delete();

            } catch (FileNotFoundException e) {
                Log.e(TAG, "Maze file not found on resume");
                setContentView(R.layout.activity_maze_customization);
            }

        }
    }*/

    public void reset() {
       bgColour = Color.GREEN;
        difficulty = GameConstants.Difficulty.NORMAL;
       playerType = 0;
    }
}
