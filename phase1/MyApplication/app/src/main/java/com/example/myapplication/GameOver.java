package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.TypeRacer.TypeRacerCustomizationActivity;

import static com.example.myapplication.MainActivity.USER;

public class GameOver extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent previous = getIntent();
        User user_1 = (User) previous.getSerializableExtra(USER);
        if (user_1 != null){
            setUser(user_1);
        }

        Button goBackButton = (Button) findViewById(R.id.goBackBtn);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TypeRacerCustomizationActivity.class);
                intent.putExtra(USER, user);
                startActivity(intent);
            }
        });

    }

    private void setUser(User new_user){
        user = new_user;
    }
}
