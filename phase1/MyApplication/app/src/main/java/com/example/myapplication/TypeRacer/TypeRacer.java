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
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.UserManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
    int backGroundColor;
    int textColor;
    int parameterDifficulty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_racer);

        Intent intent = getIntent();
        final User user_1 = (User) intent.getSerializableExtra(USER);
        if (user_1 != null) {
            setUser(user_1);
        }

        if (!user.getThereIsSaved()) {
            backGroundColor = intent.getExtras().getInt("backGroundColorKey");
            textColor = intent.getExtras().getInt("textColorKey");
            getTexts();
            countLife = intent.getExtras().getInt("lives");
            parameterDifficulty = intent.getIntExtra("difficulty", 5);

            setCustomization(this.backGroundColor, this.textColor, this.countLife, parameterDifficulty);
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
                    } else {
                        updateStatistics(false);
                        showNextQuestion();
                    }
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
            FileOutputStream fos = null;
            // File file = new File(getApplicationContext().getFilesDir(), user.getEmail() + "_typeracer.txt");
                try {
                    fos = getApplicationContext().openFileOutput(user.getEmail() + "_typeracer.txt", MODE_PRIVATE);
                    try {
                        fos.write(String.valueOf(this.countScore).getBytes());
                        fos.write("\n".getBytes());
                        fos.write(String.valueOf(this.countLife).getBytes());
                        fos.write("\n".getBytes());
                        fos.write(String.valueOf(this.countStreak).getBytes());
                        fos.write("\n".getBytes());
                        fos.write(String.valueOf(this.questionNumber).getBytes());
                        fos.write("\n".getBytes());
                        for (int i = 0; i < 5; i++) {
                            fos.write(this.questions.get(i).getBytes());
                            fos.write("\n".getBytes());
                        }
                        fos.write(this.answer.getText().toString().getBytes());
                        fos.write("\n".getBytes());

                        fos.write(String.valueOf(this.backGroundColor).getBytes());
                        fos.write("\n".getBytes());

                        fos.write(String.valueOf(this.textColor).getBytes());
                        fos.write("\n".getBytes());

                        fos.write(this.timerRunning.toString().getBytes());
                        fos.write("\n".getBytes());

                        fos.write(String.valueOf(this.parameterDifficulty).getBytes());
                        fos.write("\n".getBytes());

                        fos.write(this.countDown.getText().toString().getBytes());
                        fos.write("\n".getBytes());

                        user.setThereIsSaved(true);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

    }


    @Override
    public void onResume() {
        super.onResume();
        if (user.getThereIsSaved()) {
            FileInputStream fis = null;
            try {
                fis = getApplicationContext().openFileInput(MainActivity.Stats_file);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader rd = new BufferedReader(isr);

                this.countScore = Integer.parseInt(rd.readLine());
                this.countLife = Integer.parseInt(rd.readLine());
                this.countStreak = Integer.parseInt(rd.readLine());
                this.questionNumber = Integer.parseInt(rd.readLine());
                this.questions = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    questions.add(rd.readLine());
                }
                String answerString = rd.readLine();
                this.backGroundColor = Integer.parseInt(rd.readLine());
                this.textColor = Integer.parseInt(rd.readLine());

                if (rd.readLine() == "False") {
                    this.timerRunning = false;
                } else {
                    this.timerRunning = true;
                }

                this.parameterDifficulty = Integer.parseInt(rd.readLine());

                getTexts();
                setCustomization(backGroundColor, textColor, countLife, parameterDifficulty);

                score.setText("" + countScore);
                streak.setText("" + countStreak);
                life.setText("" + countLife);
                countDown.setText(rd.readLine());
                question.setText(questions.get(questionNumber));
                answer.setText("" + answerString);
                answer.setEnabled(true);
                manageTime();

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
                        } else {
                            updateStatistics(false);
                            showNextQuestion();
                        }
                    }
                });

                user.setThereIsSaved(false);

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

    public void setCustomization(int backGroundColor, int textColor, int lives, int diffi) {

        //User setUp

        if (!user.getThereIsSaved()) {
            // initialize 3 statistics
            countScore = 0;
            countStreak = user.getStreaks();
            score.setText("" + countScore);
            streak.setText("" + countStreak);
            life.setText("" + lives);
        }
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
        view.setBackgroundColor(backGroundColor);

        if (!user.getThereIsSaved()) {
            //set up the difficulty.

            //generate a list of questions
            for (int i = 0; i < 5; i++){
                createQuestion(diffi);
            }
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
            UserManager.update_statistics(this, user, user.getScore(), user.getStreaks(),
                    user.getNum_maze_games_played(), user.getLast_played_level(), user.getLoad_moles_stats());
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
                        if (response.length() == 1 || user.getThereIsSaved()) {
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
                UserManager.update_statistics(this, user, user.getScore(),
                        user.getStreaks(), user.getNum_maze_games_played(),
                        user.getLast_played_level(), user.getLoad_moles_stats());
                intent.putExtra(USER, user);
                startActivity(intent);
            }
        }
    }


}
