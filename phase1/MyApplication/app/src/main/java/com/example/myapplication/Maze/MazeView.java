package com.example.myapplication.Maze;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.UserManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

import static com.example.myapplication.MainActivity.USER;

public class MazeView extends View {

    private static final String TAG = "MazeView";

    /**
     * the directions the player could move in
     */
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    /**
     * a 2d array that holds the cell objects
     */
    private Cell[][] cells;

    /**
     * the player in the maze
     */
    private Cell player;

    /**
     * the exit of the maze
     */
    private Cell exit;

    /**
     * the size of each cell on the screen
     */
    private float cellSize;

    /**
     * the horizontal margin of the maze
     */
    private float horizontalMargin;

    /**
     * the vertical margin of the maze
     */
    private float verticalMargin;

    /**
     * the paint object of the walls in the maze
     */
    private Paint wallPaint;

    /**
     * the paint object of the player
     */
    private Paint playerPaint;

    /**
     * the paint object of the exit of the maze
     */
    private Paint exitPaint;

    /**
     * the number of columns the maze will have
     */
    private int cols;

    /**
     * the number of rows the maze will have
     */
    private int rows;

    /**
     * the background colour of the maze
     */
    private int bgColour;

    /**
     * the colour of the player
     */
    private int playerColour;

    /**
     * the thickness of the lines representing the walls
     */
    private static final float WALL_THICKNESS = 4;

    /**
     * Random number generator used when generating the maze
     */
    private Random rand;

    private User user_in_maze;
    static final int num_games = 2;
    private int games_played = 0;
    private Context contexts;


    public MazeView(Context context, int bgColour, String difficulty,
                    int playerColour, User user_1) {
        super(context);
        contexts = context;

        setDifficulty(difficulty);
        this.bgColour = bgColour;
        this.playerColour = playerColour;
        this.user_in_maze = user_1;

        setupPaintObjects();

        rand = new Random();

        createMaze();
    }

    public void setBgColour(int bgColour) {
        this.bgColour = bgColour;
    }

    public void setPlayerColour(int playerColour) {
        this.playerColour = playerColour;
    }

    public void setDifficulty(String difficulty) {
        if (difficulty.equals("Easy")) {
            rows = 7;
            cols = 5;
        } else if (difficulty.equals("Normal")) {
            rows = 11;
            cols = 7;
        } else if (difficulty.equals("Hard")) {
            rows = 15;
            cols = 12;
        }
    }

    /**
     * Initializes the wall, player, and exit paint objects
     */
    private void setupPaintObjects() {
        //setup wallPaint
        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(WALL_THICKNESS);

        //setup playerPaint
        playerPaint = new Paint();
        playerPaint.setColor(playerColour);

        //setup exitPaint
        exitPaint = new Paint();
        exitPaint.setColor(Color.BLACK);
    }

