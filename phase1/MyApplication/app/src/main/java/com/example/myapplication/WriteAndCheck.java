package com.example.myapplication;

import android.content.Context;

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

    /**
     * Helper method to write a username and password to the file
     *
     * @param context  of the device
     * @param fos      file output stream
     * @param username of the user
     * @param password of the user
     * @param mode     of interaction with file
     */
    private void writeUsernameAndPassHelper(Context context, FileOutputStream fos, String username,
                                            String password, int mode) {
        try {
            fos = context.openFileOutput(GameConstants.USER_FILE, mode);
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

    /**
     * Writes a username and password to the file
     *
     * @param context  of the device
     * @param fos      file output stream
     * @param username of the user
     * @param password of the user
     */
    //assuming alphanumeric characters only
    void writeUsernameAndPass(Context context, FileOutputStream fos, String username, String password) {
        File file = new File(context.getFilesDir(), GameConstants.USER_FILE);
        if (file.exists()) {
            writeUsernameAndPassHelper(context, fos, username, password, MODE_APPEND);
        } else { //file does not exist make one and write to it
            writeUsernameAndPassHelper(context, fos, username, password, MODE_PRIVATE);
        }
    }

    /**
     * Helper method to write statistics of a new user to the file
     *
     * @param context  of the device
     * @param fos      file output stream
     * @param username of the user
     * @param mode     of interaction with file
     */
    private void writeUsernameAndStatisticsHelper(Context context, FileOutputStream fos, String username, int mode) {
        try {
            fos = context.openFileOutput(GameConstants.USER_STATS_FILE, mode);
            try {
                fos.write(username.getBytes());
                String s = new String(new char[GameConstants.TOTAL_NUM_OF_STATISTICS]).replace(
                        "\0", ", 0");
                fos.write(s.getBytes());
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

    /**
     * Writes the statistics of a new user to the file.
     *
     * @param context  of the device
     * @param fos      file output stream
     * @param username of the user
     */
    void writeUsernameAndStatistics(Context context, FileOutputStream fos, String username) {
        File file = new File(context.getFilesDir(), GameConstants.USER_STATS_FILE);
        if (file.exists()) {
            writeUsernameAndStatisticsHelper(context, fos, username, MODE_APPEND);
        } else { //file does not exist make one and write to it
            writeUsernameAndStatisticsHelper(context, fos, username, MODE_PRIVATE);
        }
    }

}
