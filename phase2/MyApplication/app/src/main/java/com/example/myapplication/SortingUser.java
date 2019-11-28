package com.example.myapplication;

import com.example.myapplication.UserInfo.IUser;

import java.util.Comparator;

public class SortingUser implements Comparator<IUser> {
    private String GameName;
    private String Statistic;

    SortingUser(String gameName, String statistic) {
        this.GameName = gameName;
        this.Statistic = statistic;
    }

    @Override
    public int compare(IUser user, IUser t1) {
        if (user.getStatistic(GameName, Statistic) instanceof Integer) {
            int ValueOfUser = (int) user.getStatistic(GameName, Statistic);
            int ValueOfT1 = (int) t1.getStatistic(GameName, Statistic);
            if (ValueOfUser < ValueOfT1) {
                return 1;
            } else if (ValueOfUser > ValueOfT1) {
                return -1;
            }
            return 0;
        } else {
            return Integer.parseInt(null);
        }
    }
}
