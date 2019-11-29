package com.example.myapplication.Maze;

import android.content.Context;

import com.example.myapplication.UserInfo.UserManager;

import java.util.ArrayList;
import java.util.Arrays;

interface Loadable {

    ArrayList<StringBuilder> saveMaze();

    MazeView loadMaze(ArrayList<StringBuilder> savedMaze, MazeView maze);

    MazeView startNewMaze(int bgColour, String difficulty, int playerType);

}

/**
 * Loads and saves the maze view
 */
class MazeLoader implements Loadable {

    private MazeView mazeView;
    private Context context;
    private UserManager userManager;

    public MazeLoader(Context context, UserManager userManager) {
        this.context = context;
        this.userManager = userManager;
    }

    /**
     * Returns a Array List String Builder representation of this maze. See readMe.txt for details
     *
     * @return saved maze
     */
    @Override
    public ArrayList<StringBuilder> saveMaze() {
        ArrayList<StringBuilder> savedMaze = new ArrayList<>();
        savedMaze.add(new StringBuilder(Integer.toString(mazeView.getBgColour())));
        savedMaze.add(new StringBuilder(Integer.toString(mazeView.getPlayerType())));
        savedMaze.add(new StringBuilder(Integer.toString(mazeView.getRows())));
        savedMaze.add(new StringBuilder(Integer.toString(mazeView.getCols())));
        savedMaze.add(new StringBuilder(Integer.toString(mazeView.getPlayer().getRow())));
        savedMaze.add(new StringBuilder(Integer.toString(mazeView.getPlayer().getCol())));
        for (int i = 0; i <= mazeView.getCols() - 1; i++) {
            StringBuilder currColString = new StringBuilder();
            for (Cell c : mazeView.getCells()[i]) {
                if (c.hasLeftWall())
                    currColString.append(1);
                else
                    currColString.append(0);
                if (c.hasTopWall())
                    currColString.append(1);
                else
                    currColString.append(0);
                if (c.hasRightWall())
                    currColString.append(1);
                else
                    currColString.append(0);
                if (c.hasBottomWall())
                    currColString.append(1);
                else
                    currColString.append(0);
                currColString.append(" ");

            }
            savedMaze.add(currColString);
        }
        return savedMaze;
    }

    @Override
    /**
     * Loads the maze from an ArrayList StringBuilder representation of the maze. See readMe.txt
     * for details
     *
     * @param savedMaze the maze to be loaded
     */
    public MazeView loadMaze(ArrayList<StringBuilder> savedMaze, MazeView maze) {
        this.mazeView = maze;
        int cols = Integer.parseInt(savedMaze.get(3).toString());
        int rows = Integer.parseInt(savedMaze.get(2).toString());

        int bgColour = Integer.parseInt(savedMaze.get(0).toString());
        int playerType = Integer.parseInt(savedMaze.get(1).toString());


        int playerCol = Integer.parseInt(savedMaze.get(5).toString());
        int playerRow = Integer.parseInt(savedMaze.get(4).toString());

        Cell[][] cells = new Cell[cols][rows];

        for (int i = 0; i <= cols - 1; i++) {
            String[] currColCells = savedMaze.get(i + 6).toString().trim().split(" ");
            System.out.println(Arrays.toString(currColCells));
            for (int j = 0; j <= rows - 1; j++) {
                String currCell = currColCells[j].trim();
                cells[i][j] = new Cell(i, j);

                if (currCell.charAt(0) == '0')
                    cells[i][j].setHasLeftWall(false);

                if (currCell.charAt(1) == '0')
                    cells[i][j].setHasTopWall(false);

                if (currCell.charAt(2) == '0')
                    cells[i][j].setHasRightWall(false);

                if (currCell.charAt(3) == '0')
                    cells[i][j].setHasBottomWall(false);
            }
        }

        mazeView.setupOldMaze(cols, rows, bgColour, playerType, playerCol, playerRow, cells);

        return mazeView;

    }

    @Override
    public MazeView startNewMaze(int bgColour, String difficulty, int playerType) {
        this.mazeView = new MazeView(context, bgColour, difficulty, playerType, userManager);
        return mazeView;
    }

    public MazeView getMazeView() {
        return mazeView;
    }

    public void setMazeView(MazeView mazeView) {
        this.mazeView = mazeView;
    }
}
