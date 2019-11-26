package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SaveScoreActivity extends AppCompatActivity {

  int moleScore = 0;
  int moleHigh = 0;
  UserManager userManager;
  User user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_save_score);

    Intent intent = getIntent();
    //if from MoleActivity
    if(intent.getSerializableExtra(GameConstants.MoleScore) != null) {
      moleScore = (int) intent.getSerializableExtra(GameConstants.MoleScore);
      moleHigh = (int) intent.getSerializableExtra(GameConstants.MoleHigh);
    }

    //if from MazeCustomizationActivity
    UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
    if (user_1 != null) {
      setUserManager(user_1);
      user = userManager.getUser();
    }
  }

  private void setUserManager(UserManager newManager) {
    userManager = newManager;
  }

  public void save(View view) {
    user.setOverallScore(user.getOverallScore() + moleScore);
    user.setStatistic(GameConstants.NameGame1, GameConstants.MoleAllTimeHigh, moleHigh);
    userManager.updateStatistics(this, user);
    Intent intent = new Intent(this, GameActivity.class);
    intent.putExtra(GameConstants.USERMANAGER, userManager);
    startActivity(intent);
  }

  public void notSave(View view) {
    Intent intent = new Intent(this, GameActivity.class);
    intent.putExtra(GameConstants.USERMANAGER, userManager);
    startActivity(intent);
  }
}
