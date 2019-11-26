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


import com.example.myapplication.GameConstants;
import com.example.myapplication.GameOver;
import com.example.myapplication.R;
import com.example.myapplication.User;
import com.example.myapplication.UserManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class TypeRacer extends AppCompatActivity {

    TextView question, score, streak, life, scoreTitle, streakTitle, lifeTitle, countDownTitle, sec;
    ArrayList<Question> questions = new ArrayList<>();
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
    private UserManager userManager;
    private User user;
    int backGroundColor;
    int textColor;
    int parameterDifficulty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_racer);
        Intent intent = getIntent();
        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null) {
            setUserManager(user_1);
            user = user_1.getUser();

            if (!user.getThereIsSaved()) {
                backGroundColor = intent.getExtras().getInt("backGroundColorKey");
                textColor = intent.getExtras().getInt("textColorKey");
                getTexts();
                countLife = intent.getExtras().getInt("lives");
                parameterDifficulty = intent.getIntExtra("difficulty", 5);

                setCustomization(this.backGroundColor, this.textColor, this.countLife, parameterDifficulty);
                showNextQuestion();
                checkIfCorrect();
            }
        }
    }
    
    private void checkIfCorrect() {

        Button doneBtn = findViewById(R.id.doneButton);
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

    private void setUserManager(UserManager newManager){
        userManager = newManager;
    }

    @Override
    public void onPause() {
        super.onPause();
            FileOutputStream fos = null;
            // File file = new File(getApplicationContext().getFilesDir(), user.getEmail() + "_typeracer.txt");
                try {
                    fos = getApplicationContext().openFileOutput(user.getEmail() + "_typeracer.txt", MODE_PRIVATE);
                    try {

                        fos.write(this.timerRunning.toString().getBytes());
                        fos.write("\n".getBytes());

                        fos.write(this.countDown.getText().toString().getBytes());
                        fos.write("\n".getBytes());

                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }

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

        Intent intent = getIntent();
        /*final User user_1 = (User) intent.getSerializableExtra(USER);
        if (user_1 != null) {
            setUser(user_1);
        }*/

        if (user.getThereIsSaved()) {
            FileInputStream fis = null;
            try {
                fis = getApplicationContext().openFileInput(user.getEmail() + "_typeracer.txt");
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader rd = new BufferedReader(isr);

                this.timerRunning = Boolean.valueOf(rd.readLine());

                int time = Integer.parseInt(rd.readLine());

                startTime = System.currentTimeMillis();
                timerRunning = true;
                countDownTimer =
                        new CountDownTimer(time * 1000, 1000) {
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


                manageTime();

                Button doneBtn = findViewById(R.id.doneButton);
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
                File newFile = new File(getApplicationContext().getFilesDir(), user.getEmail() + "_typeracer.txt");
                newFile.delete();

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

    private void getTexts() {
        question = findViewById(R.id.questionTextView);
        answer = findViewById(R.id.editText2);
        countDown = findViewById(R.id.countDownTextView);
        score = findViewById(R.id.scoreTextView);
        streak = findViewById(R.id.streakTextView);
        life = findViewById(R.id.lifeTextView);
        scoreTitle = findViewById(R.id.scoreTitleTextView);
        streakTitle = findViewById(R.id.streakTitleTextView);
        lifeTitle = findViewById(R.id.lifeTitleTextView);
        countDownTitle = findViewById(R.id.countDownTitleTextView);
        sec = findViewById(R.id.secTextView);

    }

    public void setCustomization(int backGroundColor, int textColor, int lives, int diffi) {

        //User setUp

        if (!user.getThereIsSaved()) {
            // initialize 3 statistics
            countScore = 0;
            countStreak = (int) user.getStatistic(GameConstants.NameGame2, GameConstants.TypeRacerStreak);
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
            QuestionFactory questionFactory = new QuestionFactory();
            for (int i = 0; i < 5; i++){
                double x = Math.random();
                if(x > 0.4){
                    Question q = questionFactory.getQuestion("RegularQuestion");
                    questions.add(q);
                }
                else{
                    Question q = questionFactory.getQuestion("GoldenQuestion");
                    questions.add(q);
                }
            }
        }

    }

    //public void createQuestion(String questionType){
       // if(questionType=="RegularQuestion"){
            //Question q = new RegularQuestion();
          //  questions.add(q);
        //}
      //  if(questionType == "GoldenQuestion"){
          //  Question q = new GoldenQuestion();
          //  questions.add(q);
      //  }
    //}


    // shows next question, ends if all questions completed
    private void showNextQuestion() {
        if (questionNumber < questions.size()) {
            countDown.setText("30");
            question.setText(questions.get(questionNumber).createQuestion(parameterDifficulty));
            answer.setText("");
            answer.setEnabled(true);
            manageTime();
            questionNumber++;
        } else {
            user.setLastPlayedLevel(0);
            //user.setStreaks(countStreak);
            user.setStatistic(GameConstants.NameGame2, GameConstants.TypeRacerStreak, countStreak);
            userManager.updateStatistics(this, user);
            textColor = Color.BLACK;
            Intent goToEndGame = new Intent(getApplicationContext(), TypeRacerEnd.class);
            goToEndGame.putExtra(GameConstants.USERMANAGER, userManager);
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
                            startTime = COUNTDOWN_IN_MILLS;
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
            int p = questions.get(questionNumber).returnPoint();
            countScore = countScore + p;
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
                //user.setStreaks(countStreak);
                user.setStatistic(GameConstants.NameGame2, GameConstants.TypeRacerStreak, countStreak);
                userManager.updateStatistics(this, user);
                intent.putExtra(GameConstants.USERMANAGER, userManager);
                startActivity(intent);
            }
        }
    }


}
