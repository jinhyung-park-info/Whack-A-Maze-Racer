package com.example.myapplication.Maze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.GameActivity;
import com.example.myapplication.User;

import static com.example.myapplication.MainActivity.USER;

public class MazeGame extends AppCompatActivity {

    private static final String TAG = "MazeGame";
    private User user;
    //static boolean passed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "Started maze activity");
        super.onCreate(savedInstanceState);

        Intent intent = getIntent(); // gets the previously created intent
        User user_1 = (User) intent.getSerializableExtra(USER);
        if (user_1 != null){
            setUser(user_1);
        }
        int bgColour = intent.getIntExtra("bgColour", Color.GREEN);
        //Log.e(TAG, "Found bg color: " + bgColour);
        String difficulty = intent.getStringExtra("difficulty");
        //Log.e(TAG, "Found difficulty: " + difficulty);
        int playerColour = intent.getIntExtra("playerColour", Color.BLACK);
        //Log.e(TAG, "Found shape: " + playerShape);
        MazeView maze = new MazeView(this, bgColour, difficulty,
                playerColour, user);
        setContentView(maze);
        /*if(passed){
            Intent new_intent = new Intent(this, GameActivity.class);
            startActivity(new_intent);
        }*/

    }

    @Override
    protected void onPause() {
        super.onPause();
        //save maze.saveGame() to an ArrayList of StringBuilders
        //Check for a [username]_maze_save_state.txt file in the local directory and if found
        //delete the file
        //create a new [username]_maze_save_state.txt file
        //for each stringbuilder in the arraylist
        //write the stringbuilder to the file, appending a newline character
        //close the file
    }

    @Override
    protected void onResume() {
        super.onResume();
        //initialize an arraylist of stringbuilders
        //open the file [username]_maze_save_state.txt
        //for each line the file
        //trim and convert the line to a stringbuilder and append to arraylist
        //close file
        //send this ArrayList to maze.loadGame()
    }

    private void setUser(User new_user){
        user = new_user;
    }
}

