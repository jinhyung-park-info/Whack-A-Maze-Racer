package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.UserInfo.ILoginView;
import com.example.myapplication.UserInfo.IUser;
import com.example.myapplication.UserInfo.LoginPresenter;
import com.example.myapplication.UserInfo.User;
import com.example.myapplication.UserInfo.UserManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ILoginView {

    private UserManager userManager;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userManager = new UserManager();
        loginPresenter = new LoginPresenter();
        System.out.println(getApplicationContext().getFilesDir());

    }

    public void createAccount(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);

    }


    public void LoginButton(View view){
        Intent intent = new Intent(this, GameActivity.class);
        EditText editTextUser = findViewById(R.id.editText1);
        EditText editTextPass = findViewById(R.id.editText);
        String username = editTextUser.getText().toString();
        String password = editTextPass.getText().toString();
        if (loginPresenter.validateCredentialsForLogin(getApplicationContext(), username, password,
                editTextUser, editTextPass)){
            /*IUser user = new User(username);
            user.setPassword(password);
            userManager.setStatistics(getApplicationContext(), user);
            userManager.setUser(user);*/
            //System.out.println(user.getMap());
            onLoginSuccess(username, password);
            intent.putExtra(GameConstants.USERMANAGER, userManager);
            startActivity(intent);
        }
    }
    public void exitApplication(View view){
        moveTaskToBack(true);
    }

    public void forgotPassword(View view){
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

    @Override
    public void onLoginSuccess(String username, String password) {
        IUser user = new User(username);
        user.setPassword(password);
        userManager.setStatistics(getApplicationContext(), user);
        userManager.setUser(user);
    }
}
