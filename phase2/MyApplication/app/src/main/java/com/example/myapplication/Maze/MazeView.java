package com.example.myapplication.Maze;

import android.annotation.SuppressLint;
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
import com.example.myapplication.UserInfo.IUserManager;

import java.util.ArrayList;

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
    private int backgroundColour;

    /**
     * the type of the player. by default, this is 0 which represents lindsey. 1 is paul
     */
    private int playerType;

    /**
     * Determines if the maze will have collectibles or not
     */
    private boolean collectiblesEnabled;

    private IUserManager userManager;
    private IUser userInMaze;
    private int gamesPlayed = 0;
    private Context contexts;

    private MazeMaker mazeMaker;

    private Bitmap collectibleBitmap;
    private Bitmap gemCollectibleBitmap;
    private Bitmap playerBitmap;
    private Resources res = this.getResources();

    public MazeView(Context context, int backgroundColour, GameConstants.Difficulty difficulty,
                    int playerType, IUserManager user_1) {
        super(context);
        contexts = context;

        setDifficulty(difficulty);
        cells = new Cell[cols][rows];

        this.backgroundColour = backgroundColour;
        this.playerType = playerType;
        this.userManager = user_1;
        this.userInMaze = user_1.getUser();
        mazeMaker = new MazeCreation();

        wallPaint = new Paint();
        wallPaint.setColor(Color.BLACK);
        wallPaint.setStrokeWidth(GameConstants.MazeWallThickness);

        playerPaint = new Paint();
        playerPaint.setColor(Color.MAGENTA);

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

    public void setBackgroundColour(int backgroundColour) {
        this.backgroundColour = backgroundColour;
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
        cells = mazeMaker.makeMaze(cells, cols, rows);
        player = cells[0][0];
        exit = cells[cols - 1][rows - 1];
    }

    private void distributeCollectibles() {
        if (!collectibles.isEmpty())
            collectibles = new ArrayList<>();

        for (int i = 0; i < GameConstants.NumberOfMazeCollectibles; i++) {
            double randCollectibleType = Math.random();
            int[] coordinates = generateRandomCoordinates();

            //60% of the time make a gemCollectible
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
            randCoordinates[0] = (int) (Math.random() * (cols));
            randCoordinates[1] = (int) (Math.random() * (rows));
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

        invalidate();
    }

    private void checkExit() {
        if (player == exit && gamesPlayed < GameConstants.TotalMazeGames) {
            gamesPlayed += 1;
            createMaze();
            if (collectiblesEnabled)
                distributeCollectibles();

            if (gamesPlayed >= GameConstants.TotalMazeGames) {
                int CurrGamesPlayed = (int) userInMaze.getStatistic(GameConstants.MAZE, GameConstants.NumMazeGamesPlayed);
                this.userInMaze.setStatistic(GameConstants.MAZE,
                        GameConstants.NumMazeGamesPlayed, CurrGamesPlayed + gamesPlayed);
                this.userInMaze.setLastPlayedLevel(GameConstants.defaultLevel);
                //userManager.setOrUpdateStatistics(contexts, userInMaze, GameConstants.update);
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
     * https://codetheory.in/android-ontouchevent-ontouchlistener-motionevent-to-detect-common-gestures/
     * https://developer.android.com/training/graphics/opengl/touch#java
     * https://www.youtube.com/watch?v=SYoN-OvdZ3M part1-4 regarding onTouchEvent
     * all of these links were used to help us understand onTouch and how we can implement onTouch in
     * our own game properly.
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {

            float playerCentreX = horizontalMargin + (player.getCol() + 0.5f) * cellSize;
            float playerCentreY = verticalMargin + (player.getRow() + 0.5f) * cellSize;

            float differenceX = event.getX() - playerCentreX;
            float differenceY = event.getY() - playerCentreY;

            float absDifferenceX = Math.abs(differenceX);
            float absDifferenceY = Math.abs(differenceY);

            if (absDifferenceX > cellSize || absDifferenceY > cellSize) {
                if (absDifferenceX > absDifferenceY) {
                    if (differenceX > 0) {
                        movePlayer(GameConstants.Direction.RIGHT);
                    } else {
                        movePlayer(GameConstants.Direction.LEFT);
                    }
                } else {
                    if (differenceY > 0) {
                        movePlayer(GameConstants.Direction.DOWN);
                    } else {
                        movePlayer(GameConstants.Direction.UP);
                    }
                }
            }
            return true;
        }

        return super.onTouchEvent(event);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(backgroundColour);

        int width = getWidth();
        int height = getHeight();

        if (width / cols > height / rows) {
            cellSize = height / (rows + 1);
        } else {
            cellSize = width / (cols + 1);
        }

        horizontalMargin = (width - cols * cellSize) / 2;
        verticalMargin = (height - rows * cellSize) / 2;

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

            float margin = cellSize / 10;

            canvas.drawBitmap(playerBitmap, player.getCol() * cellSize + margin,
                    player.getRow() * cellSize + margin, playerPaint);

            canvas.drawRect(exit.getCol() * cellSize + margin,
                    exit.getRow() * cellSize + margin,
                    (exit.getCol() + 1) * cellSize - margin,
                    (exit.getRow() + 1) * cellSize - margin,
                    exitPaint);


                for (Collectible c : collectibles) {
                    canvas.drawBitmap(c.resizeBitmap((int) Math.floor(cellSize * 0.8),
                            (int) Math.floor(cellSize * 0.8)),
                            c.getCol() * cellSize + margin,
                            c.getRow() * cellSize + margin, collectiblePaint);
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

    public int getBackgroundColour() {
        return backgroundColour;
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




