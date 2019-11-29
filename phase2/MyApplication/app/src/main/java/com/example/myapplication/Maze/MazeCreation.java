package com.example.myapplication.Maze;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


interface MazeMaker{
   Cell[][] makeMaze(Cell[][] cells, int cols, int rows);
}

public class MazeCreation implements MazeMaker{

    /**
     * Returns a neighbouring cell of the given cell. If there is more than one neighbour,
     * returns a random neighbour.
     *
     * @param currentCell
     * @return neighbourCell
     */
    private Cell getRandomNeighbour(Cell currentCell, Cell[][] cells, int numCols, int numRows) {
        //check for unvisited neighbours
        ArrayList<Cell> neighbours = new ArrayList<>();

        //left neighbour
        if (currentCell.getCol() > 0) {
            if (!cells[currentCell.getCol() - 1][currentCell.getRow()].wasVisited()) {
                neighbours.add(cells[currentCell.getCol() - 1][currentCell.getRow()]);
            }
        }
        //right neighbour
        if (currentCell.getCol() < numCols - 1) {
            if (!cells[currentCell.getCol() + 1][currentCell.getRow()].wasVisited()) {
                neighbours.add(cells[currentCell.getCol() + 1][currentCell.getRow()]);
            }
        }
        //top neighbour
        if (currentCell.getRow() > 0) {
            if (!cells[currentCell.getCol()][currentCell.getRow() - 1].wasVisited()) {
                neighbours.add(cells[currentCell.getCol()][currentCell.getRow() - 1]);
            }
        }
        //bottom neighbour
        if (currentCell.getRow() < numRows - 1) {
            if (!cells[currentCell.getCol()][currentCell.getRow() + 1].wasVisited()) {
                neighbours.add(cells[currentCell.getCol()][currentCell.getRow() + 1]);
            }
        }

        if (neighbours.size() > 0) {
            Random ran = new Random();
            int chosenNeighbourIndex = ran.nextInt(neighbours.size());
            return neighbours.get(chosenNeighbourIndex);
        } else {
            return null;
        }
    }

    /**
     * Removes the wall between two neighbouring cells
     *
     * @param current cell
     * @param next    cell
     */
    private void removeWall(Cell current, Cell next) {
        //next cell is above current
        if (current.getCol() == next.getCol() && current.getRow() == next.getRow() + 1) {
            current.setHasTopWall(false);
            next.setHasBottomWall(false);
        }
        //next cell is below current
        else if (current.getCol() == next.getCol() && current.getRow() == next.getRow() - 1) {
            current.setHasBottomWall(false);
            next.setHasTopWall(false);
        }
        //next cell is to the left of current
        else if (current.getCol() == next.getCol() + 1 && current.getRow() == next.getRow()) {
            current.setHasLeftWall(false);
            next.setHasRightWall(false);
        }
        //next cell is to the right of current
        else if (current.getCol() == next.getCol() - 1 && current.getRow() == next.getRow()) {
            current.setHasRightWall(false);
            next.setHasLeftWall(false);
        }


    }

    /**
     * Initializes the 2d array of cells and initializes each individual cell element in the array.
     * https://www.youtube.com/watch?v=8Ju_uxJ9v44
     * https://stackoverflow.com/questions/38502/whats-a-good-algorithm-to-generate-a-maze
     * These links were used in order to help us choose  which algorithm to create the maze and to
     * create the maze
     */

    @Override
    public Cell[][] makeMaze(Cell[][] cells, int cols, int rows) {
        //recursive backtracking algorithm for creating mazes
        Stack<Cell> stackVisitedCells = new Stack<>();
        Cell current, next;

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }

        current = cells[0][0];
        current.setVisited(true);

        //do this until all cells have been visited
        do {
            next = getRandomNeighbour(current, cells, cols, rows);

            //this is done when we find a neighbouring cell
            if (next != null) {
                removeWall(current, next);
                stackVisitedCells.push(current);
                current = next;
                current.setVisited(true);
            }
            //if we don't find a neighbouring cell, we have to backtrack
            else {
                current = stackVisitedCells.pop();
            }
        } while (!stackVisitedCells.isEmpty());
     return cells;

    }
}
