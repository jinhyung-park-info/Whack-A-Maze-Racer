package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Maze.MazeCustomizationActivity;
import com.example.myapplication.Maze.MazeGame;
import com.example.myapplication.WhackAMole.MoleActivity;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.Username);
        String password = intent.getStringExtra(MainActivity.Password);
    }

    public void playGame(View v){
        Intent intent = new Intent(this, TypeRacer.class);
        startActivity(intent);
    }

    public void play_Maze(View view) {
        Intent intent = new Intent(this, MazeCustomizationActivity.class);
        startActivity(intent);
    }
}
