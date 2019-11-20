package com.example.myapplication.Maze;

/**
 * A collectible object in the maze game
 */
public class Collectible extends Cell {
    /**
     * Whether this object was collected or not
     */
    private boolean collected;

    /**
     * points gained by collecting this objects
     */
    private int points;

    Collectible(int col, int row) {
        super(col, row);
        collected = false;
        points = 1;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
