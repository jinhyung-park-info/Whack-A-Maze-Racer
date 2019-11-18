package com.example.myapplication.TypeRacer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.UserManager;

import static com.example.myapplication.MainActivity.USER;

public class typeRacerInstruction extends AppCompatActivity {

    User user;
    static int numLives = 5;
    static int backGround = Color.WHITE;
    static int d = 5;
    static int textColor = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_racer_instruction);

        Intent intent = getIntent();
        User user_1 = (User) intent.getSerializableExtra(USER);
        if (user_1 != null) {
            setUser(user_1);
    }
        
            backGround = intent.getExtras().getInt("backGroundColorKey");
            textColor = intent.getExtras().getInt("textColorKey");
            numLives = intent.getExtras().getInt("lives");
            d = intent.getIntExtra("difficulty", 5);

    }


    public void playTypeRacer(View v){
        UserManager.update_statistics(this, user);
        Intent intent = new Intent(this, TypeRacer.class);
        intent.putExtra("backGroundColorKey", backGround);
        intent.putExtra("difficulty", d);
        intent.putExtra("textColorKey", textColor);
        intent.putExtra("lives", numLives);
        intent.putExtra(USER, user);
        startActivity(intent);
    }

    private void setUser(User new_user) {
        user = new_user;
    }
}