    /**
     * Returns a neighbouring cell of the given cell. If there is more than one neighbour,
     * returns a random neighbour.
     *
     * @param currentCell
     * @return neighbourCell
     */
    private Cell getNeighbour(Cell currentCell) {
        //check for unvisited neighbours
        ArrayList<Cell> neighbours = new ArrayList<>();

        //left neighbour
        if (currentCell.getCol() > 0) {
            if (!cells[currentCell.getCol() - 1][currentCell.getRow()].wasVisited()) {
                neighbours.add(cells[currentCell.getCol() - 1][currentCell.getRow()]);
            }
        }
        //right neighbour
        if (currentCell.getCol() < cols - 1) {
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
        if (currentCell.getRow() < rows - 1) {
            if (!cells[currentCell.getCol()][currentCell.getRow() + 1].wasVisited()) {
                neighbours.add(cells[currentCell.getCol()][currentCell.getRow() + 1]);
            }
        }

        if (neighbours.size() > 0) {
            int chosenNeighbourIndex = rand.nextInt(neighbours.size());
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
            current.setTopWall(false);
            next.setBottomWall(false);
        }
        //next cell is below current
        else if (current.getCol() == next.getCol() && current.getRow() == next.getRow() - 1) {
            current.setBottomWall(false);
            next.setTopWall(false);
        }
        //next cell is to the left of current
        else if (current.getCol() == next.getCol() + 1 && current.getRow() == next.getRow()) {
            current.setLeftWall(false);
            next.setRightWall(false);
        }
        //next cell is to the right of current
        else if (current.getCol() == next.getCol() - 1 && current.getRow() == next.getRow()) {
            current.setRightWall(false);
            next.setLeftWall(false);
        }


    }

    /**
     * Initializes the 2d array of cells and initializes each individual cell element in the array.
     * https://www.youtube.com/watch?v=8Ju_uxJ9v44
     * https://stackoverflow.com/questions/38502/whats-a-good-algorithm-to-generate-a-maze
     * These links were used in order to help us choose  which algorithm to create the maze and to
     * create the maze
     */
    private void createMaze() {
        //recursive backtracking algorithm for creating mazes
        Stack<Cell> stackVisitedCells = new Stack<>();
        Cell current, next;

        cells = new Cell[cols][rows];

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }

        player = cells[0][0];
        exit = cells[cols - 1][rows - 1];

        current = cells[0][0];
        current.setVisited(true);

        //do this until all cells have been visited
        do {
            next = getNeighbour(current);

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
    }

    private void movePlayer(Direction direction) {
        switch (direction) {
            case UP:
                if (!player.hasTopWall()) {
                    player = cells[player.getCol()][player.getRow() - 1];
                }
                break;
            case DOWN:
                if (!player.hasBottomWall()) {
                    player = cells[player.getCol()][player.getRow() + 1];
                }
                break;
            case LEFT:
                if (!player.hasLeftWall()) {
                    player = cells[player.getCol() - 1][player.getRow()];
                }
                break;
            case RIGHT:
                if (!player.hasRightWall()) {
                    player = cells[player.getCol() + 1][player.getRow()];
                }
        }

        checkExit();

        //to update the positions on the screen, we have to call onDraw()
        //invalidate() calls onDraw() as soon as possible
        invalidate();
    }

    private void checkExit() {
        if (player == exit && games_played < num_games) {
            games_played += 1;
            createMaze();
            if (games_played >= num_games) {
                this.user_in_maze.setNum_maze_games_played(user_in_maze.getNum_maze_games_played() + games_played);
                this.user_in_maze.setLast_played_level(0);
                UserManager.update_statistics(contexts, user_in_maze);
                Activity activity = (MazeCustomizationActivity) contexts;
                ((MazeCustomizationActivity) contexts).reset();
                activity.setContentView(R.layout.activity_maze_customization);
                MazeCustomizationActivity.passed = true;

            }
        }
    }

    /**
     * Returns a Array List String Builder representation of this maze. See readMe.txt for details
     *
     * @return saved maze
     */
    public ArrayList<StringBuilder> saveMaze() {
        //initialize arraylist to store lines of text to represent the maze
        ArrayList<StringBuilder> savedMaze = new ArrayList<>();
        //set the first index of arraylist to the colour of the maze
        savedMaze.add(new StringBuilder(Integer.toString(bgColour)));
        //set second index of arraylist to the player's colour
        savedMaze.add(new StringBuilder(Integer.toString(playerColour)));
        //set third index of arraylist to the # of rows in maze
        savedMaze.add(new StringBuilder(Integer.toString(rows)));
        //set fourth index of arraylist to the # of cols in maze
        savedMaze.add(new StringBuilder(Integer.toString(cols)));
        //set fifth index of arraylist to row of player
        savedMaze.add(new StringBuilder(Integer.toString(player.getRow())));
        //set sixth index of arraylsist to col of player
        savedMaze.add(new StringBuilder(Integer.toString(player.getCol())));
        //for each row in the 2d array of cells
        for (int i = 0; i <= cols - 1; i++) {
            //initialize a stringbuilder
            StringBuilder currColString = new StringBuilder();
            //for each cell in the current col
            for (Cell c : cells[i]) {
                //if left wall is true
                if (c.hasLeftWall())
                    //append 1
                    currColString.append(1);
                else
                    //append 0
                    currColString.append(0);
                //if top wall is true
                if (c.hasTopWall())
                    //append 1
                    currColString.append(1);
                else
                    //append 0
                    currColString.append(0);
                //if right wall is true
                if (c.hasRightWall())
                    //append 1
                    currColString.append(1);
                else
                    //append 0
                    currColString.append(0);
                //if bottom wall is true
                if (c.hasBottomWall())
                    //append 1
                    currColString.append(1);
                else
                    //append 0
                    currColString.append(0);
                //append space
                currColString.append(" ");

            }
            //add stringbuilder to arraylist
            savedMaze.add(currColString);
        }
        //System.out.println(savedMaze);
        return savedMaze;
    }

    /**
     * Loads the maze from an ArrayList StringBuilder representation of the maze. See readMe.txt
     * for details
     *
     * @param savedMaze the maze to be loaded
     */
    public void loadMaze(ArrayList<StringBuilder> savedMaze) {
        //reinitialize 2d array of cells with rows as index 2 in arraylist and columns at the index 3 in arraylist
        cols = Integer.parseInt(savedMaze.get(3).toString());
        rows = Integer.parseInt(savedMaze.get(2).toString());
        cells = new Cell[cols][rows];
        System.out.println(cols);
        System.out.println(rows);
        //set the background colour to index 0 in the arraylist
        bgColour = Integer.parseInt(savedMaze.get(0).toString());
        //set the player's colour to index 1 in the arraylist
        playerColour = Integer.parseInt(savedMaze.get(1).toString());
        int playerCol = Integer.parseInt(savedMaze.get(5).toString());
        int playerRow = Integer.parseInt(savedMaze.get(4).toString());
        //for i from 0 to # of cols - 1
        for (int i = 0; i <= cols - 1; i++) {
            //take the ith index (plus 6) of the arraylist, convert the stringbuilder to a string and split that string by spaces
            String[] currColCells = savedMaze.get(i + 6).toString().trim().split(" ");
            System.out.println(Arrays.toString(currColCells));
            //in this splitted string, for each index j
            for (int j = 0; j <= rows - 1; j++) {
                String currCell = currColCells[j].trim();
                //initialize a new cell with col = i, row = j
                cells[i][j] = new Cell(i, j);
                //if the char in location 0 is 0
                if (currCell.charAt(0) == '0')
                    //set left wall in cell to false
                    cells[i][j].setLeftWall(false);
                //if the char in location 1 is 0
                if (currCell.charAt(1) == '0')
                    //set top wall in cell to false
                    cells[i][j].setTopWall(false);
                //if the char in location 2 is 0
                if (currCell.charAt(2) == '0')
                    //set right wall in cell to false
                    cells[i][j].setRightWall(false);
                //if the char in location 3 is 0
                if (currCell.charAt(3) == '0')
                    //set bottom wall in cell to false
                    cells[i][j].setBottomWall(false);
            }
        }

        //let player equal the cell in the 2d array at 0,0
        player = cells[playerCol][playerRow];
        //let exit equal the cell in the 2d array at col - 1, row - 1
        exit = cells[cols - 1][rows - 1];

        //redraw maze
        invalidate();

    }

    /**
    https://codetheory.in/android-ontouchevent-ontouchlistener-motionevent-to-detect-common-gestures/
    https://developer.android.com/training/graphics/opengl/touch#java
    https://www.youtube.com/watch?v=SYoN-OvdZ3M part1-4 regarding onTouchEvent
    all of these links were used to help us understand onTouch and how we can implement onTouch in
    our own game properly.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //check for this and then we'll be able to check for ACTION_MOVE events
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();

            //0.5 is a double so we must use 0.5f which represents a float
            float playerCentreX = horizontalMargin + (player.getCol() + 0.5f) * cellSize;
            float playerCentreY = verticalMargin + (player.getRow() + 0.5f) * cellSize;

            float differenceX = x - playerCentreX;
            float differenceY = y - playerCentreY;

            float absDifferenceX = Math.abs(differenceX);
            float absDifferenceY = Math.abs(differenceY);

            if (absDifferenceX > cellSize || absDifferenceY > cellSize) {
                if (absDifferenceX > absDifferenceY) {
                    //move in x direction (left or right)
                    if (differenceX > 0) {
                        //move to the right
                        movePlayer(Direction.RIGHT);
                    } else {
                        //move to the left
                        movePlayer(Direction.LEFT);
                    }
                } else {
                    //move in y direction (up or down)
                    if (differenceY > 0) {
                        //move down
                        movePlayer(Direction.DOWN);
                    } else {
                        //move up
                        movePlayer(Direction.UP);
                    }
                }
            }
            return true; //event has been handled
        }

        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //new_canvas = canvas;
        canvas.drawColor(bgColour);

        int width = getWidth();
        int height = getHeight();

        //we are making the margins the length of half a cell, so collectively they take up
        //the space of one cell. so we have to create our maze within this limited space.
        //also since cells are squares, the size of a cell represents both the cell's height & width

        if (width / cols > height / rows) {
            //adding 1 to the # of cols takes into account the space the margin takes up
            cellSize = height / (rows + 1);
        } else {
            //adding 1 to the # of rows takes into account the space the margin takes up
            cellSize = width / (cols + 1);
        }

        //the margins are half the size of a cell
        horizontalMargin = (width - cols * cellSize) / 2;
        verticalMargin = (height - rows * cellSize) / 2;

        //moves the the top left corner of the canvas horizontalMargin units to the right
        //and verticalMargin units down
        canvas.translate(horizontalMargin, verticalMargin);

        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                if (cells[x][y].hasTopWall()) {
                    canvas.drawLine(x * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            y * cellSize,
                            wallPaint);
                }
                if (cells[x][y].hasLeftWall()) {
                    canvas.drawLine(x * cellSize,
                            y * cellSize,
                            (x) * cellSize,
                            (y + 1) * cellSize,
                            wallPaint);
                }
                if (cells[x][y].hasBottomWall()) {
                    canvas.drawLine(x * cellSize,
                            (y + 1) * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            wallPaint);
                }
                if (cells[x][y].hasRightWall()) {
                    canvas.drawLine((x + 1) * cellSize,
                            y * cellSize,
                            (x + 1) * cellSize,
                            (y + 1) * cellSize,
                            wallPaint);
                }
            }

            //we will add this to the top left corner and subtract it from the bottom right corner
            float margin = cellSize / 10;
            //draw the player
            canvas.drawRect(player.getCol() * cellSize + margin,
                    player.getRow() * cellSize + margin,
                    (player.getCol() + 1) * cellSize - margin,
                    (player.getRow() + 1) * cellSize - margin,
                    playerPaint);

            //draw the exit
            canvas.drawRect(exit.getCol() * cellSize + margin,
                    exit.getRow() * cellSize + margin,
                    (exit.getCol() + 1) * cellSize - margin,
                    (exit.getRow() + 1) * cellSize - margin,
                    exitPaint);
        }


    }

    private class Cell {
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
}
