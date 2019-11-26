package com.example.myapplication.WhackAMole;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.GameConstants;
import com.example.myapplication.R;
import com.example.myapplication.SaveScoreActivity;
import com.example.myapplication.User;
import com.example.myapplication.UserManager;

public class MoleActivity extends AppCompatActivity {
  int numLives;
  int numColumns;
  int numRows;
  int backgroundID = R.drawable.game_background;
  int molesHit;
  int score = 0;
  public static boolean loaded;

  UserManager userManager;
  User user;
  private WamView wamView;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = getIntent();
    UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
    if (user_1 != null) {
      setUserManager(user_1);
      user = userManager.getUser();
    }
    user.setLastPlayedLevel(1);
    writeMoleStats();
    reset();

    //if (loaded && !user.getLoad_moles_stats().equals("0")) {
    if (loaded && !user.getStatistic(GameConstants.NameGame1, GameConstants.MoleStats).equals("0")){
      load(user);
    } else {
      setContentView(R.layout.activity_mole);
    }
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
  public void onBackPressed() {
    setContentView(R.layout.activity_mole);
    reset();
    if(wamView != null){
    wamView.thread_active = false;
    }
  }

  private void setUserManager(UserManager newManager){
    userManager = newManager;
  }

  public void onRadioButtonClicked(View view) {

    if (((RadioButton) view).isChecked()) {
      switch (view.getId()) {
        case R.id.hardcore:
          numLives = 1;
          break;
        case R.id.difficult:
          numLives = 3;
          break;
        case R.id.noraml:
          numLives = 5;
          break;
        case R.id.easy:
          numLives = 10;
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
    if(wamView == null){
      wamView = new WamView(this);
      }
    wamView.thread_active = true;
    setContentView(wamView);
    wamView.gameStatus = "inGame";
  }

  public void conclude() {
    int CurrMolesHit = (int) user.getStatistic(GameConstants.NameGame1, GameConstants.MoleHit);
    user.setStatistic(GameConstants.NameGame1, GameConstants.MoleHit, CurrMolesHit + molesHit);
    molesHit = 0;
    int moleHigh = Math.max(wamView.wamManager.score, (int) user.getStatistic(GameConstants.NameGame1, GameConstants.MoleAllTimeHigh));
    user.setLastPlayedLevel(0);
    writeMoleStats();
    Intent intent = new Intent(this, SaveScoreActivity.class);
    intent.putExtra(GameConstants.USERMANAGER, userManager);
    intent.putExtra(GameConstants.MoleScore, this.wamView.wamManager.score);
    intent.putExtra(GameConstants.MoleHigh, moleHigh);
    startActivity(intent);

  }

  // Used to reset customization to default after restarting game.
  public void reset() {
    numLives = 5;
    numRows = 2;
    numColumns = 2;
    score = 0;
    backgroundID = R.drawable.game_background;
  }

  public void writeMoleStats(){
    userManager.updateStatistics(getApplicationContext(), user);
  }

  public void load(User user) {
    String load = (String) user.getStatistic(GameConstants.NameGame1, GameConstants.MoleStats);
    String[] stats = load.split(" ");
    int lifeCount = Integer.parseInt(stats[0]);
    if(lifeCount > 0){
      this.score = Integer.parseInt(stats[3]);
      this.numLives = lifeCount;
      this.numColumns = Integer.parseInt(stats[1]);
      this.numRows = Integer.parseInt(stats[2]);
      this.backgroundID = Integer.parseInt(stats[4]);
      WamView wamView = new WamView(this);
      setContentView(wamView);
      }
    loaded = false;
    user.setStatistic(GameConstants.NameGame1, GameConstants.MoleStats, "0");
    writeMoleStats();

    }
}
