package com.example.myapplication.UserInfo;

import android.content.Context;

import com.example.myapplication.GameConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;

import static com.example.myapplication.GameConstants.openFileForReading;
import static com.example.myapplication.GameConstants.writeStringToFile;

public class HandleAllAccounts implements Serializable {

    /**
     * Add a statistic at a specific place for all accounts when applicable.
     *
     * @param context  of the device
     * @param position in the line for the statistic to be added
     * @param numTimes the number of times a statistic will be added
     * @return true if all accounts were updated or false if otherwise;
     */
    public boolean addStatForAllAccounts(Context context, int position, int numTimes) {
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader br = openFileForReading(context, GameConstants.USER_STATS_FILE);
            if (br == null) {
                return false;
            }
            String text;

            while ((text = br.readLine()) != null) {
                int numCommas = GameConstants.countOccurrences(text, ',');
                if (numCommas != GameConstants.TOTAL_NUM_OF_STATISTICS - 1) {
                    if (position > GameConstants.TOTAL_NUM_OF_STATISTICS + 1 || position <= 0) {
                        return false;
                    } else if (position == GameConstants.TOTAL_NUM_OF_STATISTICS + 1) {
                        sb.append(addStatForAllAccountsHelper(text, position, numTimes));
                    }
                } else {
                    sb.append(text).append("\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        writeStringToFile(context, sb, GameConstants.USER_STATS_FILE);
        return true;
    }

    private String addStatForAllAccountsHelper(String text, int position, int numTimes) {
        if (position == GameConstants.TOTAL_NUM_OF_STATISTICS + 1) {
            String newStats = new String(new char[numTimes])
                    .replace("\0", ", 0");
            return text + newStats + '\n';
        } else if (position == 1) {
            String newStats = new String(new char[numTimes])
                    .replace("\0", ", 0");
            return newStats + text + "\n";
        } else {
            int getToStat = 0;
            int indexOfCommaBeforeStat = 0;
            for (int i = 0; i <= text.length(); i++) {
                if (getToStat == position - 1) {
                    indexOfCommaBeforeStat = i - 1;
                    break;
                }
                if (text.charAt(i) == ',') {
                    getToStat += 1;
                }
            }
            String firstHalf = text.substring(0, indexOfCommaBeforeStat);
            String secondHalf = text.substring(indexOfCommaBeforeStat);
            String middle = new String(new char[numTimes]).replace(
                    "\0", ", 0");
            return firstHalf + middle + secondHalf + "\n";
        }
    }

}
