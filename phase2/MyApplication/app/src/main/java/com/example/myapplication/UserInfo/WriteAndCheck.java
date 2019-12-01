package com.example.myapplication.UserInfo;

import android.content.Context;

import com.example.myapplication.GameConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class WriteAndCheck implements Serializable {
    /**
     *
     * @param context of the device
     * @param username entered by the user
     * @param password entered by the user
     * @return an Arraylist consisting of 2 boolean values, with the first index representing weather
     * or not the username is in the file and the second index representing the password
     */
    ArrayList<Boolean> checkUsernameAndPassword(Context context, String username, String password) {
        InputStream fis = null;
        ArrayList<Boolean> arr = new ArrayList<Boolean>();
        try {
            fis = context.openFileInput(GameConstants.USER_FILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                int index_of_comma = text.indexOf(",");
                String other_username = text.substring(0, index_of_comma);
                if (username.equals(other_username)) {
                    arr.add(true);
                    int index_of_space = text.indexOf(" ");
                    String other_password = text.substring(index_of_space + 1);
                    if (other_password.equals(password)) {
                        arr.add(true);
                        break;
                    } else {
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

    private void writeInfoToFileHelper(Context context, String username, String password, String fileName, int mode){
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, mode);
            try {
                if(fileName.equals(GameConstants.USER_STATS_FILE)) {
                    /*fos.write(username.getBytes());
                    String s = new String(new char[GameConstants.TOTAL_NUM_OF_STATISTICS]).replace(
                            "\0", ", 0");
                    fos.write(s.getBytes());
                    fos.write("\n".getBytes());*/
                    String stat = new String(new char[GameConstants.TOTAL_NUM_OF_STATISTICS]).replace(
                            "\0", ", 0");
                    String text = username + stat + "\n";
                    fos.write(text.getBytes());
                }else if(fileName.equals(GameConstants.USER_FILE)){
                    String text = username + ", " + password + "\n";
                    /*fos.write(username.getBytes());
                    fos.write(", ".getBytes());
                    fos.write(password.getBytes());
                    fos.write("\n".getBytes());*/
                    fos.write(text.getBytes());
                }
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

    public void writeInfoToFile(Context context, String username, String password, String fileName){
        File file = new File(context.getFilesDir(),fileName);
        if (file.exists()) {
            writeInfoToFileHelper(context, username,password, fileName, MODE_APPEND);
        } else { //file does not exist make one and write to it
            writeInfoToFileHelper(context, username,password,fileName, MODE_PRIVATE);
        }
    }

}
