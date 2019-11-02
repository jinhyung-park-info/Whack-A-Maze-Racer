package com.example.myapplication.TypeRacer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Maze.MazeCustomizationActivity;
import com.example.myapplication.R;
import com.example.myapplication.User;

import static com.example.myapplication.MainActivity.USER;

public class TypeRacerEnd extends AppCompatActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_racer_end);

        Intent previous = getIntent();
        User user_1 = (User) previous.getSerializableExtra(USER);
        if (user_1 != null){
            setUser(user_1);
        }

        TextView finalScore = findViewById(R.id.finalScoreTextView);
        finalScore.setText(getIntent().getExtras().getString("finalScore"));

        Button nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MazeCustomizationActivity.class);
                intent.putExtra(USER, user);
                startActivity(intent);
            }
        });

    }
    private void setUser(User new_user){
        user = new_user;
    }
}
