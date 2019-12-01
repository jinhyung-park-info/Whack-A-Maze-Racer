package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.UserInfo.IUser;
import com.example.myapplication.UserInfo.User;
import com.example.myapplication.UserInfo.UserManager;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }

    public void PasswordCheck(View view){
        UserManager userManager = new UserManager();
        EditText username = findViewById(R.id.UsernameCheck);
        IUser user = new User(username.getText().toString());
        Object validate = userManager.getOrChangePassword(getApplicationContext(),
                user, null, GameConstants.getPassword);
        if(validate instanceof String){
            TextView Password = findViewById(R.id.PasswordCheck);
            String text = "Your Password is: " + validate.toString();
            Password.setText(text);
        }else{
            username.setError("Incorrect username was entered, only alphanumeric characters  are allowed");
        }
    }



    public void backToLogin(View view){
        finish();
    }
}
