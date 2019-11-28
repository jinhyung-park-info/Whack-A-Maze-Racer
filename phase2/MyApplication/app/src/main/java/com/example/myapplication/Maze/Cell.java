package com.example.myapplication.Maze;

public class Cell {
    /**
     * whether this cell has a wall on the top or not
     */
    private boolean topWall = true;

    /**
     * whether this cell has a wall at the bottom or not
     */
    private boolean bottomWall = true;

    /**
     * whether this cell has a wall on the left side or not
     */
    private boolean leftWall = true;

    /**
     * whether this cell has a wall on the right side or not
     */
    private boolean rightWall = true;

    /**
     * tells us if this cell was "visited" by the imaginary
     * person paving the path for the maze
     */
    private boolean visited = false;

    /**
     * the column position of this cell
     */
    private int col;

    /**
     * the row position of this cell
     */
    private int row;

    Cell(int col, int row) {
        this.col = col;
        this.row = row;
    }

    boolean hasTopWall() {
        return topWall;
    }

    void setTopWall(boolean topWall) {
        this.topWall = topWall;
    }

    boolean hasBottomWall() {
        return bottomWall;
    }

    void setBottomWall(boolean bottomWall) {
        this.bottomWall = bottomWall;
    }

    boolean hasLeftWall() {
        return leftWall;
    }

    void setLeftWall(boolean leftWall) {
        this.leftWall = leftWall;
    }

    boolean hasRightWall() {
        return rightWall;
    }

    void setRightWall(boolean rightWall) {
        this.rightWall = rightWall;
    }

    int getCol() {
        return col;
    }

    void setCol(int col) {
        this.col = col;
    }

    int getRow() {
        return row;
    }

    void setRow(int row) {
        this.row = row;
    }

    boolean wasVisited() {
        return visited;
    }

    void setVisited(boolean visited) {
        this.visited = visited;
    }
}
