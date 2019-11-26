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

    /**
     * Verifies if the given username and password combination is valid
     *
     * @param context  of this device
     * @param username of the user
     * @param password of the user
     * @return username password combo is valid?
     */
    ArrayList<Boolean> checkUsernameAndPassword(Context context, String username, String password){
        InputStream fis = null;
        ArrayList<Boolean> arr = new ArrayList<Boolean>();
        try {
            fis = context.openFileInput(MainActivity.FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
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
            fos = context.openFileOutput(MainActivity.FILE_NAME, mode);
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
     * @param context of the device
     * @param fos file output stream
     * @param username of the user
     * @param password of the user
     */
    //assuming alphanumeric characters only
    void writeUsernameAndPass(Context context, FileOutputStream fos, String username, String password) {
        File file = new File(context.getFilesDir(), MainActivity.FILE_NAME);
        if(file.exists()){
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
            fos = context.openFileOutput(MainActivity.Stats_file, mode);
            try {
                fos.write(username.getBytes());
                String s = new String(new char[GameConstants.TotalNumOfStastics]).replace(
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
     * @param context of the device
     * @param fos file output stream
     * @param username of the user
     */
    void writeUsernameAndStatistics(Context context, FileOutputStream fos, String username) {
        File file = new File(context.getFilesDir(),MainActivity.Stats_file);
        if(file.exists()){
            writeUsernameAndStatisticsHelper(context, fos, username, MODE_APPEND);
        } else { //file does not exist make one and write to it
            writeUsernameAndStatisticsHelper(context, fos, username, MODE_PRIVATE);
        }
    }

    /**
     * Set the statistics of the user in the file
     *
     * @param context of the device
     * @param user
     */
    void setStatistics(Context context, User user){
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(MainActivity.Stats_file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                int index_of_first_comma = text.indexOf(",");
                String other_username = text.substring(0, index_of_first_comma);
                if (user.getEmail().equals(other_username)) {
                    user = helper(text, user);
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
    }

    /**
     * Update the statistics of a user in the file
     *
     * @param context of the device
     * @param user
     */
    public  void updateStatistics(Context context, User user) {
        FileInputStream fis = null;
        StringBuilder sb = new StringBuilder();

        try {
            fis = context.openFileInput(MainActivity.Stats_file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                int index_of_first_comma = text.indexOf(",");
                String other_username = text.substring(0, index_of_first_comma);
                if (user.getEmail().equals(other_username)) {
                    String new_text = other_username + ", " + user.getLastPlayedLevel() + ", "  + user.getOverallScore()
                            + ", " + user.getStatistic(GameConstants.NameGame2, GameConstants.TypeRacerStreak)
                            + ", " + user.getStatistic(GameConstants.NameGame3, GameConstants.NumMazeGamesPlayed)
                            + ", "  +  user.getStatistic(GameConstants.NameGame1, GameConstants.MoleHit)
                            + ", " + user.getStatistic(GameConstants.NameGame1, GameConstants.MoleStats) + "\n";
                    sb.append(new_text);
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

    /**
     * Helper method to transfer the user statistics in the file to the user object
     *
     * @param line in the file
     * @param user
     * @return the updated user
     */
    private User helper(String line, User user) {
        int indexOfFirstComma = line.indexOf(",");
        int indexOfSecondComma = line.indexOf(",", indexOfFirstComma + 1);
        int indexOfThirdComma = line.indexOf(",", indexOfSecondComma + 1);
        int indexOfForthComma = line.indexOf(",", indexOfThirdComma + 1);
        int indexOfFifthComma = line.indexOf(",", indexOfForthComma + 1);
        int indexOfSixthComma = line.indexOf(",", indexOfFifthComma + 1);
        int lastPlayedLevel = Integer.parseInt(line.substring(indexOfFirstComma + 2, indexOfSecondComma));
        int overallScore = Integer.parseInt(line.substring(indexOfSecondComma + 2, indexOfThirdComma));
        int streaks = Integer.parseInt(line.substring(indexOfThirdComma + 2, indexOfForthComma));
        int numMazeGame = Integer.parseInt(line.substring(indexOfForthComma + 2, indexOfFifthComma));
        int moleHit = Integer.parseInt(line.substring(indexOfFifthComma + 2, indexOfSixthComma));
        String loadMolesStats = line.substring(indexOfSixthComma + 2);
        Object[] typeRacer = new Object[]{GameConstants.NameGame2, GameConstants.TypeRacerStreak, streaks};
        Object[] whackAMole = new Object[]{GameConstants.NameGame1, GameConstants.MoleStats, loadMolesStats, GameConstants.MoleHit, moleHit};
        Object[] maze = new Object[]{GameConstants.NameGame3, GameConstants.NumMazeGamesPlayed, numMazeGame};
        ArrayList<Object[]> arrayOfGameStats = new ArrayList<>();
        arrayOfGameStats.add(typeRacer);
        arrayOfGameStats.add(maze);
        arrayOfGameStats.add(whackAMole);
        user.setStatisticsInMap(arrayOfGameStats);
        user.setLastPlayedLevel(lastPlayedLevel);
        user.setOverallScore(overallScore);
        return user;
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
                    helper(text, NewUser);
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

    /*private void AddXAmountOfStatisticToPreviousAccounts(Context context, int position, int numTimes){

    }
    */

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
                int numCommas = countOccurrences(text, ',');
                if (numCommas != GameConstants.TotalNumOfStastics) {
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

    /**
     * Count the occurences of a character in a line
     *
     * @param line
     * @param character
     * @return
     */
    public static int countOccurrences(String line, char character)
    {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == character)
            {
                count++;
            }
        }
        return count;
    }

}
