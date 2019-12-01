package com.example.myapplication.UserInfo;

import android.content.Context;

import com.example.myapplication.GameConstants;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;

public class UserManager implements Serializable {

    private WriteAndCheck writeAndCheck;
    private ReadAndUpdate readAndUpdate;
    private HandleAllAccounts handleAllAccounts;

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
        this.readAndUpdate = new ReadAndUpdate();
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
        readAndUpdate.setOrUpdateStatistics(context, user, setOrUpdate);

    }

    /**
     * @param context of the device
     * @param user    the current user which will not be in the ArrayList
     * @return a list of all the users present in the file except the current user playing the game.
     */

    public ArrayList<IUser> getListOfAllUsers(Context context, IUser user) {
        return handleAllAccounts.getListOfAllUsers(context, user);
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
        return handleAllAccounts.addStatForAllAccounts(context, position, numTimes);
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
        return readAndUpdate.getOrChangePassword(context, username, newPassword, getOrChange);
    }


    public void writeInfoToFile(Context context, String username, String password, String fileName) {
        writeAndCheck.writeInfoToFile(context, username, password, fileName);
    }


}
