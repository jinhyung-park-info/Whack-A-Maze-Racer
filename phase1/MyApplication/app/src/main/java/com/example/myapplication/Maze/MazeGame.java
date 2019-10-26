package com.example.myapplication.Maze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

public class MazeGame extends AppCompatActivity {

    private static final String TAG = "MazeGame";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "Started maze activity");
        super.onCreate(savedInstanceState);

        Intent intent = getIntent(); // gets the previously created intent
        int bgColour = intent.getIntExtra("bgColour", Color.GREEN);
        //Log.e(TAG, "Found bg color: " + bgColour);
        String difficulty = intent.getStringExtra("difficulty");
        //Log.e(TAG, "Found difficulty: " + difficulty);
        int playerColour = intent.getIntExtra("playerColour", Color.BLACK);
        //Log.e(TAG, "Found shape: " + playerShape);
        MazeView maze = new MazeView(this.getApplicationContext(), bgColour, difficulty,
                playerColour);
        setContentView(maze);

    }
}

