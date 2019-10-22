package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static String Username = "username";
    public static final String Password = "password";
    public static UserAccountManager userAccountManager;
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.userAccountManager = new UserAccountManager(getFilesDir().getAbsolutePath());
    }

    public void createAccount(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        /*EditText editText_2 =  findViewById(R.id.editText2);
        EditText editText_3 =  findViewById(R.id.editText3);
        String email = editText_2.getText().toString();
        String password = editText_3.getText().toString();*/
        //intent.putExtra(Password, password);
        startActivity(intent);

    }

    public boolean validate(EditText user_text, String username, String password, EditText pass_text){
        User user;

        String resultOfCheckingCredentials = userAccountManager.checkUserCredentialsForLogin(username, password);
        if (resultOfCheckingCredentials.equals("")) {
            return true;
        }
        else {
            if (resultOfCheckingCredentials.contains("Username")){
                user_text.setError(resultOfCheckingCredentials);
            }
            else if (resultOfCheckingCredentials.contains("Password")) {
                pass_text.setError(resultOfCheckingCredentials);
            }
            return false;
        }
    }

    public void LoginButton(View view){
        Intent intent = new Intent(this, GameActivity.class);
        EditText editText_user = (EditText) findViewById(R.id.editText1);
        EditText editText_pass = (EditText) findViewById(R.id.editText);
        String username = editText_user.getText().toString();
        String password = editText_pass.getText().toString();
        intent.putExtra(Username, username);
        intent.putExtra(Password, password);

        //find user returns a user object if it can find one based on the username
        //if it can't, it returns a user object with values (username="", password="", saveStateFile=getFilesDir())
        //to indicate that it's empty. I also created an isEmpty method in user to easily check for this
        User user;
        String resultOfCheckingCredentials = userAccountManager.checkUserCredentialsForLogin(username, password);
        if (resultOfCheckingCredentials.equals("")) {
            startActivity(intent);
        }
        else {
            if (resultOfCheckingCredentials.contains("Username")){
                editText_user.setError(resultOfCheckingCredentials);
            }
            else if (resultOfCheckingCredentials.contains("Password")) {
                editText_pass.setError(resultOfCheckingCredentials);
            }
        }
    }
}