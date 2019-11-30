package com.example.myapplication.TypeRacer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.GameActivity;
import com.example.myapplication.GameConstants;
import com.example.myapplication.R;
import com.example.myapplication.SaveScoreActivity;
import com.example.myapplication.UserInfo.IUser;
import com.example.myapplication.UserInfo.UserManager;

public class TypeRacerEnd extends AppCompatActivity {
    private UserManager userManager;
    private IUser user;
    private int streak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_racer_end);

        Intent intent = getIntent();
        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null){
            setUserManager(user_1);
            user = userManager.getUser();
        }

        TextView finalScore = findViewById(R.id.finalScoreTextView);
        finalScore.setText(getIntent().getExtras().getString("finalScore"));

        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                streak = 0;
                int racerStreak = (int) user.getStatistic(GameConstants.NameGame2, GameConstants.TypeRacerStreak);
                user.setStatistic(GameConstants.NameGame2, GameConstants.TypeRacerStreak, racerStreak + streak);
                Intent intent = new Intent(getApplicationContext(), SaveScoreActivity.class);
                intent.putExtra(GameConstants.USERMANAGER, userManager);
                intent.putExtra(GameConstants.TypeRacerStreak, racerStreak);
                intent.putExtra(GameConstants.gameName, GameConstants.racerName);
                startActivity(intent);
            }
        });

    }
    private void setUserManager(UserManager newManager){
        userManager = newManager;
    }
}
