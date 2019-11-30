package com.example.myapplication.TypeRacer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.GameConstants;
import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.UserManager;

public class typeRacerInstruction extends AppCompatActivity {

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_racer_instruction);

        Intent intent = getIntent();
        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null){
            setUserManager(user_1);
        }

    }

    public void playTypeRacer(View v){
        Intent intent = new Intent(this, TypeRacer.class);
        intent.putExtra("backGroundColorKey", getIntent().getExtras().getInt("backGroundColorKey"));
        intent.putExtra("textColorKey", getIntent().getExtras().getInt("textColorKey"));
        intent.putExtra("difficulty", getIntent().getExtras().getInt("difficulty"));
        intent.putExtra("lives", getIntent().getExtras().getInt("lives"));
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    private void setUserManager(UserManager newManager){
        userManager = newManager;
    }
}
