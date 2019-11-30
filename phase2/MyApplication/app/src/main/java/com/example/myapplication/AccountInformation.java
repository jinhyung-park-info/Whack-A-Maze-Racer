package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.UserInfo.IUser;
import com.example.myapplication.UserInfo.UserManager;


import java.awt.font.TextAttribute;

public class AccountInformation extends AppCompatActivity {

    private TextView EditTextUsername;
    private TextView EditTextPassword;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_information);

        EditTextUsername = findViewById(R.id.UsernameDisplay);
        EditTextPassword = findViewById(R.id.PasswordDsiplay);

        Intent intent = getIntent();
        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null){
            setUserManager(user_1);
        }

        displayUsernameAndPassword();
    }

    private void setUserManager(UserManager UserManager){
        userManager = UserManager;
    }

    private void displayUsernameAndPassword() {
        IUser user = userManager.getUser();
        String text = "Your username is: " + user.getEmail();
        EditTextUsername.setText(text);
        String pass = "Your password is: " + user.getPassword();
        EditTextPassword.setText(pass);
    }

    public void goBackToMain(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }
}
