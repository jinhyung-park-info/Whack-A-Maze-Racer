package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Maze.MazeCustomizationActivity;
import com.example.myapplication.TypeRacer.TypeRacer;
import com.example.myapplication.WhackAMole.MoleActivity;

import static com.example.myapplication.MainActivity.USER;

public class GameActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        User user_1 = (User) intent.getSerializableExtra(USER);
        if (user_1 != null){
            setUser(user_1);
        }
    }
    private void setUser(User new_user){
        user = new_user;
    }

    public void play_Mole(View v){
        Intent intent = new Intent(this, MoleActivity.class);
        intent.putExtra(USER, user);
        startActivity(intent);
    }

    public void view_stats(View view){
        Intent intent = new Intent(this, PopUp.class);
        intent.putExtra(USER, user);
        startActivity(intent);
    }
}
