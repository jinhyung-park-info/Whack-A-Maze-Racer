package com.example.myapplication;

import java.util.Comparator;

public class SortingUser implements Comparator<User> {
    private String GameName;
    private String Statistic;

    SortingUser(String gameName, String statistic){
        this.GameName = gameName;
        this.Statistic = statistic;
    }
    @Override
    public int compare(User user, User t1) {
        int ValueOfUser = (int) user.getStatistic(GameName, Statistic);
        int ValueOfT1 = (int) t1.getStatistic(GameName, Statistic);
        if (ValueOfUser < ValueOfT1) {
            return 1;
        } else if (ValueOfUser > ValueOfT1) {
            return -1;
        }
        return 0;
    }
}
