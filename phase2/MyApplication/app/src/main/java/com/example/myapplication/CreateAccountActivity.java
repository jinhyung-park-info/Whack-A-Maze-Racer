package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.UserInfo.LoginPresenter;
import com.example.myapplication.UserInfo.UserManager;

import java.io.FileOutputStream;
import java.util.ArrayList;


public class CreateAccountActivity extends AppCompatActivity {

    private UserManager userManager;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Intent intent = getIntent();
        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null){
            setUserManager(user_1);
        }
       loginPresenter = new LoginPresenter();

    }

    private void setUserManager(UserManager usermanager){
        userManager = usermanager;
    }

    public void DoneButton(View view){
        EditText editTextUsername =  findViewById(R.id.answerEditText);
        EditText editTextPassword =  findViewById(R.id.editText3);
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        if(loginPresenter.validateCredentialsForAccountCreation(getApplicationContext(), username,
                password, editTextUsername, editTextPassword)) {
            /*FileOutputStream fos = null;
            userManager.writeUsernameAndPass(getApplicationContext(), fos, username, password);
            fos = null;
            userManager.writeUsernameAndStatistics(getApplicationContext(), fos, username);*/
            userManager.writeInfoToFile(getApplicationContext(), username, password, GameConstants.USER_FILE);
            userManager.writeInfoToFile(getApplicationContext(), username, password, GameConstants.USER_STATS_FILE);
            finish();
        }
    }



}

