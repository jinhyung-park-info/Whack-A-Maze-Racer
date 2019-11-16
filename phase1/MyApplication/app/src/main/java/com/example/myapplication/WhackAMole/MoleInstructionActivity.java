package com.example.myapplication.WhackAMole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.UserManager;

import static com.example.myapplication.MainActivity.USER;

public class MoleInstructionActivity extends AppCompatActivity {

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mole_instruction);

        Intent intent = getIntent();
        User user_1 = (User) intent.getSerializableExtra(USER);
        if (user_1 != null) {
            setUser(user_1);
        }
    }

    public void play_game(View v){
        user.setLoad_moles_stats("0");
        UserManager.update_statistics(this, user);
        Intent intent = new Intent(this, MoleInstructionActivity.class);
        intent.putExtra(USER, user);
        startActivity(intent);
    }

    private void setUser(User new_user) {
        user = new_user;
    }
}
