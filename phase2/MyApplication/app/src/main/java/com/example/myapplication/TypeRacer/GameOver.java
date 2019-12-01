package com.example.myapplication.TypeRacer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.GameConstants;
import com.example.myapplication.R;
import com.example.myapplication.TypeRacer.TypeRacerCustomizationActivity;
import com.example.myapplication.UserInfo.UserManager;

public class GameOver extends AppCompatActivity {

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent previous = getIntent();
        UserManager user_1 = (UserManager) previous.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null){
            setUser(user_1);
        }

        Button goBackButton = (Button) findViewById(R.id.goBackBtn);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TypeRacerCustomizationActivity.class);
                intent.putExtra(GameConstants.USERMANAGER, userManager);
                startActivity(intent);
            }
        });

    }

    private void setUser(UserManager new_user){
        userManager = new_user;
    }
}
