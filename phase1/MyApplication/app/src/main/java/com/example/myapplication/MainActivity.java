package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static String Username = "username";
    public static final String Password = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if (username.length() == 0){
            user_text.setError("Please enter some text");
            return false;
        }
        if(password.length() == 0){
            pass_text.setError("Please enter some text");
            return false;
        }
        if(!(User.map.containsKey(username))){
            user_text.setError("Username does not exist");
            return false;
        }
        if(!(User.map.get(username).equals(password))){
            pass_text.setError("Invalid password");
            return false;
        }
        return true;
    }

    public void LoginButton(View view){
        Intent intent = new Intent(this, GameActivity.class);
        EditText editText_user = (EditText) findViewById(R.id.editText1);
        EditText editText_pass = (EditText) findViewById(R.id.editText);
        String username = editText_user.getText().toString();
        String password = editText_pass.getText().toString();
        intent.putExtra(Username, username);
        intent.putExtra(Password, password);
        if(username.length() == 0){
            editText_user.setError("Please enter text");
        }
        if(password.length() == 0){
            editText_pass.setError("Please enter text");
        }
        if(!(User.map.containsKey(username)) && username.length() != 0){
            editText_user.setError("Username does not exist");
        }
        if (User.map.get(username) != null) {
            if (!(User.map.get(username).equals(password))) {
                editText_pass.setError("Invalid password");
            }
        }
        if(password.length() != 0 && username.length() != 0 && User.map.containsKey(username) && User.map.get(username).equals(password))
            startActivity(intent);
    }
}