package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        /*String email = intent.getStringExtra(MainActivity.Username);
        String pass = intent.getStringExtra(MainActivity.Password);*/
        //TextView textView = findViewById(R.id.textView2);
        /*String to_see = email + pass;
        textView.setText(to_see);*/
    }
    public void DoneButton(View view){
        EditText editText_2 =  findViewById(R.id.answerEditText);
        EditText editText_3 =  findViewById(R.id.editText3);
        String username = editText_2.getText().toString();
        String password = editText_3.getText().toString();
        //System.out.println(check_duplicate_username(username));
        boolean a = true;
        if(check_duplicate_username(username)){
            a = false;
            editText_2.setError("Username already exists");
        }
        if(username.length() == 0){
            editText_2.setError("Please enter Text");
        }
        if(password.length() == 0){
            editText_3.setError("Please enter Text");
        }
        if(username.contains(" ")){
            editText_2.setError("Spaces are not allowed in username and passwords");
        }
        if(username.contains(",")){
            editText_2.setError("Commas are not allowed in username and passwords");
        }
        if(password.contains(" ")){
            editText_3.setError("Spaces are not allowed in username and passwords");
        }
        if(password.contains(",")){
            editText_3.setError("Commas are not allowed in username and passwords");
        }
        if(username.length() != 0 && password.length() != 0  && !(username.contains(" ")) &&
                !(username.contains(",")) && !(password.contains(" ")) && !(password.contains(","))
                && a){
            FileOutputStream fos = null;
            write_to_file(fos, username, password);
            new User(username, password);
            finish();
        }
    }

    public void write_to_file(FileOutputStream fos, String username, String password) {
        File file = new File(getApplicationContext().getFilesDir(),"user_data.txt");
        if(file.exists()){
            try {
                fos = openFileOutput(MainActivity.FILE_NAME, MODE_APPEND);
                try {
                    fos.write(username.getBytes());
                    fos.write(", ".getBytes());
                    fos.write(password.getBytes());
                    fos.write("\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else { //file does not exist make one and write to it
            try {
                fos = openFileOutput(MainActivity.FILE_NAME, MODE_PRIVATE);
                try {
                    fos.write(username.getBytes());
                    fos.write(", u".getBytes());
                    fos.write(password.getBytes());
                    fos.write("\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public boolean check_duplicate_username(String username){
        FileInputStream fis = null;

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
                    return true;
                }
                //sb.append(text).append("\n");
            }

            //System.out.println(sb);

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
        return false;
    }
}

class User implements Serializable {
    private String email;
    private String password;
    public static HashMap<String, String> map = new HashMap<>();


    User(String Email, String Password){
        this.email = Email;
        this.password = Password;
        map.put(email, password);
    }

    public String getEmail() {
        return email;
    }
}



