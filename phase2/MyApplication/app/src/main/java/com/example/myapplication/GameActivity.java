package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Maze.MazeCustomizationActivity;
import com.example.myapplication.TypeRacer.TypeRacerCustomizationActivity;
import com.example.myapplication.UserInfo.IUserManager;
import com.example.myapplication.WhackAMole.MoleActivity;

import java.io.File;

public class GameActivity extends AppCompatActivity {

    private IUserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        IUserManager userManager1 = (IUserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (userManager1 != null) {
            setUserManager(userManager1);
        }
        TextView gemDisplay = findViewById(R.id.gemDisplay);
        String text = "Gem: " + userManager.getUser().getCurrency();
        gemDisplay.setText(text);
    }


    private void setUserManager(IUserManager usermanager) {
        userManager = usermanager;
    }

    public void view_stats(View view) {
        Intent intent = new Intent(this, ViewStatisticsPopUpActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    public void playGame(View view) {
        File file_type = new File(getApplicationContext().getFilesDir(), userManager.getUser().getEmail() + "_typeracer.txt");
        if (file_type.exists()) {
            if (file_type.delete()) {
                Log.i("File", "deleted the typeracer save file because new game was started");
            }
        }
        Intent intent = new Intent(this, PlayGamesActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    public void resume(View view) {
        switch (userManager.getUser().getLastPlayedLevel()) {
            case GameConstants.whackAMoleLevel:
                Intent intent = new Intent(this, MoleActivity.class);
                intent.putExtra(GameConstants.USERMANAGER, userManager);
                startActivity(intent);
                break;
            case GameConstants.typeRacerLevel:
                Intent intent2 = new Intent(this, TypeRacerCustomizationActivity.class);
                intent2.putExtra(GameConstants.USERMANAGER, userManager);
                startActivity(intent2);
                break;
            case GameConstants.mazeLevel:
                Intent intent3 = new Intent(this, MazeCustomizationActivity.class);
                intent3.putExtra(GameConstants.USERMANAGER, userManager);
                startActivity(intent3);
                break;
            default:
                Toast.makeText(getApplicationContext(), "You have not played a game yet"
                        , Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void ViewAccountInfo(View view) {
        Intent intent = new Intent(this, AccountInformationActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    public void ViewScoreBoard(View view) {
        Intent intent = new Intent(this, ViewScoreBoardActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    public void buyGem(View view) {
        Intent intent = new Intent(this, InGamePurchaseActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }
}
