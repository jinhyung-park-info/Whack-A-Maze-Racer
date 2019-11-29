package com.example.myapplication.Maze;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.myapplication.GameConstants;
import com.example.myapplication.UserInfo.IUser;
import com.example.myapplication.UserInfo.User;
import com.example.myapplication.UserInfo.UserManager;

/**
 * A collectible object in the maze game
 */
public class Collectible extends Cell {

    /**
     * points gained by collecting this objects
     */
    private int points;


    private Bitmap bitmap;

    Collectible(int col, int row, Bitmap bitmap) {
        super(col, row);
        points = GameConstants.PointsForACollectible;
        this.bitmap = bitmap;
    }

    public int getPoints() {
        return points;
    }

    public void handleCollection(IUser userInMaze) {
        //increment number of collectibles collected by 1
        int newNumCollectiblesCollected = (int) userInMaze.getStatistic(GameConstants.NameGame3,
                GameConstants.NumCollectiblesCollectedMaze) + 1;
        userInMaze.setStatistic(GameConstants.NameGame3,
                GameConstants.NumCollectiblesCollectedMaze, newNumCollectiblesCollected);

        int newScore = userInMaze.getOverallScore() + points;
        userInMaze.setOverallScore(newScore);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Bitmap resizeBitmap(int width, int height) {
        bitmap = Bitmap.createScaledBitmap(bitmap, height, width, true);
        return bitmap;
    }

}
