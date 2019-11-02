package com.example.myapplication.WhackAMole;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.TypeRacer.TypeRacerCustomizationActivity;
import com.example.myapplication.User;
import com.example.myapplication.UserManager;

import static com.example.myapplication.MainActivity.USER;

public class MoleActivity extends AppCompatActivity {
  boolean passed;
  int numLives;
  int numColumns;
  int numRows;
  int backgroundID = R.drawable.game_background;
  int molesHit;
  int score = 0;
  public static boolean loaded;

  User user;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = getIntent();
    User user_1 = (User) intent.getSerializableExtra(USER);
    if (user_1 != null) {
      setUser(user_1);
    }
    user.setLast_played_level(1);
    UserManager.update_statistics(this, user);
    reset();

    if (loaded && !user.getLoad_moles_stats().equals("0")) {
      load(this, user);
    } else {
      setContentView(R.layout.activity_mole);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    UserManager.update_statistics(this, user);
  }

  @Override
  protected void onPause() {
    super.onPause();
    UserManager.update_statistics(this, user);
  }

  @Override
  public void onBackPressed() {}

  private void setUser(User new_user) {
    user = new_user;
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
    WamView wamView = new WamView(this);
    setContentView(wamView);
  }

  public void next(View view) {
    Button nextButton = findViewById(R.id.button);
    if (passed) {
      passed = false;
      user.setScore(user.getScore() + molesHit);
      user.setLoad_moles_stats("0");
      Intent intent = new Intent(this, TypeRacerCustomizationActivity.class);
      intent.putExtra(USER, user);
      startActivity(intent);
    } else {
      nextButton.setError("Please Pass This Level First");
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
    String load = user.getLoad_moles_stats();
    String[] stats = load.split(" ");

    this.score = Integer.parseInt(stats[3]);
    this.numLives = Integer.parseInt(stats[0]);
    this.numColumns = Integer.parseInt(stats[1]);
    this.numRows = Integer.parseInt(stats[2]);
    this.backgroundID = Integer.parseInt(stats[4]);
    WamView wamView = new WamView(this);
    setContentView(wamView);
    loaded = false;
    wamView.used = true;
    user.setLoad_moles_stats("0");
    UserManager.update_statistics(this, user);
  }
}
