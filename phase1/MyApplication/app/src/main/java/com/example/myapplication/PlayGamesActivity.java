package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Maze.MazeInstructionsActivity;
import com.example.myapplication.TypeRacer.TypeRacer;
import com.example.myapplication.TypeRacer.TypeRacerCustomizationActivity;
import com.example.myapplication.TypeRacer.typeRacerInstruction;
import com.example.myapplication.WhackAMole.MoleInstructionActivity;

import java.io.File;

import static com.example.myapplication.MainActivity.USER;

public class PlayGamesActivity extends AppCompatActivity {

    private UserManager userManager;
    //private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_games);
        Intent intent = getIntent();
        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null){
            setUserManager(user_1);
        }
    }

    public void playMole(View v){
        userManager.getUser().setLoad_moles_stats("0");
        userManager.update_statistics(this, userManager.getUser());
        Intent intent = new Intent(this, MoleInstructionActivity.class);

        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    public void playTypeRacer(View view){
        Intent intent = new Intent(this, TypeRacerCustomizationActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    public void playMaze(View view){
        Intent intent = new Intent(this, MazeInstructionsActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    private void setUserManager(UserManager newManager){
        userManager = newManager;
    }

    public void GoBack(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
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
