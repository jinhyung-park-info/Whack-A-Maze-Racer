package com.example.myapplication.Maze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.myapplication.GameActivity;
import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.UserManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static com.example.myapplication.MainActivity.USER;

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
     * the player's colour. by default this is black
     */
    private int playerColour = Color.BLACK;

    private User user;

    private boolean startedMaze;

    static boolean passed = false;

    private MazeView maze;

    private String mazeSaveStateFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        User user_1 = (User) intent.getSerializableExtra(USER);
        if (user_1 != null) {
            setUser(user_1);
        }

        mazeSaveStateFileName = user.getEmail() + "_maze_save_state.txt";
        startedMaze = false;

        //if they clicked start game instead of load game and properly went through each level
        if (user.getLast_played_level() != 3) {
            File mazeFile = new File(getApplicationContext().getFilesDir(), mazeSaveStateFileName);
            if (mazeFile.exists()) {
                mazeFile.delete();
            }
            setContentView(R.layout.activity_maze_customization);
        } else {
            setContentView(R.layout.activity_maze_customization);
        }

        user.setLast_played_level(3);
        UserManager.update_statistics(getApplicationContext(), user);
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
            case R.id.radioButtonBlack:
                if (checked)
                    playerColour = Color.BLACK;
                break;
            case R.id.radioButtonMagenta:
                if (checked)
                    playerColour = Color.MAGENTA;
                break;
            case R.id.radioButtonCyan:
                if (checked)
                    playerColour = Color.CYAN;
        }

    }

    private void setUser(User new_user) {
        user = new_user;
    }

    public void startMazeGame(View view) {
        setupMaze();
    }

    private void setupMaze() {
        maze = new MazeView(this, bgColour, difficulty,
                playerColour, user);

        setContentView(maze);
        startedMaze = true;
    }

    public void finish_button(View view) {
        Button button = findViewById(R.id.button8);
        if (passed) {
            passed = false;
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(USER, user);
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
       playerColour = Color.BLACK;
    }
}
