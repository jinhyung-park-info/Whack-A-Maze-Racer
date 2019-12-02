package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.UserInfo.IUser;
import com.example.myapplication.UserInfo.User;
import com.example.myapplication.UserInfo.UserManagerFacade;

public class ForgotPasswordActivity extends AppCompatActivity {

    UserManagerFacadeBuilder umfb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        this.umfb = new UserManagerFacadeBuilder();
    }

    private UserManagerFacade buildUserManagerFacade(){
        umfb.buildWAC();
        umfb.buildRAU();
        umfb.buildHAC();
        umfb.buildUMF();
        return umfb.getUmf();
    }

    public void PasswordCheck(View view){
        UserManagerFacade userManagerFacade = buildUserManagerFacade();
        EditText username = findViewById(R.id.UsernameCheck);
        IUser user = new User(username.getText().toString());
        Object validate = userManagerFacade.getOrChangePassword(getApplicationContext(),
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
