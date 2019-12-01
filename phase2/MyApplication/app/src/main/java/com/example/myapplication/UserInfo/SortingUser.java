package com.example.myapplication.UserInfo;


import java.util.Comparator;

/**
 * This class will sort the user based on the game statistic only if the statistic is an int
 */

public class SortingUser implements Comparator<IUser> {
    private String gameName;
    private String statistic;

    public SortingUser(String gameName, String statistic) {
        this.gameName = gameName;
        this.statistic = statistic;
    }

    @Override
    public int compare(IUser user, IUser t1) {

        int valueOfUser = (int) user.getStatistic(gameName, statistic);
        int valueOfT1 = (int) t1.getStatistic(gameName, statistic);
        if (valueOfUser < valueOfT1) {
            return 1;
        } else if (valueOfUser > valueOfT1) {
            return -1;
        }
        return 0;

    }
}
