package com.example.myapplication.Maze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.UserManager;

import static com.example.myapplication.MainActivity.USER;

public class MazeInstructionsActivity extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze_instructions);

        Intent intent = getIntent();
        User user_1 = (User) intent.getSerializableExtra(USER);
        if (user_1 != null) {
            setUser(user_1);
        }
    }

    public void onStartGameClicked(View v) {
        UserManager.update_statistics(this, user);
        Intent intent = new Intent(this, MazeCustomizationActivity.class);
        intent.putExtra(USER, user);
        startActivity(intent);
    }

    private void setUser(User new_user) {
        user = new_user;
    }
}
