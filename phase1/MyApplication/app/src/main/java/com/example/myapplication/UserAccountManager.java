package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class UserAccountManager {

    private final static String TAG = "UserAccountManager";

    /**
     * ArrayList of all user accounts
     */
    private static ArrayList<User> userAccounts;

    /**
     * Directory to save files in
     */
    private static String directory;

    /**
     * File holding the list of user accounts
     */
    private static File userAccountsFile;

    public UserAccountManager(String directory){
        this.directory = directory;
        this.userAccounts = readUserFile();
    }

    /**
     * Searches for a file of user accounts. If not found, creates a new one. Returns an ArrayList
     * of User objects derived from the file.
     * @return List of User objects
     */
    private static ArrayList readUserFile() {
        ArrayList<User> userAccounts = new ArrayList<User>();

        String path = directory + "/user_accounts.txt";
        userAccountsFile = createFile(path);

        //open stream to read file
        try (Scanner input = new Scanner(userAccountsFile)) {
            //split by commas
            input.useDelimiter(",");
            String username;
            String password;

            //on each line (keep in mind we're screwed if they decide to have commas in their
            //username or password
            while (input.hasNext()) {
                //System.out.println(input.nextLine());
                username = input.next();
                password = input.next();

                userAccounts.add(new User(username, password, new File(input.next().trim())));
            }

        } catch (IOException e) {
            Log.e(TAG, "Error encountered trying to open file for reading: " + userAccountsFile);
        }

        return userAccounts;
    }

    /**
     * Creates and returns a file object for the save state file of a user. Doesn't matter if the
     * user has no save states, will return a file dedicated to save states only for this user.
     * @param username of user
     * @return save state file
     */
    private static File createSaveStateFile(String username) {
        String path = directory + "/" + username + "_saveState.txt";
        File file = createFile(path);

        return file;
    }

    /**
     * Creates and returns a file object given a path.
     * @param path
     * @return file at path
     */
    private static File createFile(String path) {
        File file = new File(path); //create file that will be placed in directory
        if (file.exists()==false) {
            //sometimes the createNewFile could throw an error
            try {
                file.createNewFile(); //add file to the computer's directory
                Log.e(TAG, "File created: " + file.getName());

            } catch (IOException e) {
                Log.e(TAG, "Error encountered trying to open file for writing: " + file);
            }
        }

        return file;
    }

    public static String createUserAccount(String username, String password) {
        String invalidMsg = "";

        User user = findUser(username);

        if(username.length() == 0){
            invalidMsg = "Please enter text in Username field";
        }
        else if(password.length() == 0){
            invalidMsg = "Please enter text in Password field";
        }
        else if(user.isEmpty()==false){ //we want the username to not exist cause we're creating a new one
            invalidMsg = "Username already exists, please pick another Username";
        }
        else {
            addUser(username, password);
        }

        return invalidMsg;
    }


    public static String checkUserCredentialsForLogin(String username, String password) {
        String invalidMsg = "";
        User user = findUser(username);

        if(username.length() == 0){
            invalidMsg = "Please enter text in Username field";
        }
        if(password.length() == 0){
            invalidMsg = "Please enter text in Password field";
        }
        if(user.isEmpty()){
            invalidMsg = "Username does not exist";
        }
        if (!(user.verifyPassword(password))) {
            invalidMsg = "Invalid Password";
        }
        return invalidMsg;
    }

    /**
     * Searches list of user accounts to see if user exists by username. If exists, returns the user.
     * Else, returns an empty user.
     * @param username
     * @return found user
     */
    public static User findUser(String username) {
        for (User u: userAccounts) {
            if (u.getUsername().equals(username))
                return u;
        }
        return new User("", "", new File(directory));
    }

    /**
     * Adds user to the ArrayList and user accounts file. Returns true if there is success and false
     * if there is any error.
     * @param username
     * @param password
     * @return whether user could be added or not
     *
     */
    public static boolean addUser(String username, String password){
        User user = new User(username, password, createSaveStateFile(username));
        if (findUser(user.getUsername()).isEmpty()==false) {
            return false;
        }
        else {
            userAccounts.add(user);
            updateUserFile(user);
            return true;
        }
    }

    /**
     * Updates the user accounts file by adding a new user
     * @param user
     *
     */
    private static void updateUserFile(User user) {
        BufferedWriter output;

        try {
            output = new BufferedWriter(new FileWriter(userAccountsFile.getAbsolutePath(), true));
            output.append(user.getUsername() + "," + user.getPassword() + "," +
                    user.getSaveState().getAbsolutePath());
            System.out.println(user.getUsername() + "," + user.getPassword() + "," +
                    user.getSaveState().getAbsolutePath());
            output.newLine();
            output.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Error encountered trying to open file for writing: " + userAccountsFile);
        }
        catch (IOException e) {
            Log.e(TAG, "Error encountered trying to open file for writing: " + userAccountsFile);
        }
    }
}

/*
 * A user object, which holds the user's username and password. Is empty if
 * (username, password, saveState) = ("", "", directory of phone)
 */
class User {

    /**
     * the username of this user
     */
    private String username;

    /**
     * the password of this user
     */
    private String password;

    /**
     * the save state file for this user.
     */
    private File saveState;

    User(String username, String password, File saveState){
        this.username = username;
        this.password = password;
        this.saveState = saveState;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setSaveState(File saveState) {
        this.saveState = saveState;
    }

    public File getSaveState() {
        return saveState;
    }

    /**
     * Returns the username of this user.
     * @return username of this user
     */
    public String getUsername() {
        return username;
    }

    public boolean isEmpty() {
        if (username.equals(""))
            return true;
        else
            return false;
    }

    /**
     * Verifies if the given password is correct for this user.
     * @param password guess
     * @return whether this password is correct or not
     */
    public boolean verifyPassword(String password) {
        return this.password.equals(password);
    }

}
