package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Maze.MazeInstructionsActivity;
import com.example.myapplication.TypeRacer.typeRacerInstruction;
import com.example.myapplication.WhackAMole.MoleInstructionActivity;

import java.io.File;

import static com.example.myapplication.MainActivity.USER;

public class PlayGamesActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_games);
        Intent intent = getIntent();
        User user_1 = (User) intent.getSerializableExtra(USER);
        if (user_1 != null){
            setUser(user_1);
        }
    }

    public void playMole(View v){
        user.setLoad_moles_stats("0");
        UserManager.update_statistics(this, user);
        Intent intent = new Intent(this, MoleInstructionActivity.class);

        intent.putExtra(USER, user);
        startActivity(intent);
    }

    public void playTypeRacer(View view){
        Intent intent = new Intent(this, typeRacerInstruction.class);
        intent.putExtra(USER, user);
        startActivity(intent);
    }

    public void playMaze(View view){
        Intent intent = new Intent(this, MazeInstructionsActivity.class);
        intent.putExtra(USER, user);
        startActivity(intent);
    }

    private void setUser(User new_user){
        user = new_user;
    }

    public void GoBack(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(USER, user);
        startActivity(intent);
    }

    /*@Override
    protected void onDestroy() {
        System.out.println("OnDestroy");
        super.onDestroy();
    }

    @Override
    protected void onPause(){
        System.out.println("OnPause");
        super.onPause();

    }

    @Override
    protected void onStop(){
        System.out.println("OnStop");
        super.onStop();
    }*/
}
