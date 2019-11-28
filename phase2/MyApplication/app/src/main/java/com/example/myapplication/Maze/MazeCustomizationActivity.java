package com.example.myapplication.Maze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.myapplication.GameConstants;
import com.example.myapplication.R;
import com.example.myapplication.SaveScoreActivity;
import com.example.myapplication.UserInfo.UserManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MazeCustomizationActivity extends AppCompatActivity {

    private static final String TAG = "MazeCustomizationActivity";

    /**
     * background colour of the screen. by default this is green
     */
    private int bgColour = Color.GREEN;

    /**
     * difficulty of the maze. by default this is normal
     */
    private String difficulty = "Normal";

    /**
     * the player type. by default this is lindsey, which is represented by 0. Paul is 1
     */
    private int playerType = 0;

    private UserManager usermanager;

    private boolean startedMaze;

    static boolean passed = false;

    private MazeView maze;

    private String mazeSaveStateFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        System.out.println(user_1 == null);
        if (user_1 != null) {
            setUserManager(user_1);
        }

        mazeSaveStateFileName = usermanager.getUser().getEmail() + "_maze_save_state.txt";
        startedMaze = false;

        //if they clicked start game instead of load game and properly went through each level
        if (usermanager.getUser().getLastPlayedLevel() != 3) {
            File mazeFile = new File(getApplicationContext().getFilesDir(), mazeSaveStateFileName);
            if (mazeFile.exists()) {
                mazeFile.delete();
            }
            setContentView(R.layout.activity_maze_customization);
        } else {
            setContentView(R.layout.activity_maze_customization);
        }

        System.out.println(usermanager.getUser().getLastPlayedLevel());
        usermanager.getUser().setLastPlayedLevel(3);
        System.out.println(usermanager.getUser().getLastPlayedLevel());
        usermanager.updateStatistics(getApplicationContext(), usermanager.getUser());
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

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButtonGreen:
                if (checked)
                    bgColour = Color.GREEN;
                break;
            case R.id.radioButtonBlue:
                if (checked)
                    bgColour = Color.BLUE;
                break;
            case R.id.radioButtonRed:
                if (checked)
                    bgColour = Color.RED;
                break;
            case R.id.radioButtonYellow:
                if (checked)
                    bgColour = Color.YELLOW;
                break;
            case R.id.radioButtonWhite:
                if (checked)
                    bgColour = Color.WHITE;
                break;
            case R.id.radioButtonHard:
                if (checked)
                    difficulty = "Hard";
                break;
            case R.id.radioButtonNormal:
                if (checked)
                    difficulty = "Normal";
                break;
            case R.id.radioButtonEasy:
                if (checked)
                    difficulty = "Easy";
                break;
            case R.id.radioButtonChar1:
                if (checked)
                    playerType = 0;
                break;
            case R.id.radioButtonCharTwo:
                if (checked)
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
        maze = new MazeView(this, bgColour, difficulty,
                playerType, usermanager);

        setContentView(maze);
        startedMaze = true;
    }

    public void finish_button(View view) {
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

    /**
     * Saves the maze when activity is interrupted, if the player already started playing the maze.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (startedMaze) {
            System.out.println("Writing save state file for maze");
            //save maze.saveMaze() to an ArrayList of StringBuilders
            ArrayList<StringBuilder> savedMaze = maze.saveMaze();
            FileOutputStream fos = null;
            //overwrites or creates new file
            try {
                fos = getApplicationContext().openFileOutput(mazeSaveStateFileName, MODE_PRIVATE);
                try {
                    for (StringBuilder s : savedMaze) {
                        //write the stringbuilder to the file, appending a newline character
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
            //initialize an arraylist of stringbuilders
            ArrayList<StringBuilder> savedMaze = new ArrayList<>();
            //open the file [username]_maze_save_state.txt
            File mazeFile = new File(getApplicationContext().getFilesDir(), mazeSaveStateFileName);
            try {
                Scanner input = new Scanner(mazeFile);
                //for each line the file
                while (input.hasNextLine()) {
                    //trim and convert the line to a stringbuilder and append to arraylist
                    savedMaze.add(new StringBuilder(input.nextLine().trim()));
                }
                //close file
                input.close();
                //send this ArrayList to maze.loadMaze()
                maze.loadMaze(savedMaze);
                mazeFile.delete();
            } catch (FileNotFoundException e) {
                Log.e(TAG, "Maze file not found on resume");
            }

        }
    }

    public void reset() {
       bgColour = Color.GREEN;
       difficulty = "Normal";
       playerType = 0;
    }
}
