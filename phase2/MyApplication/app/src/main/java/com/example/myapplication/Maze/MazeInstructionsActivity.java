package com.example.myapplication.Maze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.GameConstants;
import com.example.myapplication.PlayGamesActivity;
import com.example.myapplication.R;
import com.example.myapplication.UserInfo.IUserManager;

public class MazeInstructionsActivity extends AppCompatActivity {

    private IUserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze_instructions);

        Intent intent = getIntent();
        IUserManager user_1 = (IUserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null) {
            setUserManager(user_1);
        }
    }

    public void onStartGameClicked(View v) {
        Intent intent = new Intent(this, MazeCustomizationActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    private void setUserManager(IUserManager newManager){
        userManager = newManager;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PlayGamesActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }
}
