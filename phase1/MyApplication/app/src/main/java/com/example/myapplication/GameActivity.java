package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Maze.MazeCustomizationActivity;
import com.example.myapplication.TypeRacer.TypeRacer;
import com.example.myapplication.TypeRacer.TypeRacerCustomizationActivity;
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

    public void resume(View view){
        switch (user.getLast_played_level()){
            case 0:
                Button loadButton = findViewById(R.id.button4);
                loadButton.setError("Have not started the game!");
                break;
            case 1:
                MoleActivity.loaded = true;
                Intent intent = new Intent(this, MoleActivity.class);
                intent.putExtra(USER, user);
                startActivity(intent);
                break;
            case 2:
                Intent intent2 = new Intent(this, TypeRacerCustomizationActivity.class);
                intent2.putExtra(USER, user);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(this, MazeCustomizationActivity.class);
                intent3.putExtra(USER, user);
                startActivity(intent3);
                break;
        }
    }
}
