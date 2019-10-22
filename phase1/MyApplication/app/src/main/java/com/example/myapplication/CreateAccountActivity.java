package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;

public class CreateAccountActivity extends AppCompatActivity {

    private final static String TAG = "CreateAccountActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        /*String email = intent.getStringExtra(MainActivity.Username);
        String pass = intent.getStringExtra(MainActivity.Password);*/
        //TextView textView = findViewById(R.id.textView2);
        /*String to_see = email + pass;
        textView.setText(to_see);*/
    }

    public void DoneButton(View view) {
        EditText editText_2 = findViewById(R.id.editText2);
        EditText editText_3 = findViewById(R.id.editText3);
        String username = editText_2.getText().toString();
        String password = editText_3.getText().toString();
        String resultOfValidatingAccount = MainActivity.userAccountManager.createUserAccount(username, password);

        if (resultOfValidatingAccount.equals("")) {
            MainActivity.userAccountManager.addUser(username, password);
            finish();
        }
        else if (resultOfValidatingAccount.contains("Username")) {
            editText_2.setError(resultOfValidatingAccount);
        }
        else if (resultOfValidatingAccount.contains("Password")){
            editText_3.setError(resultOfValidatingAccount);
        }
    }
}



