package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static String Username = "username";
    public static final String Password = "password";
    public static final  String  FILE_NAME = "user_data.txt";
    public static final String USER = "user";
    public static final String Stats_file = "user_stats.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createAccount(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);

    }


    public void LoginButton(View view){
        Intent intent = new Intent(this, GameActivity.class);
        EditText editText_user = (EditText) findViewById(R.id.editText1);
        EditText editText_pass = (EditText) findViewById(R.id.editText);
        String username = editText_user.getText().toString();
        String password = editText_pass.getText().toString();
        if(username.length() == 0){
            editText_user.setError("Please enter text");
        }
        if(password.length() == 0){
            editText_pass.setError("Please enter text");
        }
        if(username.contains(" ")){
            editText_user.setError("Space is not allowed in username and passwords");
        }
        if(username.contains(",")){
            editText_user.setError("Commas are not allowed in username and passwords");
        }
        if(password.contains(" ")){
            editText_pass.setError("Space is not allowed in username and passwords");
        }
        if(password.contains(",")){
            editText_pass.setError("Commas are not allowed in username and passwords");
        }
        ArrayList<Boolean> validation = UserManager.check_username_and_password(getApplicationContext(), username, password);
        if (validation.get(0)){
            if(!(validation.get(1))){
                editText_pass.setError("Incorrect Password");
            }
        }else{
            editText_user.setError("Username does not exist");
        }
        if (password.length() != 0  && username.length() != 0  && validation.get(0)
                && validation.get(1)) {
            User user = new User(username, password);
            UserManager.set_statistics(getApplicationContext(), username, user);
            intent.putExtra(USER, user);
            startActivity(intent);
        }
    }
    public void exit_application(View view){
        moveTaskToBack(true);
    }

}
