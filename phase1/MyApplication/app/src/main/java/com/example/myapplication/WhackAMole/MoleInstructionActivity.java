package com.example.myapplication.WhackAMole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.GameConstants;
import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.UserManager;

import static com.example.myapplication.MainActivity.USER;

public class MoleInstructionActivity extends AppCompatActivity {

    private UserManager userManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mole_instruction);

        Intent intent = getIntent();
        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null){
            setUserManager(user_1);
        }
    }

    public void play_game(View v){
        //userManager.getUser().setLoad_moles_stats("0");
        userManager.getUser().setStatistic(GameConstants.NameGame1, GameConstants.MoleStats, "0");
        userManager.update_statistics(this, userManager.getUser());
        Intent intent = new Intent(this, MoleActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    private void setUserManager(UserManager newManager){
        userManager = newManager;
    }
}
