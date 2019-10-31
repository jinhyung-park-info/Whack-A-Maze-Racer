package com.example.myapplication.TypeRacer;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.myapplication.GameOver;
import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.UserManager;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static com.example.myapplication.MainActivity.USER;


public class TypeRacer extends AppCompatActivity {

    TextView question, score, streak, life, scoreTitle, streakTitle, lifeTitle, countDownTitle, sec;
    ArrayList<String> questions = new ArrayList<>();
    private int questionNumber = 0;
    EditText answer;

    // 3 statistics
    private int countScore, countStreak, countLife;

    // time related variables
    private TextView countDown;
    long startTime, endTime;
    private static final long COUNTDOWN_IN_MILLS = 30000;
    private long timeLeftInMillis;
    private CountDownTimer countDownTimer;
    Boolean timerRunning = false;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_racer);
        getTexts();
        setCustomization();
        showNextQuestion();

        Button doneBtn = (Button)findViewById(R.id.doneButton);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //goes to next question if response is correct
                if (userIsCorrect()) {
                    endTime = System.currentTimeMillis();
                    if (countDownTimer != null) countDownTimer.cancel();
                    timerRunning = false;
                    answer.setEnabled(false);
                    answer.clearFocus();
                    updateStatistics(true);
                    showNextQuestion();
                }
                else {
                    updateStatistics(false);
                    showNextQuestion();
                }
            }
        });
    }

    private void setUser(User new_user) {
        user = new_user;
    }

    public void getTexts() {
        question = findViewById(R.id.questionTextView);
        answer = findViewById(R.id.editText2);
        countDown = findViewById(R.id.countDownTextView);
        score = findViewById(R.id.scoreTextView);
        streak = findViewById(R.id.streakTextView);
        life = findViewById(R.id.lifeTextView);
        scoreTitle = findViewById(R.id.scoreTitleTextView);
        streakTitle = findViewById(R.id.scoreTitleTextView);
        lifeTitle = findViewById(R.id.lifeTitleTextView);
        countDownTitle = findViewById(R.id.countDownTitleTextView);
        sec = findViewById(R.id.secTextView);

    }

    public void setCustomization() {

        //User setUp
        final Intent intent = getIntent();
        final User user_1 = (User) intent.getSerializableExtra(USER);
        if (user_1 != null) {
            setUser(user_1);
        }

        // initialize 3 statistics
        countScore = 0;
        countStreak = user.getStreaks();
        countLife = 3;
        score.setText("" + countScore);
        streak.setText("" + countStreak);
        life.setText("" + countLife);

        //set up the color of the words.
        int trBC = intent.getIntExtra("trBC", Color.WHITE);
        int textColor = intent.getIntExtra("textColor", Color.BLACK);

        question.setTextColor(textColor);
        answer.setTextColor(textColor);
        countDown.setTextColor(textColor);
        score.setTextColor(textColor);
        streak.setTextColor(textColor);
        life.setTextColor(textColor);
        scoreTitle.setTextColor(textColor);
        streakTitle.setTextColor(textColor);
        lifeTitle.setTextColor(textColor);
        countDownTitle.setTextColor(textColor);
        sec.setTextColor(textColor);

        //set up the background color.
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(trBC);

        //set up the difficulty.
        int difficulty = intent.getIntExtra("difficulty", 5);

        //generate a list of questions
        for (int i = 0; i < 5; i++){
            createQuestion(difficulty);
        }

    }

    public void createQuestion(int d){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < d; i++){
            sb.append((char) ThreadLocalRandom.current().nextInt(32, 126+1));
        }
        String q = sb.toString();
        questions.add(q);
    }


    // shows next question, ends if all questions completed
    private void showNextQuestion() {
        if (questionNumber < questions.size()) {
            countDown.setText("30");
            question.setText(questions.get(questionNumber));
            answer.setText("");
            answer.setEnabled(true);
            manageTime();
            questionNumber++;
        } else {
            user.setStreaks(countStreak);
            UserManager.update_statistics(this, user, user.getScore(), user.getStreaks(), user.getNum_maze_games_played(), user.getLast_played_level());
            Intent goToEndGame = new Intent(getApplicationContext(), TypeRacerEnd.class);
            goToEndGame.putExtra(USER, user);
            goToEndGame.putExtra("finalScore", "" + countScore);
            startActivity(goToEndGame);
        }
    }


    private void manageTime() {
        answer.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        String response = answer.getText().toString();
                        //start counting
                        if (response.length() == 1) {
                            startTime = System.currentTimeMillis();
                            if (timerRunning) return;
                            timerRunning = true;
                            countDownTimer =
                                    new CountDownTimer(COUNTDOWN_IN_MILLS, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            countDown.setText(Long.toString(millisUntilFinished / 1000));
                                        }

                                        @Override
                                        public void onFinish() {
                                            updateStatistics(false);
                                            countDown.setText("0");
                                            showNextQuestion();
                                            timerRunning = false;
                                        }
                                    }.start();
                        }
                    }


                    @Override
                    public void afterTextChanged(Editable s) {

                    }

                });
    }

    public boolean userIsCorrect() {
        String response = answer.getText().toString();
        return response.equals(question.getText().toString());
    }

    // method called to update the statistic.
    public void updateStatistics(boolean isCorrect){
        if (isCorrect) {
            countScore++;
            countStreak++;
            score.setText("" + countScore);
            streak.setText("" + countStreak);
        } else {
            countStreak = 0;
            countLife--;

            if (countLife > 0) {
                streak.setText("" + countStreak);
                life.setText("" + countLife);
            } else {
                Intent intent = new Intent(getApplicationContext(), GameOver.class);
                user.setStreaks(countStreak);
                UserManager.update_statistics(this, user, user.getScore(), user.getStreaks(), user.getNum_maze_games_played(), user.getLast_played_level());
                intent.putExtra(USER, user);
                startActivity(intent);
            }
        }
    }


}
