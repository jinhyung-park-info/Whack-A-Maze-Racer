package com.example.myapplication.Maze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.User;

import static com.example.myapplication.MainActivity.USER;

public class MazeGame extends AppCompatActivity {

    private static final String TAG = "MazeGame";
    private User user;

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
        MazeView maze = new MazeView(this.getApplicationContext(), bgColour, difficulty,
                playerColour, user);
        setContentView(maze);

    }

    private void setUser(User new_user){
        user = new_user;
    }
}

