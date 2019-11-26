package com.example.myapplication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class SetAndUpdate extends UserManager {
    /**
     * Set the statistics of the user in the file
     *
     * @param context of the device
     * @param user
     */
    void setStatistics(Context context, User user) {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(MainActivity.Stats_file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                int index_of_first_comma = text.indexOf(",");
                String other_username = text.substring(0, index_of_first_comma);
                if (user.getEmail().equals(other_username)) {
                    user = helper(text, user);
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
            fis = context.openFileInput(MainActivity.Stats_file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                int index_of_first_comma = text.indexOf(",");
                String other_username = text.substring(0, index_of_first_comma);
                if (user.getEmail().equals(other_username)) {
                    String new_text = other_username + ", " + user.getLastPlayedLevel() + ", " + user.getOverallScore()
                            + ", " + user.getStatistic(GameConstants.NameGame2, GameConstants.TypeRacerStreak)
                            + ", " + user.getStatistic(GameConstants.NameGame3, GameConstants.NumMazeGamesPlayed)
                            + ", " + user.getStatistic(GameConstants.NameGame3, GameConstants.NumCollectiblesCollectedMaze)
                            + ", " + user.getStatistic(GameConstants.NameGame1, GameConstants.MoleHit)
                            + ", " + user.getStatistic(GameConstants.NameGame1, GameConstants.MoleStats)
                            + ", " + user.getStatistic(GameConstants.NameGame1, GameConstants.MoleAllTimeHigh)
                            + ", " + user.getCurrency() + "\n";
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
        FileOutputStream fileOut = null;
        try {
            fileOut = context.openFileOutput(MainActivity.Stats_file, MODE_PRIVATE);
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

    /**
     * Helper method to transfer the user statistics in the file to the user object
     *
     * @param line in the file
     * @param user
     * @return the updated user
     */
    User helper(String line, User user) {
        int indexOfFirstComma = line.indexOf(",");
        int indexOfSecondComma = line.indexOf(",", indexOfFirstComma + 1);
        int indexOfThirdComma = line.indexOf(",", indexOfSecondComma + 1);
        int indexOfForthComma = line.indexOf(",", indexOfThirdComma + 1);
        int indexOfFifthComma = line.indexOf(",", indexOfForthComma + 1);
        int indexOfSixthComma = line.indexOf(",", indexOfFifthComma + 1);
        int indexOfSeventhComma = line.indexOf(",", indexOfSixthComma + 1);
        int indexOfEightComma = line.indexOf(",", indexOfSeventhComma + 1);
        int indexOfNinthComma = line.indexOf(",", indexOfEightComma + 1);
        int lastPlayedLevel = Integer.parseInt(line.substring(indexOfFirstComma + 2, indexOfSecondComma));
        int overallScore = Integer.parseInt(line.substring(indexOfSecondComma + 2, indexOfThirdComma));
        int streaks = Integer.parseInt(line.substring(indexOfThirdComma + 2, indexOfForthComma));
        int numMazeGame = Integer.parseInt(line.substring(indexOfForthComma + 2, indexOfFifthComma));
        int numMazeItemsCollected = Integer.parseInt(line.substring(indexOfFifthComma + 2, indexOfSixthComma));
        int moleHit = Integer.parseInt(line.substring(indexOfSixthComma + 2, indexOfSeventhComma));
        String loadMolesStats = line.substring(indexOfSeventhComma + 2, indexOfEightComma);
        int MoleAllTimeHigh = Integer.parseInt(line.substring(indexOfEightComma + 2, indexOfNinthComma));
        int gemsRemaining = Integer.parseInt(line.substring(indexOfNinthComma + 2));
        Object[] typeRacer = new Object[]{GameConstants.NameGame2, GameConstants.TypeRacerStreak, streaks};
        Object[] whackAMole = new Object[]{GameConstants.NameGame1, GameConstants.MoleStats, loadMolesStats, GameConstants.MoleHit, moleHit, GameConstants.MoleAllTimeHigh, MoleAllTimeHigh};
        Object[] maze = new Object[]{GameConstants.NameGame3, GameConstants.NumMazeGamesPlayed, numMazeGame, GameConstants.NumCollectiblesCollectedMaze, numMazeItemsCollected};
        ArrayList<Object[]> arrayOfGameStats = new ArrayList<>();
        arrayOfGameStats.add(typeRacer);
        arrayOfGameStats.add(maze);
        arrayOfGameStats.add(whackAMole);
        user.setStatisticsInMap(arrayOfGameStats);
        user.setLastPlayedLevel(lastPlayedLevel);
        user.setOverallScore(overallScore);
        user.setCurrency(gemsRemaining);
        return user;
    }
}
