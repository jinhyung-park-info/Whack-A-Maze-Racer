package com.example.myapplication.Maze;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.GameConstants;
import com.example.myapplication.R;
import com.example.myapplication.UserInfo.IUser;
import com.example.myapplication.UserInfo.UserManager;

import java.util.ArrayList;
import java.util.Arrays;

public class MazeView extends View {


    /**
     * a 2d array that holds the cell objects
     */
    Cell[][] cells;

    /**
     * the player in the maze
     */
    Cell player;

    /**
     * A list of collectibles in the maze
     */
    ArrayList<Collectible> collectibles;

    /**
     * the exit of the maze
     */
    Cell exit;

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
     * the paint object of the collectibles of the maze
     */
    private Paint collectiblePaint;

    /**
     * the number of columns the maze will have
     */
    int cols;

    /**
     * the number of rows the maze will have
     */
    int rows;

    /**
     * the background colour of the maze
     */
    private int bgColour;

    /**
     * the type of the player. by default, this is 0 which represents lindsey. 1 is paul
     */
    private int playerType;

    /**
     * Determines if the maze will have collectibles or not
     */
    private boolean collectiblesEnabled;

    private UserManager userManager;
    private IUser userInMaze;
    private int gamesPlayed = 0;
    private Context contexts;

    private MazeCreation mazeCreation;

    private Bitmap collectibleBitmap;
    private Bitmap gemCollectibleBitmap;
    private Bitmap playerBitmap;
    private Resources res = this.getResources();

    public MazeView(Context context, int bgColour, GameConstants.Difficulty difficulty,
                    int playerType, UserManager user_1) {
        super(context);
        contexts = context;

        setDifficulty(difficulty);
        cells = new Cell[cols][rows];

        this.bgColour = bgColour;
        this.playerType = playerType;
        this.userManager = user_1;
        this.userInMaze = user_1.getUser();
        mazeCreation = new MazeCreation();

        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(GameConstants.MazeWallThickness);

        //setup playerPaint
        playerPaint = new Paint();
        playerPaint.setColor(Color.MAGENTA);

        //setup exitPaint
        exitPaint = new Paint();
        exitPaint.setColor(Color.BLACK);

        collectiblesEnabled = true;
        collectibles = new ArrayList<Collectible>();
        collectibleBitmap = BitmapFactory.decodeResource(res, R.drawable.a_plus);
        gemCollectibleBitmap = BitmapFactory.decodeResource(res, R.drawable.gem);
        collectiblePaint = new Paint();
        collectiblePaint.setColor(Color.LTGRAY);

        if (playerType == 0)
            playerBitmap = BitmapFactory.decodeResource(res, R.drawable.lindsey_mole);
        else
            playerBitmap = BitmapFactory.decodeResource(res, R.drawable.paul_mole);

        createMaze();
        distributeCollectibles();
    }

    public MazeView(Context context, UserManager user_1) {
        super(context);
        contexts = context;

        this.userManager = user_1;
        this.userInMaze = user_1.getUser();
        mazeCreation = new MazeCreation();

        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(GameConstants.MazeWallThickness);

        //setup playerPaint
        playerPaint = new Paint();
        playerPaint.setColor(Color.MAGENTA);

        //setup exitPaint
        exitPaint = new Paint();
        exitPaint.setColor(Color.BLACK);

        collectiblesEnabled = true;
        collectibles = new ArrayList<Collectible>();
        collectibleBitmap = BitmapFactory.decodeResource(res, R.drawable.a_plus);
        gemCollectibleBitmap = BitmapFactory.decodeResource(res, R.drawable.gem);
        collectiblePaint = new Paint();
        collectiblePaint.setColor(Color.LTGRAY);

        if (playerType == 0)
            playerBitmap = BitmapFactory.decodeResource(res, R.drawable.lindsey_mole);
        else
            playerBitmap = BitmapFactory.decodeResource(res, R.drawable.paul_mole);
    }

    public void setBgColour(int bgColour) {
        this.bgColour = bgColour;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }

    public void setDifficulty(GameConstants.Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                rows = GameConstants.RowsForEasyMaze;
                cols = GameConstants.ColsForEasyMaze;
                break;
            case NORMAL:
                rows = GameConstants.RowsForNormalMaze;
                cols = GameConstants.ColsForNormalMaze;
                break;
            case HARD:
                rows = GameConstants.RowsForHardMaze;
                cols = GameConstants.ColsForHardMaze;
                break;
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
        cells = mazeCreation.makeMaze(cells, cols, rows);
        player = cells[0][0];
        exit = cells[cols - 1][rows - 1];
    }

    private void distributeCollectibles() {
        for (int i = 0; i < GameConstants.NumberOfMazeCollectibles; i++) {
            double randCollectibleType = Math.random();
            int[] coordinates = generateRandomCoordinates();

            if (randCollectibleType < 0.6)
                collectibles.add(new GemCollectible(coordinates[0], coordinates[1], gemCollectibleBitmap));
            else
                collectibles.add(new Collectible(coordinates[0], coordinates[1], collectibleBitmap));
        }
    }

    private int[] generateRandomCoordinates() {
        int[] randCoordinates = new int[2];

        while ((randCoordinates[0] == 0 && randCoordinates[1] == 0) ||
                (randCoordinates[0] == cols - 1 && randCoordinates[1] == rows - 1)) {
            randCoordinates[0] = (int) (Math.random() * (cols)); //setting rand col
            randCoordinates[1] = (int) (Math.random() * (rows)); //setting rand row
        }

        return randCoordinates;
    }

