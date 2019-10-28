package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

    }
    public void DoneButton(View view){
        EditText editText_2 =  findViewById(R.id.editText2);
        EditText editText_3 =  findViewById(R.id.editText3);
        String username = editText_2.getText().toString();
        String password = editText_3.getText().toString();
        boolean a = true;
        if(UserManager.check_duplicate_username(getApplicationContext(), username)){
            a = false;
            editText_2.setError("Username already exists");
        }
        if(username.length() == 0){
            editText_2.setError("Please enter Text");
        }
        if(password.length() == 0){
            editText_3.setError("Please enter Text");
        }
        if(username.contains(" ")){
            editText_2.setError("Spaces are not allowed in username and passwords");
        }
        if(username.contains(",")){
            editText_2.setError("Commas are not allowed in username and passwords");
        }
        if(password.contains(" ")){
            editText_3.setError("Spaces are not allowed in username and passwords");
        }
        if(password.contains(",")){
            editText_3.setError("Commas are not allowed in username and passwords");
        }
        if(username.length() != 0 && password.length() != 0  && !(username.contains(" ")) &&
                !(username.contains(",")) && !(password.contains(" ")) && !(password.contains(","))
                && a){
            FileOutputStream fos = null;
            UserManager.write_username_and_pass(getApplicationContext(), fos, username, password);
            fos = null;
            UserManager.write_username_and_statistics(getApplicationContext(), fos, username);
            finish();
        }
    }



}

