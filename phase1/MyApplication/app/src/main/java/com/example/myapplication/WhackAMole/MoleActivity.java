package com.example.myapplication.WhackAMole;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.TypeRacer.TypeRacerCustomizationActivity;
import com.example.myapplication.User;
import com.example.myapplication.UserManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

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
        setContentView(R.layout.activity_mole);
        Intent intent = getIntent();
        User user_1 = (User) intent.getSerializableExtra(USER);
        if (user_1 != null) {
            setUser(user_1);
        }
        user.setLast_played_level(1);
        UserManager.update_statistics(this, user, user.getScore(), user.getStreaks(),
                user.getNum_maze_games_played(), user.getLast_played_level(), user.getLoad_moles_stats());
        reset();

        if (loaded) {
            load(this, user.getEmail(), user);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserManager.update_statistics(this, user, user.getScore(), user.getStreaks(),
                user.getNum_maze_games_played(), user.getLast_played_level(), user.getLoad_moles_stats());
        loaded = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        UserManager.update_statistics(this, user, user.getScore(), user.getStreaks(),
                user.getNum_maze_games_played(), user.getLast_played_level(), user.getLoad_moles_stats());
        loaded = true;

    }


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
        backgroundID = R.drawable.game_background;
    }

    public void load(Context context, String username, User user) {

        FileInputStream fis = null;

        try {
            fis = context.openFileInput(MainActivity.Stats_file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String text;

            while ((text = br.readLine()) != null) {
                int index_of_first_comma = text.indexOf(",");
                String other_username = text.substring(0, index_of_first_comma);
                if (username.equals(other_username)) {
                    int index_of_second_comma = text.indexOf(",", index_of_first_comma + 1);
                    int index_of_third_comma = text.indexOf(",", index_of_second_comma + 1);
                    int index_of_forth_comma = text.indexOf(",", index_of_third_comma + 1);
                    int index_of_fifth_comma = text.indexOf(",", index_of_forth_comma + 1);

                    String load_moles_stats = text.substring(index_of_fifth_comma + 2);
                    String[] stats = load_moles_stats.split(" ");

                    if (stats.length == 4) {
                        WamView wamView = new WamView(this);
                        this.score = Integer.getInteger(stats[3]);
                        this.numLives = Integer.getInteger(stats[0]);
                        this.numRows = Integer.getInteger(stats[1]);
                        this.numColumns = Integer.getInteger(stats[2]);
                        setContentView(wamView);
                        loaded = false;
                    }


                }

            }

            //System.out.println(sb);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
