package com.example.myapplication.WhackAMole;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.myapplication.R;


public class WamView extends SurfaceView implements SurfaceHolder.Callback, Runnable {


    public String gameStatus = "inGame";
    public static int screenWidth, screenHeight;
    public Bitmap molePic, holePic, background, lifePic, scoreBoard;

    private Resources res = this.getResources();
    private Canvas canvas;
    private Paint paint;
    private SurfaceHolder holder;
    private boolean thread_active;

    public WamCollection wamCollection;

    private String endScore;
    private String end_message1;
    private String end_message2;

    public WamView(Context context) {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);
        paint = new Paint();
    }

    public void surfaceCreated(SurfaceHolder holder) {

        screenWidth = this.getWidth();
        screenHeight = this.getHeight();
        initializeGame();

        Thread thread = new Thread(this);
        thread.start();

        thread_active = true;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    protected void initializeGame() {

        background = BitmapFactory.decodeResource(res, R.drawable.game_background);
        background = SizeAndCoordinate.resizeBitmap(background, screenWidth, screenHeight);

        holePic = BitmapFactory.decodeResource(res, R.drawable.hole);
        holePic = SizeAndCoordinate.resizeBitmap(holePic, 300, 300);

        molePic = BitmapFactory.decodeResource(res, R.drawable.lindsey_mole);
        molePic = (SizeAndCoordinate.resizeBitmap(molePic, 250, 250));

        lifePic = BitmapFactory.decodeResource(res, R.drawable.life);
        lifePic = SizeAndCoordinate.resizeBitmap(lifePic, 150, 150);


        scoreBoard = BitmapFactory.decodeResource(res, R.drawable.text_bg_bmp);
        scoreBoard = SizeAndCoordinate.resizeBitmap(scoreBoard, 900, 525);

        Rect mole_rect = new Rect(0, screenHeight * 2 / 7, screenWidth, screenHeight * 5 / 6);

        wamCollection = new WamCollection(holePic, mole_rect, molePic, lifePic, 3, 2, 2, this);
        wamCollection.initialize();

        endScore = "Score:" + this.wamCollection.score;
        end_message1 = "Press Any Where";
        end_message2 = "to Play Again";
    }

    public void reinitializeGame() {
        wamCollection.reinitialize();
    }


    public void myDraw() {

        try {
            canvas = holder.lockCanvas();
            if (canvas != null) {
                switch (gameStatus) {
                    case "inGame":
                        canvas.drawBitmap(background, 0, 0, paint);
                        wamCollection.draw(canvas, paint);
                        break;
                    case "end":
                        canvas.drawBitmap(scoreBoard, screenWidth / 7, screenHeight * 4 / 7, paint);
                        canvas.drawText(endScore, screenWidth / 4, screenHeight * 2 / 3, paint);
                        canvas.drawText(end_message1, screenWidth / 4, screenHeight * 11 / 15, paint);
                        canvas.drawText(end_message2, screenWidth / 4, screenHeight * 12 / 15, paint);

                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (gameStatus) {
            case "inGame":
                this.inGameTouch(event, wamCollection);
                return false;
            case "end":
                reinitializeGame();
                gameStatus = "inGame";
        }
        return true;
    }

    private void gameTransition() {
        switch (gameStatus) {
            case "inGame":
                this.wamCollection.setMoleMovement();
                if (this.wamCollection.currentLives <= 0) {
                    this.gameStatus = "end";
                }
                break;
            case "end":
                endScore = "Score:" + this.wamCollection.score;
                break;
        }
    }

    private void inGameTouch(MotionEvent event, WamCollection wc) {
        int x, y;
        x = (int) event.getX();
        y = (int) event.getY();
        for (Mole mole : wc.moleList) {
            Rect rect = mole.getTouchRect();
            if (SizeAndCoordinate.contains(rect, x, y) && mole.getState() != Mole.Movement.HIT) {
                mole.setState(Mole.Movement.HIT);
                this.wamCollection.score += 1;
            }
        }
    }

    @Override
    public void run() {
        while (thread_active) {
            long start_time = System.currentTimeMillis();
            myDraw();
            gameTransition();
            long end_time = System.currentTimeMillis();
            if (end_time - start_time < 50) {
                try {
                    Thread.sleep(end_time - start_time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
