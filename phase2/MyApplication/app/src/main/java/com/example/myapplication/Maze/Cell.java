package com.example.myapplication.Maze;

public class Cell {
    private boolean hasTopWall = true;

    private boolean hasBottomWall = true;

    private boolean hasLeftWall = true;

    private boolean hasRightWall = true;

    private boolean visited = false;

    private int col;

    private int row;

    Cell(int col, int row) {
        if(col >= 0 && row >= 0){
            this.col = col;
            this.row = row;
        }
    }

    synchronized boolean hasTopWall() {
            return hasTopWall;
    }

   synchronized void setHasTopWall(boolean hasTopWall) {
        this.hasTopWall = hasTopWall;
    }

     synchronized boolean hasBottomWall() {
        return hasBottomWall;
    }

    synchronized void setHasBottomWall(boolean hasBottomWall) {
        this.hasBottomWall = hasBottomWall;
    }

    synchronized boolean hasLeftWall() {
        return hasLeftWall;
    }

    synchronized void setHasLeftWall(boolean hasLeftWall) {
        this.hasLeftWall = hasLeftWall;
    }

    synchronized boolean hasRightWall() {
        return hasRightWall;
    }

    synchronized void setHasRightWall(boolean hasRightWall) {
        this.hasRightWall = hasRightWall;
    }

    synchronized int  getCol() {
        return col;
    }

    synchronized void setCol(int col) {
            this.col = col;
    }

    synchronized int getRow() {
        return row;
    }

    synchronized void setRow(int row) {
        this.row = row;
    }

   synchronized boolean wasVisited() {
        return visited;
    }

    synchronized void setVisited(boolean visited) {
        this.visited = visited;
    }
}
