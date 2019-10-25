package com.example.myapplication.WhackAMole;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.GameActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class MoleActivity extends AppCompatActivity {
    static boolean passed;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mole);
        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.Username);
        String password = intent.getStringExtra(MainActivity.Password);
        passed = false;
    }

    public void play(View view) {
        setContentView(new WamView(this));
    }

    public void next(View view){
        Button nextButton = findViewById(R.id.button);
    if (passed) {
        passed = false;
      Intent intent = new Intent(this, GameActivity.class);
      startActivity(intent);
    }else{
        nextButton.setError("Please Pass This Level First");
    }

    }
}
