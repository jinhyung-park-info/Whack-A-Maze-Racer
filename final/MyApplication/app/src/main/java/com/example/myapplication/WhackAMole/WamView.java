package com.example.myapplication.WhackAMole;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.myapplication.GameConstants;
import com.example.myapplication.R;

public class WamView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Resources res = this.getResources();
    private final MoleActivity activity = (MoleActivity) getContext();
    public String gameStatus = "inGame";
    public static int screenWidth, screenHeight;
    public static Bitmap genericMole, molePic, molePic2, molePic3, holePic, lifePic, scoreBoard;
    private Bitmap background;
    private Canvas canvas;
    private Paint paint;
    private SurfaceHolder holder;
    boolean thread_active; // If true, thread will continue to draw on Canvas.
    private String endScore;
    private String end_message1;
    private String end_message2;

    public WamManager wamManager;

    public WamView(Context context) {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);
        paint = new Paint();
    }

    public void surfaceCreated(SurfaceHolder holder) {

        screenWidth = getWidth();
        screenHeight = getHeight();
        initialize();

        Thread thread = new Thread(this);
        thread.start();

        thread_active = true;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    // Method burrowed but significantly modified from source 2).
    protected void initialize() {

        background = BitmapFactory.decodeResource(res, activity.backgroundID);
        background = Bitmap.createScaledBitmap(background, screenWidth, screenHeight, true);

        holePic = BitmapFactory.decodeResource(res, R.drawable.hole);
        holePic = Bitmap.createScaledBitmap(holePic, 300, 300, true);

        molePic = BitmapFactory.decodeResource(res, R.drawable.lindsey_mole);
        molePic = Bitmap.createScaledBitmap(molePic, 250, 250, true);

        genericMole = BitmapFactory.decodeResource(res, R.drawable.mole);
        genericMole = Bitmap.createScaledBitmap(genericMole, 250, 250, true);

        molePic2 = BitmapFactory.decodeResource(res, R.drawable.paul_mole);
        molePic2 = Bitmap.createScaledBitmap(molePic2, 250, 250, true);

        molePic3 = BitmapFactory.decodeResource(res, R.drawable.gem);
        molePic3 = Bitmap.createScaledBitmap(molePic3, 250, 250, true);

        lifePic = BitmapFactory.decodeResource(res, R.drawable.life);
        lifePic = Bitmap.createScaledBitmap(lifePic, 150, 150, true);

        scoreBoard = BitmapFactory.decodeResource(res, R.drawable.text_bg_bmp);
        scoreBoard = Bitmap.createScaledBitmap(scoreBoard, screenWidth * 4 / 5, screenHeight * 3 / 10, true);

        Rect mole_rect = new Rect(0, screenHeight * 2 / 7, screenWidth, screenHeight * 5 / 6);
        wamManager =
                new WamManager(
                        mole_rect,
                        activity.numLives,
                        activity.numColumns,
                        activity.numRows,
                        activity.score
                );
        wamManager.initialize();

        endScore = "Score:" + this.wamManager.score;
        end_message1 = "You have been";
        end_message2 = "overtaken by moles";
    }

    // Draw method inspired by FishTank Project.
    public void draw() {

        try {
            canvas = holder.lockCanvas();
            if (canvas != null) {
                switch (gameStatus) {
                    case "inGame":
                        canvas.drawBitmap(background, 0, 0, paint);
                        paint.setTextSize(WamView.screenHeight / 24);
                        paint.setColor(Color.WHITE);
                        canvas.drawText(
                                "Score:" + this.wamManager.score,
                                WamView.screenWidth * 2 / 3,
                                WamView.screenHeight / 18,
                                paint);
                        wamManager.draw(canvas, paint);
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

    // Obtained but significantly modified from source 2).
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (gameStatus) {
            case "inGame":
                this.inGameTouch(event, wamManager);
                return false;
            case "end":
                thread_active = false;
                activity.conclude();
        }
        return true;
    }

    // Update in game statistics.
    private void update() {
        upload_moles_stats(
                wamManager.currentLives
                        + "-"
                        + wamManager.holesX
                        + "-"
                        + wamManager.holesY
                        + "-"
                        + wamManager.score
                        + "-"
                        + activity.backgroundID);
        switch (gameStatus) {
            case "inGame":
                this.wamManager.move();
                if (this.wamManager.currentLives <= 0) {
                    this.gameStatus = "end";
                }
                break;
            case "end":
                endScore = "Score:" + this.wamManager.score;
                break;
        }
    }

    private void inGameTouch(MotionEvent event, WamManager wc) {
        int x, y;
        x = (int) event.getX();
        y = (int) event.getY();
        for (Mole mole : wc.moleList) {
            Rect rect = mole.getTouchRect();
            if (rect.contains(x, y) && mole.getState() != Mole.Movement.HIT && mole.getState() != Mole.Movement.STANDBY) {
                mole.setState(Mole.Movement.HIT);
                this.wamManager.score = Math.max(0, this.wamManager.score + mole.value);
                this.activity.molesHit += 1;
                this.activity.user.setCurrency(this.activity.user.getCurrency() + mole.gemValue);
            }
        }
    }

    /**
     * Update the current user's statistics to match stats
     *
     * @param stats to be updated
     */
    private void upload_moles_stats(String stats) {
        this.activity.user.setStatistic(GameConstants.WHACK_A_MOLE, GameConstants.MoleStats, stats);
    }

    // Inspired by source 3).
    @Override
    public void run() {
        while (thread_active) {
            long start_time = System.currentTimeMillis();
            draw();
            update();
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
