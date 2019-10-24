package com.example.myapplication.WhackAMole;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class MoleActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mole);
        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.Username);
        String password = intent.getStringExtra(MainActivity.Password);
    }

    public void play(View view) {
        setContentView(new WamView(this));
    }
}
