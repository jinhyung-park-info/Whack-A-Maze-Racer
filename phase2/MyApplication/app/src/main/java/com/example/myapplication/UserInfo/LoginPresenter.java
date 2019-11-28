package com.example.myapplication.UserInfo;

import android.content.Context;
import android.widget.EditText;

import com.example.myapplication.GameConstants;

import java.util.ArrayList;

public class LoginPresenter implements ILoginPresenter {

    private UserManager userManager;

    public LoginPresenter() {
        this.userManager = new UserManager();
    }

    private boolean validateChar(String username, String password, EditText editTextUser,
                                 EditText editTextPass) {
        if (username.length() > GameConstants.usernameLength) {
            editTextUser.setError("Usernames cannot be longer than " + GameConstants.usernameLength
                    + " characters");
            return false;
        }
        if (password.length() > GameConstants.passwordLength) {
            editTextPass.setError("Passwords cannot be longer than " + GameConstants.passwordLength
                    + " characters");
            return false;
        }
        if (username.length() == 0) {
            editTextUser.setError("Please enter Text");
            return false;
        }
        if (password.length() == 0) {
            editTextPass.setError("Please enter Text");
            return false;
        }
        if (username.contains(" ")) {
            editTextUser.setError("Spaces are not allowed in username and passwords");
            return false;
        }
        if (username.contains(",")) {
            editTextUser.setError("Commas are not allowed in username and passwords");
            return false;
        }
        if (password.contains(" ")) {
            editTextPass.setError("Spaces are not allowed in username and passwords");
            return false;
        }
        if (password.contains(",")) {
            editTextPass.setError("Commas are not allowed in username and passwords");
            return false;
        }
        return true;
    }

    public boolean validateCredentialsForAccountCreation(Context context, String username, String password,
                                                         EditText editTextUser, EditText editTextPass) {
        if (validateChar(username, password, editTextUser, editTextPass)) {
            ArrayList<Boolean> arr = userManager.checkUsernameAndPassword(context, username, password);
            if (arr.get(0)) {
                editTextUser.setError("Username already exists");
                return false;
            }
        }else {
            return false;
        }
        return true;
    }


    @Override
    public boolean validateCredentialsForLogin(Context context, String username, String password, EditText editTextUser, EditText editTextPass) {
        if(validateChar(username, password, editTextUser, editTextPass)) {
            ArrayList<Boolean> validation = userManager.checkUsernameAndPassword(context, username, password);
            if (validation.get(0)) {
                if (!(validation.get(1))) {
                    editTextPass.setError("Incorrect Password");
                    return false;
                }
            } else {
                editTextUser.setError("Username does not exist");
                return false;
            }
        }else {
            return false;
        }
        return true;
    }
}
