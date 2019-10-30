package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import static com.example.myapplication.MainActivity.USER;

public class PopUp extends Activity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        Intent intent = getIntent();
        User user_1 = (User) intent.getSerializableExtra(USER);
        if (user_1 != null){
            setUser(user_1);
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.8), (int)(height * 0.6));

        EditText statistic_1 =  findViewById(R.id.editText5);
      /*  System.out.println(user.getNum_maze_games_played());
        System.out.println(user.getScore());
        System.out.println(user.getStreaks());*/
        String to_show = "maze games played: " + user.getNum_maze_games_played();
        statistic_1.setText(to_show);

        EditText statistic_2 =  findViewById(R.id.score);
      /*  System.out.println(user.getNum_maze_games_played());
        System.out.println(user.getScore());
        System.out.println(user.getStreaks());*/
        String molesHit = "Moles Hit: " + user.getScore();
        statistic_2.setText(molesHit);

    }

    private void setUser(User new_user){
        user = new_user;
    }

    public void close(View view){
        finish();
    }
}
