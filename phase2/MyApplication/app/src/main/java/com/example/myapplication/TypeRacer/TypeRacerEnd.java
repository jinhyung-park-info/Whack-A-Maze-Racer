package com.example.myapplication.TypeRacer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.GameActivity;
import com.example.myapplication.GameConstants;
import com.example.myapplication.Maze.MazeCustomizationActivity;
import com.example.myapplication.Maze.MazeInstructionsActivity;
import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.UserManager;

public class TypeRacerEnd extends AppCompatActivity {
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_racer_end);

        Intent intent = getIntent();
        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null){
            setUserManager(user_1);
        }

        TextView finalScore = findViewById(R.id.finalScoreTextView);
        finalScore.setText(getIntent().getExtras().getString("finalScore"));

        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra(GameConstants.USERMANAGER, userManager);
                startActivity(intent);
            }
        });

    }
    private void setUserManager(UserManager newManager){
        userManager = newManager;
    }
}
