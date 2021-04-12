package com.example.myapplication.WhackAMole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.GameConstants;
import com.example.myapplication.R;
import com.example.myapplication.SaveScoreActivity;
import com.example.myapplication.UserInfo.IUser;
import com.example.myapplication.UserInfo.IUserManager;

public class MoleActivity extends AppCompatActivity {
    int numLives;
    int numColumns;
    int numRows;
    int backgroundID = R.drawable.game_background;
    int molesHit;
    int score = 0;

    IUserManager userManager;
    IUser user;
    private WamView wamView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        IUserManager user_1 = (IUserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null) {
            setUserManager(user_1);
            user = userManager.getUser();
        }
        user.setLastPlayedLevel(GameConstants.whackAMoleLevel);
        writeMoleStats();
        reset();

        String loadStat = (String) user.getStatistic(GameConstants.WHACK_A_MOLE, GameConstants.MoleStats);
        if (loadStat.contains("-")) {
            String[] stats = loadStat.split("-");
            load(user, stats);
        } else {
            setContentView(R.layout.activity_mole);
        }
    }

    public void load(IUser user, String[] stats) {

        int lifeCount = Integer.parseInt(stats[0]);
        if (lifeCount > 0) {
            this.score = Integer.parseInt(stats[3]);
            this.numLives = lifeCount;
            this.numColumns = Integer.parseInt(stats[1]);
            this.numRows = Integer.parseInt(stats[2]);
            this.backgroundID = Integer.parseInt(stats[4]);
        } else {
            reset();
        }
        wamView = new WamView(this);
        setContentView(wamView);
        wamView.gameStatus = "inGame";
        wamView.thread_active = true;
        user.setStatistic(GameConstants.WHACK_A_MOLE, GameConstants.MoleStats, "0");
        writeMoleStats();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        writeMoleStats();
    }

    @Override
    protected void onPause() {
        super.onPause();
        writeMoleStats();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String loadStat = (String) user.getStatistic(GameConstants.WHACK_A_MOLE, GameConstants.MoleStats);
        if (loadStat.contains("-")) {
            String[] stats = loadStat.split("-");
            load(user, stats);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MoleInstructionActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    private void setUserManager(IUserManager newManager) {
        userManager = newManager;
    }

    public void onRadioButtonClicked(View view) {

        if (((RadioButton) view).isChecked()) {
            switch (view.getId()) {
                case R.id.hardcore:
                    numLives = GameConstants.moleHardcoreLives;
                    break;
                case R.id.difficult:
                    numLives = GameConstants.moleDifficultLives;
                    break;
                case R.id.noraml:
                    numLives = GameConstants.moleNormalLives;
                    break;
                case R.id.easy:
                    numLives = GameConstants.moleEasyLives;
                    break;
                case R.id.c1:
                    numColumns = 1;
                    break;
                case R.id.c2:
                    numColumns = 2;
                    break;
                case R.id.c3:
                    numColumns = 3;
                    break;
                case R.id.c4:
                    numColumns = 4;
                    break;
                case R.id.r1:
                    numRows = 1;
                    break;
                case R.id.r2:
                    numRows = 2;
                    break;
                case R.id.r3:
                    numRows = 3;
                    break;
                case R.id.r4:
                    numRows = 4;
                    break;
                case R.id.beach:
                    backgroundID = R.drawable.game_background;
                    break;
                case R.id.grass:
                    backgroundID = R.drawable.game_background_grass;
                    break;
                case R.id.space:
                    backgroundID = R.drawable.game_background_space;
                    break;
            }
        }
    }

    public void play(View view) {
        wamView = new WamView(this);
        wamView.thread_active = true;
        setContentView(wamView);
        wamView.gameStatus = "inGame";
    }

    public void powerPlay(View view) {
        Button powerPlayButton = findViewById(R.id.powerPlay);
        if (user.getCurrency() >= GameConstants.molePowerPlayCost) {
            user.setCurrency(user.getCurrency() - GameConstants.molePowerPlayCost);
            writeMoleStats();
            this.score = GameConstants.powerPlayPoint;
            wamView = new WamView(this);
            wamView.thread_active = true;
            setContentView(wamView);
            wamView.gameStatus = "inGame";
        } else {
            powerPlayButton.setError("Not Enough Gems!");
        }
    }

    public void conclude() {
        int CurrMolesHit = (int) user.getStatistic(GameConstants.WHACK_A_MOLE, GameConstants.MoleHit);
        user.setStatistic(GameConstants.WHACK_A_MOLE, GameConstants.MoleHit, CurrMolesHit + molesHit);
        molesHit = 0;
        int moleHigh = Math.max(wamView.wamManager.score, (int) user.getStatistic(GameConstants.WHACK_A_MOLE, GameConstants.MoleAllTimeHigh));
        user.setLastPlayedLevel(GameConstants.defaultLevel);
        writeMoleStats();
        Intent intent = new Intent(this, SaveScoreActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        intent.putExtra(GameConstants.MoleScore, this.wamView.wamManager.score);
        intent.putExtra(GameConstants.MoleHigh, moleHigh);
        intent.putExtra(GameConstants.gameName, GameConstants.moleName);
        startActivity(intent);

    }

    /**
     * Reset customizations to default.
     */

    public void reset() {
        numLives = GameConstants.moleDefaultLives;
        numRows = GameConstants.moleDefaultHolesY;
        numColumns = GameConstants.moleDefaultHolesX;
        score = 0;
        backgroundID = R.drawable.game_background;
    }

    /**
     * Write current user statistics to txt for storage.
     */

    public void writeMoleStats() {
        userManager.setOrUpdateStatistics(getApplicationContext(), user, GameConstants.update);
    }

}
