package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class CreateAccountActivity extends AppCompatActivity {

    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Intent intent = getIntent();
        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null){
            setUserManager(user_1);
        }

    }

    private void setUserManager(UserManager newManager){
        userManager = newManager;
    }

    public boolean validate_2(String username, String password, EditText editText_2, EditText editText_3){
        ArrayList<Boolean> arr = userManager.check_username_and_password(getApplicationContext(), username, password);
        if(arr.get(0)){
            editText_2.setError("Username already exists");
            return false;
        }
        if(username.length() == 0){
            editText_2.setError("Please enter Text");
            return false;
        }
        if(password.length() == 0){
            editText_3.setError("Please enter Text");
            return false;
        }
        if(username.contains(" ")){
            editText_2.setError("Spaces are not allowed in username and passwords");
            return false;
        }
        if(username.contains(",")){
            editText_2.setError("Commas are not allowed in username and passwords");
            return false;
        }
        if(password.contains(" ")){
            editText_3.setError("Spaces are not allowed in username and passwords");
            return false;
        }
        if(password.contains(",")){
            editText_3.setError("Commas are not allowed in username and passwords");
            return false;
        }
        return true;
    }
    public void DoneButton(View view){
        EditText editText_2 =  findViewById(R.id.editText2);
        EditText editText_3 =  findViewById(R.id.editText3);
        String username = editText_2.getText().toString();
        String password = editText_3.getText().toString();
        if(validate_2(username, password, editText_2, editText_3)){
            FileOutputStream fos = null;
            userManager.writeUsernameAndPass(getApplicationContext(), fos, username, password);
            fos = null;
            userManager.writeUsernameAndStatistics(getApplicationContext(), fos, username);
            finish();
        }
    }



}

