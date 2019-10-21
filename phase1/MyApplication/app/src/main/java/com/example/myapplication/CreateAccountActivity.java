package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

public class CreateAccountActivity extends AppCompatActivity {

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
    public void DoneButton(View view){
        EditText editText_2 =  findViewById(R.id.editText2);
        EditText editText_3 =  findViewById(R.id.editText3);
        String username = editText_2.getText().toString();
        String password = editText_3.getText().toString();
        if(username.length() == 0){
            editText_2.setError("Please enter Text");
        }
        if(password.length() == 0){
            editText_3.setError("Please enter Text");
        }
        if(User.map.containsKey(username)){
            editText_2.setError("Username already exists, please pick another username");
        }
        if(username.length() != 0 && password.length() != 0 && !(User.map.containsKey(username))){
            new User(username, password);
            finish();
        }
    }
}

class User{
    private String email;
    private String password;
    public static HashMap<String, String> map = new HashMap<>();


    User(String Email, String Password){
        this.email = Email;
        this.password = Password;
        map.put(email, password);
    }

    public String getEmail() {
        return email;
    }
}

