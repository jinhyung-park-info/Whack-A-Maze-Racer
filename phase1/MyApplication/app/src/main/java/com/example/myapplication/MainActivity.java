package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private UserManager userManager;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final  String  FILE_NAME = "user_data.txt";
    public static final String USER = "user";
    public static final String Stats_file = "user_stats.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userManager = new UserManager();
    }

    public void createAccount(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);

    }

    boolean validate(String username, String password, EditText editText_user ,EditText editText_pass){
        if(username.length() == 0 && password.length() == 0) {
            editText_user.setError("Please enter text");
            editText_pass.setError("Please enter text;");
            return false;
        }
        if(username.length() == 0){
            editText_user.setError("Please enter text");
            return false;
        }
        if(password.length() == 0){
            editText_pass.setError("Please enter text");
            return false;
        }
        if(username.contains(" ")){
            editText_user.setError("Space is not allowed in username and passwords");
            return false;
        }
        if(username.contains(",")){
            editText_user.setError("Commas are not allowed in username and passwords");
            return false;
        }
        if(password.contains(" ")){
            editText_pass.setError("Space is not allowed in username and passwords");
            return false;
        }
        if(password.contains(",")){
            editText_pass.setError("Commas are not allowed in username and passwords");
            return false;
        }
        ArrayList<Boolean> validation = userManager.check_username_and_password(getApplicationContext(), username, password);
        if (validation.get(0)){
            if(!(validation.get(1))){
                editText_pass.setError("Incorrect Password");
                return false;
            }
        }else{
            editText_user.setError("Username does not exist");
            return false;
        }
        return true;
    }


    public void LoginButton(View view){
        Intent intent = new Intent(this, GameActivity.class);
        EditText editText_user = findViewById(R.id.editText1);
        EditText editText_pass = findViewById(R.id.editText);
        String username = editText_user.getText().toString();
        String password = editText_pass.getText().toString();
        if (validate(username, password, editText_user, editText_pass)){
            User user = new User(username);
            user.setPassword(password);
            //userManager = new UserManager(user);
            //if (GameConstants.limiter == 0) {
                //GameConstants.limiter = 1;
            //userManager.AddStatisticAtSpecificPlaceForPreviousAccounts(getApplicationContext(), 3, 1);
            //}
            userManager.setStatistics(getApplicationContext(), user);
            userManager.setUser(user);
            System.out.println(user.getMap());
            intent.putExtra(GameConstants.USERMANAGER, userManager);
            startActivity(intent);
        }
    }
    public void exit_application(View view){
        moveTaskToBack(true);
    }

}