    private void movePlayer(GameConstants.Direction direction) {
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

        if (collectiblesEnabled) {
            checkCollectibles();
        }

        //to update the positions on the screen, we have to call onDraw()
        //invalidate() calls onDraw() as soon as possible
        invalidate();
    }

    private void checkExit() {
        if (player == exit && gamesPlayed < GameConstants.TotalMazeGames) {
            gamesPlayed += 1;
            createMaze();
            if (gamesPlayed >= GameConstants.TotalMazeGames) {
                int CurrGamesPlayed = (int) userInMaze.getStatistic(GameConstants.NameGame3, GameConstants.NumMazeGamesPlayed);
                this.userInMaze.setStatistic(GameConstants.NameGame3,
                        GameConstants.NumMazeGamesPlayed, CurrGamesPlayed + gamesPlayed);
                this.userInMaze.setLastPlayedLevel(0);
                userManager.updateStatistics(contexts, userInMaze);
                Activity activity = (MazeCustomizationActivity) contexts;
                ((MazeCustomizationActivity) contexts).reset();
                activity.setContentView(R.layout.activity_maze_customization);
                MazeCustomizationActivity.passed = true;

            }
        }
    }

    private void checkCollectibles() {
        ArrayList<Collectible> collectiblesToRemove = new ArrayList<>();

        for (Collectible c : collectibles) {
            if (player.getCol() == c.getCol() && player.getRow() == c.getRow()) {
                c.handleCollection(userInMaze);
                collectiblesToRemove.add(c);
            }
        }

        for (Collectible c : collectiblesToRemove) {
            collectibles.remove(c);
        }
    }

    /**
     * Loads the maze from an ArrayList StringBuilder representation of the maze. See readMe.txt
     * for details
     */
    public void setupOldMaze(int cols, int rows, int bgColour, int playerType, int playerCol,
                             int playerRow, Cell[][] cells) {
        //leaving and returning to the maze scares away the collectibles
        collectiblesEnabled = false;
        this.cols = cols;
        this.rows = rows;
        this.bgColour = bgColour;
        this.playerType = playerType;

        this.cells = cells;
        player = this.cells[playerCol][playerRow];
        exit = this.cells[cols - 1][rows - 1];

        //redraw maze
        invalidate();
    }

    /**
     * https://codetheory.in/android-ontouchevent-ontouchlistener-motionevent-to-detect-common-gestures/
     * https://developer.android.com/training/graphics/opengl/touch#java
     * https://www.youtube.com/watch?v=SYoN-OvdZ3M part1-4 regarding onTouchEvent
     * all of these links were used to help us understand onTouch and how we can implement onTouch in
     * our own game properly.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //check for this and then we'll be able to check for ACTION_MOVE events
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            /*float x = event.getX();
            float y = event.getY();*/

            float playerCentreX = horizontalMargin + (player.getCol() + 0.5f) * cellSize;
            float playerCentreY = verticalMargin + (player.getRow() + 0.5f) * cellSize;

            float differenceX = event.getX() - playerCentreX;
            float differenceY = event.getY() - playerCentreY;

            float absDifferenceX = Math.abs(differenceX);
            float absDifferenceY = Math.abs(differenceY);

            if (absDifferenceX > cellSize || absDifferenceY > cellSize) {
                if (absDifferenceX > absDifferenceY) {
                    //move in x direction (left or right)
                    if (differenceX > 0) {
                        //move to the right
                        movePlayer(GameConstants.Direction.RIGHT);
                    } else {
                        //move to the left
                        movePlayer(GameConstants.Direction.LEFT);
                    }
                } else {
                    //move in y direction (up or down)
                    if (differenceY > 0) {
                        //move down
                        movePlayer(GameConstants.Direction.DOWN);
                    } else {
                        //move up
                        movePlayer(GameConstants.Direction.UP);
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

        rescaleBitmaps();

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
            canvas.drawBitmap(playerBitmap, player.getCol() * cellSize + margin,
                    player.getRow() * cellSize + margin, playerPaint);

            //draw the exit
            canvas.drawRect(exit.getCol() * cellSize + margin,
                    exit.getRow() * cellSize + margin,
                    (exit.getCol() + 1) * cellSize - margin,
                    (exit.getRow() + 1) * cellSize - margin,
                    exitPaint);

            if (collectiblesEnabled) {
                //draw collectibles
                for (Collectible c : collectibles) {
                    canvas.drawBitmap(c.resizeBitmap((int) Math.floor(cellSize * 0.8),
                            (int) Math.floor(cellSize * 0.8)),
                            c.getCol() * cellSize + margin,
                            c.getRow() * cellSize + margin, collectiblePaint);
                }
            }
        }

    }

    /**
     * Resizes all the bitmap images used in the maze.
     */
    private void rescaleBitmaps() {
        collectibleBitmap = Bitmap.createScaledBitmap(collectibleBitmap,
                (int) Math.floor(cellSize * 0.8),
                (int) Math.floor(cellSize * 0.8), true);
        playerBitmap = Bitmap.createScaledBitmap(playerBitmap,
                (int) Math.floor(cellSize * 0.8),
                (int) Math.floor(cellSize * 0.8), true);
        gemCollectibleBitmap = Bitmap.createScaledBitmap(gemCollectibleBitmap,
                (int) Math.floor(cellSize * 0.8),
                (int) Math.floor(cellSize * 0.8), true);
    }

    public void setSpecificCell(int i, int j, Cell cell) {
        cells[i][j] = cell;
    }

    public Cell getSpecificCell(int i, int j) {
        return cells[i][j];
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }

    public int getBgColour() {
        return bgColour;
    }

    public int getPlayerType() {
        return playerType;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getPlayer() {
        return player;
    }
}




