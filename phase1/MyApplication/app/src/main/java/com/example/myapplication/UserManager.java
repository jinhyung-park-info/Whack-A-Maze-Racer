package com.example.myapplication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class UserManager implements Serializable {

    private WriteAndCheck writeAndCheck;
    private SetAndUpdate setAndUpdate;

    /**
     * The user this class manages
     */
    private User user;

    /**
     * Gets the user this class manages
     *
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * Set the user this class manages
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    UserManager() {
        this.writeAndCheck = new WriteAndCheck();
        this.setAndUpdate = new SetAndUpdate();
    }

    /**
     * Verifies if the given username and password combination is valid
     *
     * @param context  of this device
     * @param username of the user
     * @param password of the user
     * @return username password combo is valid?
     */
    ArrayList<Boolean> checkUsernameAndPassword(Context context, String username, String password){
        return writeAndCheck.checkUsernameAndPassword(context, username, password);
    }

    /**
     * Writes a username and password to the file
     * @param context of the device
     * @param fos file output stream
     * @param username of the user
     * @param password of the user
     */
    //assuming alphanumeric characters only
    void writeUsernameAndPass(Context context, FileOutputStream fos, String username, String password) {
        writeAndCheck.writeUsernameAndPass(context, fos, username, password);
    }

    /**
     * Writes the statistics of a new user to the file.
     *
     * @param context of the device
     * @param fos file output stream
     * @param username of the user
     */
    void writeUsernameAndStatistics(Context context, FileOutputStream fos, String username) {
        writeAndCheck.writeUsernameAndStatistics(context, fos, username);
    }

    /**
     * Set the statistics of the user in the file
     *
     * @param context of the device
     * @param user
     */
    void setStatistics(Context context, User user){
        setAndUpdate.setStatistics(context, user);
    }

    /**
     * Update the statistics of a user in the file
     *
     * @param context of the device
     * @param user
     */
    public  void updateStatistics(Context context, User user) {
        setAndUpdate.updateStatistics(context, user);

    }

    public ArrayList<User> getListOfAllUsers(Context context, User user){
        InputStream fis = null;
        ArrayList<User> arr = new ArrayList<>();
        try {
            fis = context.openFileInput(MainActivity.Stats_file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                int index_of_comma = text.indexOf(",");
                String username = text.substring(0, index_of_comma);
                if (user.getEmail().equals(username)){
                }else{
                    User NewUser = new User(username);
                    new SetAndUpdate().helper(text, NewUser);
                    arr.add(NewUser);
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
        return arr;
    }

    /**
     * Add a statistic at a specific place for previous accounts.
     *
     * @param context  of the device
     * @param position in the line of the statistic to be added
     * @param numTimes the number of times a statistic will be added
     */
    public void addStatisticAtSpecificPlaceForPreviousAccounts(Context context, int position, int numTimes) {
        FileInputStream fis = null;
        StringBuilder sb = new StringBuilder();

        try {
            fis = context.openFileInput(MainActivity.Stats_file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                int numCommas = GameConstants.countOccurrences(text, ',');
                if (numCommas != GameConstants.TOTAL_NUM_OF_STATISTICS) {
                    int b = 0;
                    int y = 0;
                    for (int i = 0; i <= text.length(); i++) {
                        if (b == position - 1) {
                            y = i - 1;
                            break;
                        }
                        if (text.charAt(i) == ',') {
                            b += 1;
                        }
                    }
                    int indexComma = y;
                    String firstHalf = text.substring(0, y);
                    String secondHalf = text.substring(y);
                    String middle = new String(new char[numTimes]).replace(
                            "\0", ", 0");
                    String newText = firstHalf + middle + secondHalf;
                    sb.append("\n");
                    sb.append(newText);
                } else {
                    sb.append(text).append("\n");
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
        // write the new string with the replaced line OVER the same file
        FileOutputStream fileOut = null;
        try {
            fileOut = context.openFileOutput(MainActivity.Stats_file, MODE_PRIVATE);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            if (fileOut != null)
                fileOut.write(sb.toString().getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            if (fileOut != null)
                fileOut.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


}
