package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.WhackAMole.MoleActivity;

public class SaveScoreActivity extends AppCompatActivity {

  int moleScore;
  UserManager userManager;
  User user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_save_score);

    Intent intent = getIntent();
    moleScore = (int) intent.getSerializableExtra(GameConstants.MoleScore);
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
    user.setSaveToScoreBoard(true);
    user.setOverallScore(user.getOverallScore() + moleScore);
    userManager.updateStatistics(this, user);
    Intent intent = new Intent(this, GameActivity.class);
    intent.putExtra(GameConstants.USERMANAGER, userManager);
    startActivity(intent);
  }

  public void notSave(View view) {
    user.setSaveToScoreBoard(false);
    userManager.updateStatistics(this, user);
    Intent intent = new Intent(this, GameActivity.class);
    intent.putExtra(GameConstants.USERMANAGER, userManager);
    startActivity(intent);
  }
}
