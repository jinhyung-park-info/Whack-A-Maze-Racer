package com.example.myapplication;

import com.example.myapplication.TypeRacer.TypeRacer;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class GameConstants {
    public static int limiter = 0;
    public static final String USERMANAGER = "UserManager";
    public static final int molePassingScore = 5;
    public static final int moleDefaultDuration = 2400;
    public static final int TotalMazeGames = 2;
    public static final int MazeWallThickness = 4;
    public static final int NumberOfMazeCollectibles = 3;
    //2 for user and 6 for the games
    public static final int TotalNumOfStastics = 8;
    public static final int NumPeopleOnScoreBoard = 4;
    public static final String NameGame1 = "Whack-A-Mole";
    public static final String NameGame2 = "TypeRacer";
    public static final String NameGame3 = "Maze";
    public static final String MoleHit = "MoleHit";
    public static final String MoleStats = "MoleStats";
    public static final String MoleScore = "MoleScore";
    public static final String MoleHigh = "MoleHigh";
    public static final String MoleAllTimeHigh = "MoleAllTimeHigh";
    public static final String NumMazeGamesPlayed = "NumMazeGamesPlayed";
    public static final String TypeRacerStreak = "TypeRacerStreak";
    public static final String NumCollectiblesCollectedMaze = "NumCollectiblesCollectedMaze";
    public static final String[] OptionsForScoreBoard = new String[]{"Overall Score" ,"Moles Hit", "Num MazeGames Played",
            "Num MazeItems Collected","TypeRacerStreak"};
    public static final String[] WhackAMoleStatistics = new String[]{MoleStats, MoleHit, MoleAllTimeHigh};
    public static final String[]  TypeRacerStatistics = new String[]{TypeRacerStreak};
    public static final String[]  MazeStatistics = new String[]{NumMazeGamesPlayed, NumCollectiblesCollectedMaze};
    public static String[] GameNames = new String[]{NameGame1, NameGame2, NameGame3};

    public static String[] getArrayOfStatistics(String GameName){
        switch (GameName){
            case NameGame1:
                return WhackAMoleStatistics;
            case NameGame2:
                return TypeRacerStatistics;
            case NameGame3:
                return MazeStatistics;
        }
        return new String[]{};
    }

}
