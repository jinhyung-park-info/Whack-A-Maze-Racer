package com.example.myapplication.Maze;

import android.graphics.Bitmap;

import com.example.myapplication.GameConstants;
import com.example.myapplication.UserInfo.IUser;

/**
 * A gem collectible which will add 10 gems to the user
 */

public class GemCollectible extends Collectible {
    GemCollectible(int col, int row, Bitmap bitmap) {
        super(col, row, bitmap);
    }

    @Override
    public void handleCollection(IUser userInMaze) {
        userInMaze.setCurrency(userInMaze.getCurrency() + GameConstants.pointsFromCollectingGem);
    }

}
