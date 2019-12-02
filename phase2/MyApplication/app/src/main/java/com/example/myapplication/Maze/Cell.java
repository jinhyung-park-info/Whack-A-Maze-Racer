package com.example.myapplication.Maze;

/**
 * A cell object to be drawn in the maze
 */

public class Cell {
    private boolean hasTopWall = true;

    private boolean hasBottomWall = true;

    private boolean hasLeftWall = true;

    private boolean hasRightWall = true;

    private boolean visited = false;

    private int col;

    private int row;

    Cell(int col, int row) {
        if (col >= 0 && row >= 0) {
            this.col = col;
            this.row = row;
        }
    }

   boolean hasTopWall() { return hasTopWall; }

    void setHasTopWall(boolean hasTopWall) { this.hasTopWall = hasTopWall; }

    boolean hasBottomWall() { return hasBottomWall; }

    void setHasBottomWall(boolean hasBottomWall) { this.hasBottomWall = hasBottomWall; }

    boolean hasLeftWall() { return hasLeftWall; }

    void setHasLeftWall(boolean hasLeftWall) { this.hasLeftWall = hasLeftWall; }

    boolean hasRightWall() { return hasRightWall; }

    void setHasRightWall(boolean hasRightWall) { this.hasRightWall = hasRightWall; }

     int getCol() { return col; }

    int getRow() { return row; }

    boolean wasVisited() { return visited; }

    void setVisited(boolean visited) { this.visited = visited; }
}
