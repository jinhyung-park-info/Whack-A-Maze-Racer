package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.UserInfo.LoginPresenter;
import com.example.myapplication.UserInfo.UserManagerFacade;


public class CreateAccountActivity extends AppCompatActivity {

    private UserManagerFacade userManagerFacade;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Intent intent = getIntent();
        UserManagerFacade user_1 = (UserManagerFacade) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null){
            setUserManagerFacade(user_1);
        }
       loginPresenter = new LoginPresenter();

    }

    private void setUserManagerFacade(UserManagerFacade usermanager){
        userManagerFacade = usermanager;
    }

    public void DoneButton(View view){
        EditText editTextUsername =  findViewById(R.id.answerEditText);
        EditText editTextPassword =  findViewById(R.id.editText3);
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        if(loginPresenter.validateCredentialsForAccountCreation(getApplicationContext(), username,
                password, editTextUsername, editTextPassword)) {
            userManagerFacade.writeInfoToFile(getApplicationContext(), username, password, GameConstants.USER_FILE);
            userManagerFacade.writeInfoToFile(getApplicationContext(), username, password, GameConstants.USER_STATS_FILE);
            finish();
        }
    }



}

