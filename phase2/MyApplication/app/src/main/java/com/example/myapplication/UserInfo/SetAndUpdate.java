package com.example.myapplication.UserInfo;

import android.content.Context;

import com.example.myapplication.GameConstants;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SetAndUpdate implements Serializable {

    public void setOrUpdateStatistics(Context context, IUser user, String setOrUpdate){
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader br = openFileForReading(context, GameConstants.USER_STATS_FILE);
            if(br == null){
                return;
            }
            String text;

            while ((text = br.readLine()) != null) {
                int indexOfFirstComma = text.indexOf(",");
                String otherUsername = text.substring(0, indexOfFirstComma);
                if (user.getEmail().equals(otherUsername)) {
                    if(setOrUpdate.equals(GameConstants.update)) {
                        String new_text = makeLine(otherUsername, user);
                        sb.append(new_text);
                    }else if(setOrUpdate.equals(GameConstants.set)){
                        setInfoInLineToUser(text, user);
                        return;
                    }
                } else {
                    sb.append(text).append("\n");
                }
            }

        }catch (IOException e) {
            e.printStackTrace();
        }

        writeStringToFile(context, sb, GameConstants.USER_STATS_FILE);
    }

    public BufferedReader openFileForReading(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            return br;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void writeStringToFile(Context context, StringBuilder sb, String FileName) {
        try {
            FileOutputStream fileOut = context.openFileOutput(FileName, MODE_PRIVATE);
            fileOut.write(sb.toString().getBytes());
            fileOut.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String makeLine(String username, IUser user) {
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
    void setInfoInLineToUser(String line, IUser user) {
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
        user.setStatisticsInDataStructure(arrayOfGameStats);
        user.setLastPlayedLevel(lastPlayedLevel);
        user.setOverallScore(overallScore);
        user.setCurrency(gemsRemaining);
    }
}
