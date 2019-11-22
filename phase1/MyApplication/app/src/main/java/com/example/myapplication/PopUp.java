package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import static com.example.myapplication.MainActivity.USER;

public class PopUp extends Activity {

    private UserManager userManager;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        Intent intent = getIntent();
        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null){
            setUserManager(user_1);
            user = userManager.getUser();
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.8), (int)(height * 0.6));

        EditText mazeCompletedButton =  findViewById(R.id.mazeCompleted);
      /*  System.out.println(user.getNum_maze_games_played());
        System.out.println(user.getScore());
        System.out.println(user.getStreaks());*/
        String to_show = "maze games played: " + user.getNum_maze_games_played();
        mazeCompletedButton.setText(to_show);

        EditText molesHitButton =  findViewById(R.id.molesHit);
        String molesHit = "Moles Hit: " + user.getScore();
        molesHitButton.setText(molesHit);

        EditText levelButton =  findViewById(R.id.level);
        String level = "Last Played Level: " + user.getLast_played_level();
        levelButton.setText(level);

        EditText streakButton =  findViewById(R.id.streak);
        String streak = "TypeRacer Streak: " + user.getStreaks();
        streakButton.setText(streak);
    }

    private void setUserManager(UserManager newManager){
        userManager = newManager;
    }

    public void close(View view){
        finish();
    }
}
