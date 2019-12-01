package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Maze.MazeInstructionsActivity;
import com.example.myapplication.TypeRacer.TypeRacerCustomizationActivity;
import com.example.myapplication.UserInfo.IUserManager;
import com.example.myapplication.UserInfo.UserManager;
import com.example.myapplication.WhackAMole.MoleInstructionActivity;

public class PlayGamesActivity extends AppCompatActivity {

    private IUserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_games);
        Intent intent = getIntent();
        IUserManager user_1 = (IUserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null) {
            setUserManager(user_1);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    public void playMole(View v) {
        //userManager.getUser().setLoad_moles_stats("0");
        userManager.getUser().setStatistic(GameConstants.WHACK_A_MOLE, GameConstants.MoleStats, "0");
        userManager.setOrUpdateStatistics(this, userManager.getUser(), GameConstants.update);
        Intent intent = new Intent(this, MoleInstructionActivity.class);

        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    public void playTypeRacer(View view) {
        Intent intent = new Intent(this, TypeRacerCustomizationActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    public void playMaze(View view) {
        Intent intent = new Intent(this, MazeInstructionsActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    private void setUserManager(IUserManager newManager) {
        userManager = newManager;
    }

    public void GoBack(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }
}
