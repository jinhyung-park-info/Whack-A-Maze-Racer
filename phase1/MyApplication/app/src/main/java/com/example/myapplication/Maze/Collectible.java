package com.example.myapplication.Maze;

/**
 * A collectible object in the maze game
 */
public class Collectible extends Cell {

    /**
     * points gained by collecting this objects
     */
    private int points;

    Collectible(int col, int row) {
        super(col, row);
        points = 1;
    }

    public int getPoints() {
        return points;
    }

}
