package com.example.myapplication.UserInfo;

import android.content.Context;

import java.io.Serializable;

public interface IUserManager extends Serializable {

    void setOrUpdateStatistics(Context context, IUser user, String setOrUpdate);

    IUser getUser();

    void setUser(IUser user);

}
