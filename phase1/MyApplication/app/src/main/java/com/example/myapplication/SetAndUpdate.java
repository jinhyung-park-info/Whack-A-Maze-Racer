package com.example.myapplication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SetAndUpdate implements Serializable {
    /**
     * Set the statistics of the user in the file
     *
     * @param context of the device
     * @param user
     */
    void setStatistics(Context context, User user) {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(GameConstants.USER_STATS_FILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                int index_of_first_comma = text.indexOf(",");
                String other_username = text.substring(0, index_of_first_comma);
                if (user.getEmail().equals(other_username)) {
                    helper(text, user);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Update the statistics of a user in the file
     *
     * @param context of the device
     * @param user
     */
    public void updateStatistics(Context context, User user) {
        FileInputStream fis = null;
        StringBuilder sb = new StringBuilder();

        try {
            fis = context.openFileInput(GameConstants.USER_STATS_FILE);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                int index_of_first_comma = text.indexOf(",");
                String other_username = text.substring(0, index_of_first_comma);
                if (user.getEmail().equals(other_username)) {
                    String new_text = makeLine(other_username, user);
                    sb.append(new_text);
                } else {
                    sb.append(text).append("\n");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // write the new string with the replaced line OVER the same file
        writeStringToFile(context, sb, GameConstants.USER_STATS_FILE);

    }

    public void writeStringToFile(Context context, StringBuilder sb, String FileName) {
        FileOutputStream fileOut = null;
        try {
            fileOut = context.openFileOutput(FileName, MODE_PRIVATE);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            if (fileOut != null)
                fileOut.write(sb.toString().getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            if (fileOut != null)
                fileOut.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String makeLine(String username, User user) {
        String line = username + ", " + user.getLastPlayedLevel() + ", " + user.getOverallScore()
                + ", " + user.getStatistic(GameConstants.NameGame2, GameConstants.TypeRacerStreak)
                + ", " + user.getStatistic(GameConstants.NameGame3, GameConstants.NumMazeGamesPlayed)
                + ", " + user.getStatistic(GameConstants.NameGame3, GameConstants.NumCollectiblesCollectedMaze)
                + ", " + user.getStatistic(GameConstants.NameGame1, GameConstants.MoleHit)
                + ", " + user.getStatistic(GameConstants.NameGame1, GameConstants.MoleStats)
                + ", " + user.getStatistic(GameConstants.NameGame1, GameConstants.MoleAllTimeHigh)
                + ", " + user.getCurrency() + "\n";
        return line;
    }

    /**
     * Helper method to transfer the user statistics in the file to the user object
     *
     * @param line in the file
     * @param user
     * @return the updated user
     */
    void helper(String line, User user) {
        String cleanLine = line.replaceAll("\\s", "");
        int numCommas = GameConstants.countOccurrences(cleanLine, ',');
        String[] userAndGameStats = cleanLine.split(",", numCommas + 1);
        int lastPlayedLevel = Integer.parseInt(userAndGameStats[1]);
        int overallScore = Integer.parseInt(userAndGameStats[2]);
        int streaks = Integer.parseInt(userAndGameStats[3]);
        int numMazeGame = Integer.parseInt(userAndGameStats[4]);
        int numMazeItemsCollected = Integer.parseInt(userAndGameStats[5]);
        int moleHit = Integer.parseInt(userAndGameStats[6]);
        String loadMolesStats = userAndGameStats[7];
        int MoleAllTimeHigh = Integer.parseInt(userAndGameStats[8]);
        int gemsRemaining = Integer.parseInt(userAndGameStats[9]);

        Object[] typeRacer = new Object[]{GameConstants.NameGame2, GameConstants.TypeRacerStreak, streaks};
        Object[] whackAMole = new Object[]{GameConstants.NameGame1, GameConstants.MoleStats,
                loadMolesStats, GameConstants.MoleHit, moleHit, GameConstants.MoleAllTimeHigh, MoleAllTimeHigh};
        Object[] maze = new Object[]{GameConstants.NameGame3, GameConstants.NumMazeGamesPlayed,
                numMazeGame, GameConstants.NumCollectiblesCollectedMaze, numMazeItemsCollected};
        List<Object[]> ListOfGameStats = Arrays.asList(typeRacer, whackAMole, maze);
        ArrayList<Object[]> arrayOfGameStats = new ArrayList<>(ListOfGameStats);
        user.setStatisticsInMap(arrayOfGameStats);
        user.setLastPlayedLevel(lastPlayedLevel);
        user.setOverallScore(overallScore);
        user.setCurrency(gemsRemaining);
        //return user;
    }
}
