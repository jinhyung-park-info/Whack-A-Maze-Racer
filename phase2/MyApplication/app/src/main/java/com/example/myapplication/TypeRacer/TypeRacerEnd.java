package com.example.myapplication.TypeRacer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.GameConstants;
import com.example.myapplication.R;
import com.example.myapplication.SaveScoreActivity;
import com.example.myapplication.UserInfo.IUser;
import com.example.myapplication.UserInfo.IUserManager;

import java.util.Objects;

public class TypeRacerEnd extends AppCompatActivity implements TypeRacerObserver {
    private IUserManager userManager;
    private IUser user;
    private int streak;

    private TypeRacerData mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_racer_end);


        Intent intent = getIntent();
        IUserManager user_1 = (IUserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null) {
            setUserManager(user_1);
            user = userManager.getUser();
        }

        // register this observer

        mData = TypeRacerData.getInstance(Objects.requireNonNull(getIntent().getExtras()).getString("finalScore") + "/25");
        mData.registerObserver(this);

        TextView finalScore = findViewById(R.id.finalScoreTextView);
        finalScore.setText(getIntent().getExtras().getString("finalScore"));

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                streak = 0;
                int racerStreak = (int) user.getStatistic(GameConstants.TYPE_RACER, GameConstants.TypeRacerStreak);
                user.setStatistic(GameConstants.TYPE_RACER, GameConstants.TypeRacerStreak, racerStreak + streak);
                Intent intent = new Intent(getApplicationContext(), SaveScoreActivity.class);
                intent.putExtra(GameConstants.USERMANAGER, userManager);
                intent.putExtra(GameConstants.TypeRacerStreak, racerStreak);
                intent.putExtra(GameConstants.gameName, GameConstants.racerName);
                startActivity(intent);
            }
        });

    }

    private void setUserManager(IUserManager newManager) {
        userManager = newManager;
    }

    @Override
    public void onUserDataChanged(String c) {
        setContentView(R.layout.activity_type_racer_end);
        TextView correct = findViewById(R.id.correctnessTV);
        correct.setText(c);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mData.removeObserver(this);
    }
}
