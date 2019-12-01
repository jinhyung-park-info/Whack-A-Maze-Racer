package com.example.myapplication.UserInfo;

import android.content.Context;
import android.widget.EditText;

public interface ILoginPresenter {
    boolean validateCredentialsForAccountCreation(Context context, String username, String password, EditText editTextUser,
                                                  EditText editTextPass);

    boolean validateCredentialsForLogin(Context context, String username, String password, EditText editTextUser,
                                        EditText editTextPass);

}
