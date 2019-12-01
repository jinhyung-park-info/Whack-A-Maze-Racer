package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.UserInfo.IUser;
import com.example.myapplication.UserInfo.UserManager;

public class PopUp extends Activity {

    private UserManager userManager;
    private IUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        Intent intent = getIntent();
        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null) {
            setUserManager(user_1);
            user = userManager.getUser();
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.7));

        TextView mazeCompletedText = findViewById(R.id.mazeCompleted);
        String to_show = " maze games played: " + user.getStatistic(GameConstants.NameGame3, GameConstants.NumMazeGamesPlayed);
        mazeCompletedText.setText(to_show);

        TextView molesHitText = findViewById(R.id.molesHit);
        String molesHit = " Moles Hit: " + user.getStatistic(GameConstants.NameGame1, GameConstants.MoleHit);
        molesHitText.setText(molesHit);

        TextView currencyText = findViewById(R.id.currency);
        String currency = " Gems Remaining: " + user.getCurrency();
        currencyText.setText(currency);

        TextView streakText = findViewById(R.id.streak);
        String streak = " TypeRacer Streak: " + user.getStatistic(GameConstants.NameGame2, GameConstants.TypeRacerStreak);
        streakText.setText(streak);

        TextView overallText = findViewById(R.id.overall);
        String overall = " Overall Score: " + user.getOverallScore();
        overallText.setText(overall);

        TextView moleAllTimeHigh = findViewById(R.id.moleAllTimeHigh);
        String moleHigh = " Mole All Time High: " + user.getStatistic(GameConstants.NameGame1,
                GameConstants.MoleAllTimeHigh);
        moleAllTimeHigh.setText(moleHigh);

        TextView numMazeItems = findViewById(R.id.numMazeItems);
        String mazeItemsCollected = " Num maze items collected: " +
                user.getStatistic(GameConstants.NameGame3, GameConstants.NumCollectiblesCollectedMaze);
        numMazeItems.setText(mazeItemsCollected);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    private void setUserManager(UserManager newManager) {
        userManager = newManager;
    }

    public void buyCurrency(View view) {

        Intent intent = new Intent(this, InGamePurchaseActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }
}

