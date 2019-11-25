package com.example.myapplication.WhackAMole;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.GameActivity;
import com.example.myapplication.GameConstants;
import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.UserManager;

public class MoleActivity extends AppCompatActivity {
  boolean passed;
  int numLives;
  int numColumns;
  int numRows;
  int backgroundID = R.drawable.game_background;
  int molesHit;
  int score = 0;
  public static boolean loaded;

  private UserManager userManager;
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
    user.setLast_played_level(1);
    userManager.updateStatistics(this, user);
    reset();

    //if (loaded && !user.getLoad_moles_stats().equals("0")) {
    if (loaded && !user.getStatistic(GameConstants.NameGame1, GameConstants.MoleStats).equals("0")){
      load(this, user);
    } else {
      setContentView(R.layout.activity_mole);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    userManager.updateStatistics(this, user);
  }

  @Override
  protected void onPause() {
    super.onPause();
    userManager.updateStatistics(this, user);
  }

  @Override
  public void onBackPressed() {
    setContentView(R.layout.activity_mole);
    reset();
    wamView.thread_active = false;
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
  }

  public void next(View view) {
    Button nextButton = findViewById(R.id.button);
    if (passed) {
      passed = false;
      //user.setScore(user.getScore() + molesHit);
      //user.setLoad_moles_stats("0");
      int CurrMolesHit = (int) user.getStatistic(GameConstants.NameGame1, GameConstants.MoleHit);
      user.setStatistic(GameConstants.NameGame1, GameConstants.MoleHit, CurrMolesHit + molesHit);
      user.setLast_played_level(0);
      userManager.updateStatistics(getApplicationContext(), user);
      Intent intent = new Intent(this, GameActivity.class);
      intent.putExtra(GameConstants.USERMANAGER, userManager);
      startActivity(intent);
    } else {
      Toast.makeText(getApplicationContext(), "Please pass this level first",
              Toast.LENGTH_LONG).show();
    }
  }

  // Used to reset customization to default after restarting game.
  public void reset() {
    numLives = 5;
    numRows = 2;
    numColumns = 2;
    score = 0;
    backgroundID = R.drawable.game_background;
  }

  public void load(Context context, User user) {
    //String load = user.getLoad_moles_stats();
    String load = (String) user.getStatistic(GameConstants.NameGame1, GameConstants.MoleStats);
    String[] stats = load.split(" ");

    this.score = Integer.parseInt(stats[3]);
    this.numLives = Integer.parseInt(stats[0]);
    this.numColumns = Integer.parseInt(stats[1]);
    this.numRows = Integer.parseInt(stats[2]);
    this.backgroundID = Integer.parseInt(stats[4]);
    WamView wamView = new WamView(this);
    setContentView(wamView);
    loaded = false;
    //user.setLoad_moles_stats("0");
    user.setStatistic(GameConstants.NameGame1, GameConstants.MoleStats, "0");
    userManager.updateStatistics(this, user);
  }
}
