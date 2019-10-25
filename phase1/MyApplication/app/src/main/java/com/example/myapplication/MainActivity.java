package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.WhackAMole.Mole;
import com.example.myapplication.WhackAMole.MoleActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static String Username = "username";
    public static final String Password = "password";
    public static final  String  FILE_NAME = "user_data.txt";
    public static final String USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    /*File file = new File("C:\\Users\\asjad\\newfile.txt");

        try {
            if (file.createNewFile()) {
                System.out.println("File create");
            } else {
                System.out.println("File already exists!");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }*/

    /*File user_info = new File("user_data.txt");
      try {
          user_info.createNewFile();
    System.out.println("made file" + user_info);
      } catch (IOException e) {
          e.printStackTrace();
      }
      try {
          FileOutputStream oFile = new FileOutputStream(user_info, false);
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }*/
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

    //the first index will represent if username is in the file and the second index will represent
    // the password
    public ArrayList<Boolean> check_username_and_password(String username, String password){
        FileInputStream fis = null;
        ArrayList<Boolean> arr = new ArrayList<Boolean>();
        try {
            fis = openFileInput(MainActivity.FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            //StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                int index_of_comma = text.indexOf(",");
                String other_username = text.substring(0, index_of_comma);
                if (username.equals(other_username)){
                    arr.add(true);
                    int index_of_space = text.indexOf(" ");
                    String other_password = text.substring(index_of_space + 1);
                    if(other_password.equals(password)){
                        arr.add(true);
                        break;
                    }else{
                        arr.add(false);
                        break;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        arr.add(false);
        arr.add(false);
        return arr;
    }

    public void LoginButton(View view){
        Intent intent = new Intent(this, MoleActivity.class);
        EditText editText_user = (EditText) findViewById(R.id.editText1);
        EditText editText_pass = (EditText) findViewById(R.id.editText);
        String username = editText_user.getText().toString();
        String password = editText_pass.getText().toString();
        intent.putExtra(Username, username);
        intent.putExtra(Password, password);
        //User user = new User(username, password);
        //intent.putExtra(USER, user);
        if(username.length() == 0){
            editText_user.setError("Please enter text");
        }
        if(password.length() == 0){
            editText_pass.setError("Please enter text");
        }
        if(username.contains(" ")){
            editText_user.setError("Space is not allowed in username and passwords");
        }
        if(username.contains(",")){
            editText_user.setError("Commas are not allowed in username and passwords");
        }
        if(password.contains(" ")){
            editText_pass.setError("Space is not allowed in username and passwords");
        }
        if(password.contains(",")){
            editText_pass.setError("Commas are not allowed in username and passwords");
        }
        ArrayList<Boolean> validation = check_username_and_password(username, password);
        if (validation.get(0)){
            if(!(validation.get(1))){
                editText_pass.setError("Incorrect Password");
            }
        }else{
            editText_user.setError("Username does not exist");
        }
        if (password.length() != 0  && username.length() != 0  && validation.get(0)
                && validation.get(1)) {
            startActivity(intent);
        }
    }

}
