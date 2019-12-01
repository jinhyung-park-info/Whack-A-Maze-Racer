package com.example.myapplication.UserInfo;

import java.util.ArrayList;

public interface IUser extends Comparable<IUser> {

    String getEmail();

    String getPassword();

    int getOverallScore();

    int getCurrency();

    int getLastPlayedLevel();

    void setStatistic(String gameName, String statisticName, Object statistic);

    Object getStatistic(String gameName, String statisticName);

    void setStatisticsInDataStructure(ArrayList<Object[]> arrayOfGameStats);

    void setLastPlayedLevel(int level);

    void setOverallScore(int overallScore);

    void setCurrency(int currency);

    void setPassword(String password);
}
