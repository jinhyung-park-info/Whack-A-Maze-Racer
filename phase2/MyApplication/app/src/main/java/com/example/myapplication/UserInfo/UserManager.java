package com.example.myapplication.UserInfo;

import android.content.Context;

import com.example.myapplication.GameConstants;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;

public class UserManager implements Serializable {

    private WriteAndCheck writeAndCheck;
    private ReadAndUpdate setAndUpdate;

    /**
     * The user this class manages
     */
    private IUser user;

    /**
     * Gets the user this class manages
     *
     * @return user
     */
    public IUser getUser() {
        return user;
    }

    /**
     * Set the user this class manages
     *
     * @param user that is managed
     */
    public void setUser(IUser user) {
        this.user = user;
    }

    public UserManager() {
        this.writeAndCheck = new WriteAndCheck();
        this.setAndUpdate = new ReadAndUpdate();
    }

    /**
     * Verifies if the given username and password combination is valid
     *
     * @param context  of this device
     * @param username of the user
     * @param password of the user
     * @return an ArrayList with the first two values being a boolean with the first value representing
     * weather or not the username is in the file, and the second also being a boolean representing
     * weather or not the password is correct.
     */
    ArrayList<Boolean> checkUsernameAndPassword(Context context, String username, String password) {
        return writeAndCheck.checkUsernameAndPassword(context, username, password);
    }

    /**
     * Update the statistics of a user in the file
     *
     * @param context of the device
     * @param user    that is managed
     */
    public void setOrUpdateStatistics(Context context, IUser user, String setOrUpdate) {
        setAndUpdate.setOrUpdateStatistics(context, user, setOrUpdate);

    }

    /**
     * @param context of the device
     * @param user    the current user which will not be in the ArrayList
     * @return a list of all the users present in the file except the current user playing the game.
     */

    public ArrayList<IUser> getListOfAllUsers(Context context, IUser user) {
        ArrayList<IUser> arr = new ArrayList<>();
        try {
            BufferedReader br = setAndUpdate.openFileForReading(context, GameConstants.USER_STATS_FILE);
            String text;

            while ((text = br.readLine()) != null) {
                int index_of_comma = text.indexOf(",");
                String username = text.substring(0, index_of_comma);
                if (!user.getEmail().equals(username)) {
                    IUser NewUser = new User(username);
                    setAndUpdate.setInfoInLineToUser(text, NewUser);
                    arr.add(NewUser);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    /**
     * Add a statistic at a specific place for all accounts when applicable.
     *
     * @param context  of the device
     * @param position in the line for the statistic to be added
     * @param numTimes the number of times a statistic will be added
     * @return true if all accounts were updated or false if otherwise;
     */
    public boolean addStatForAllAccounts(Context context, int position, int numTimes) {
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader br = setAndUpdate.openFileForReading(context, GameConstants.USER_STATS_FILE);
            if (br == null) {
                return false;
            }
            String text;

            while ((text = br.readLine()) != null) {
                int numCommas = GameConstants.countOccurrences(text, ',');
                if (numCommas != GameConstants.TOTAL_NUM_OF_STATISTICS - 1) {
                    if (position > GameConstants.TOTAL_NUM_OF_STATISTICS + 1 || position <= 0) {
                        return false;
                    } else if (position == GameConstants.TOTAL_NUM_OF_STATISTICS + 1) {
                        String newStats = new String(new char[numTimes])
                                .replace("\0", ", 0");
                        String newText = text + newStats + "\n";
                        sb.append(newText);
                    } else if (position == 1) {
                        String newStats = new String(new char[numTimes])
                                .replace("\0", ", 0");
                        String newText = newStats + text + "\n";
                        sb.append(newText);
                    } else {
                        int getToStat = 0;
                        int indexOfCommaBeforeStat = 0;
                        for (int i = 0; i <= text.length(); i++) {
                            if (getToStat == position - 1) {
                                indexOfCommaBeforeStat = i - 1;
                                break;
                            }
                            if (text.charAt(i) == ',') {
                                getToStat += 1;
                            }
                        }
                        String firstHalf = text.substring(0, indexOfCommaBeforeStat);
                        String secondHalf = text.substring(indexOfCommaBeforeStat);
                        String middle = new String(new char[numTimes]).replace(
                                "\0", ", 0");
                        String newText = firstHalf + middle + secondHalf + "\n";
                        sb.append(newText);
                    }
                } else {
                    sb.append(text).append("\n");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // write the new string with the replaced line OVER the same file
        setAndUpdate.writeStringToFile(context, sb, GameConstants.USER_STATS_FILE);
        return true;
    }


    /**
     * @param context     of the device
     * @param username    of the user
     * @param newPassword to be changed
     * @param getOrChange the string which will tell the method if you want to return the password
     *                    or change the password
     * @return the password or return true if the password was successfully changed
     */

    public Object getOrChangePassword(Context context, String username, String newPassword, String getOrChange) {
        return setAndUpdate.getOrChangePassword(context, username, newPassword, getOrChange);
    }


    public void writeInfoToFile(Context context, String username, String password, String fileName) {
        writeAndCheck.writeInfoToFile(context, username, password, fileName);
    }


}
