package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Maze.MazeCustomizationActivity;
import com.example.myapplication.TypeRacer.TypeRacerCustomizationActivity;
import com.example.myapplication.UserInfo.UserManager;
import com.example.myapplication.WhackAMole.MoleActivity;

import java.io.File;

public class GameActivity extends AppCompatActivity {

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        UserManager userManager1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (userManager1 != null){
            setUserManager(userManager1);
        }
        TextView gemDisplay = findViewById(R.id.gemDisplay);
        String text = "Gem: " + userManager.getUser().getCurrency();
        gemDisplay.setText(text);
    }


    private void setUserManager(UserManager usermanage){
        userManager = usermanage;
    }

    public void view_stats(View view){
        Intent intent = new Intent(this, ViewStatisticsPopUpActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    public void playGame(View view) {
        File file_type = new File(getApplicationContext().getFilesDir(),userManager.getUser().getEmail() + "_typeracer.txt");
        File file_maze = new File(getApplicationContext().getFilesDir(), userManager.getUser().getEmail() + "_maze_save_state.txt");
        if(file_type.exists()){
            file_type.delete();
        }
        if(file_maze.exists()){
            file_maze.delete();
        }
        Intent intent = new Intent(this, PlayGamesActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    public void resume(View view){
        switch (userManager.getUser().getLastPlayedLevel()) {
            case 0:
                Toast.makeText(getApplicationContext(), "You have not played a game yet"
                        , Toast.LENGTH_LONG).show();
                break;
            case 1:
                MoleActivity.loaded = true;
                Intent intent = new Intent(this, MoleActivity.class);
                intent.putExtra(GameConstants.USERMANAGER, userManager);
                startActivity(intent);
                break;
            case 2:
                Intent intent2 = new Intent(this, TypeRacerCustomizationActivity.class);
                intent2.putExtra(GameConstants.USERMANAGER, userManager);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(this, MazeCustomizationActivity.class);
                intent3.putExtra(GameConstants.USERMANAGER, userManager);
                startActivity(intent3);
                break;
        }
    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Logout(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void ViewAccountInfo(View view){
        Intent intent = new Intent(this, AccountInformationActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    public void ViewScoreBoard(View view){
        Intent intent = new Intent(this, ViewScoreBoardActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    public void buyGem(View view){
        Intent intent = new Intent(this, InGamePurchaseActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }
}
