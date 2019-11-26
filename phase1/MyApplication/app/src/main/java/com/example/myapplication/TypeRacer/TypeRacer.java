package com.example.myapplication.TypeRacer;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


public class TypeRacer extends AppCompatActivity {

    Map<String, TextView> textViewMap;
    ArrayList<String> questions = new ArrayList<>();

    private int questionNumber = 0;
    EditText answer;

    // 3 statistics
    private int countScore, countStreak, countLife;

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

            getTextViews();
            backGroundColor = intent.getExtras().getInt("backGroundColorKey");
            textColor = intent.getExtras().getInt("textColorKey");
            countLife = intent.getExtras().getInt("lives");
            parameterDifficulty = intent.getIntExtra("difficulty", 5);


            // User setUp
            // initialize 3 statistics
            countScore = 0;
            countStreak = (int) user.getStatistic(GameConstants.NameGame2, GameConstants.TypeRacerStreak);
            textViewMap.get("score").setText("" + countScore);
            textViewMap.get("streak").setText("" + countStreak);






            setCustomization(this.backGroundColor, this.textColor, this.countLife, parameterDifficulty);
            showNextQuestion();
            prepareForScreenUpdate();

        }
    }

    private void prepareForScreenUpdate() {

        Button doneBtn = (Button) findViewById(R.id.doneButton);
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

                fos.write(textViewMap.get("countDown").getText().toString().getBytes());
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

        if (user.getThereIsSaved()) {
            FileInputStream fis = null;
            try {
                fis = getApplicationContext().openFileInput(user.getEmail() + "_typeracer.txt");
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader rd = new BufferedReader(isr);

                if (!Boolean.valueOf(rd.readLine())) {
                    this.timerRunning = false;
                } else {
                    this.timerRunning = true;
                }

                int time = Integer.parseInt(rd.readLine());

                startTime = System.currentTimeMillis();
                timerRunning = true;
                countDownTimer =
                        new CountDownTimer(time * 1000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                textViewMap.get("countDown").setText(Long.toString(millisUntilFinished / 1000));
                            }

                            @Override
                            public void onFinish() {
                                updateStatistics(false);
                                textViewMap.get("countDown").setText("0");
                                showNextQuestion();
                                timerRunning = false;
                            }
                        }.start();

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


    private void getTextViews() {
        textViewMap = new HashMap<>();

        TextView scoreTitle = findViewById(R.id.scoreTitleTextView);
        textViewMap.put("scoreTitle", scoreTitle);

        TextView streakTitle = findViewById(R.id.streakTitleTextView);
        textViewMap.put("streakTitle", streakTitle);

        TextView lifeTitle = findViewById(R.id.lifeTitleTextView);
        textViewMap.put("lifeTitle", lifeTitle);

        TextView countDownTitle = findViewById(R.id.countDownTitleTextView);
        textViewMap.put("countDownTitle", countDownTitle);

        TextView sec = findViewById(R.id.secTextView);
        textViewMap.put("sec", sec);

        TextView score = findViewById(R.id.scoreTextView);
        textViewMap.put("score", score);

        TextView streak = findViewById(R.id.streakTextView);
        textViewMap.put("streak", streak);

        TextView life = findViewById(R.id.lifeTextView);
        textViewMap.put("life", life);

        TextView question = findViewById(R.id.questionTextView);
        textViewMap.put("question", question);

        TextView countDown = findViewById(R.id.countDownTextView);
        textViewMap.put("countDown", countDown);

        answer = findViewById(R.id.answerEditText);
    }

    private void setCustomization(int backGroundColor, int textColor, int lives, int diffi) {

        // 1. Set up the background color as <backGroundColor>.
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(backGroundColor);

        // 2. Set up all the color of the text with <textColor>
        setAllTextColor(textViewMap, textColor);
        answer.setTextColor(textColor);

        // 3. Set the number of lives
        textViewMap.get("life").setText("" + lives);

        // 4. Create questions according to the difficulty
        for (int i = 0; i < 5; i++){
            createQuestion(diffi);
        }

    }

    private void setAllTextColor(Map<String, TextView> map, int textColor) {
        for (Map.Entry<String, TextView> entry : map.entrySet()) {
            entry.getValue().setTextColor(textColor);
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
            textViewMap.get("countDown").setText("30");
            textViewMap.get("question").setText(questions.get(questionNumber));
            answer.setText("");
            answer.setEnabled(true);
            manageTime();
            questionNumber++;
        } else {
            user.setLastPlayedLevel(0);
            //user.setStreaks(countStreak);
            user.setStatistic(GameConstants.NameGame2, GameConstants.TypeRacerStreak, countStreak);
            userManager.updateStatistics(this, user);
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
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

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
                                            textViewMap.get("countDown").setText(Long.toString(millisUntilFinished / 1000));
                                        }

                                        @Override
                                        public void onFinish() {
                                            updateStatistics(false);
                                            textViewMap.get("countDown").setText("0");
                                            showNextQuestion();
                                            timerRunning = false;
                                        }
                                    }.start();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });
    }

    public boolean userIsCorrect() {
        String response = answer.getText().toString();
        return response.equals(textViewMap.get("question").getText().toString());
    }

    // method called to update the statistic.
    public void updateStatistics(boolean isCorrect){
        if (isCorrect) {
            countScore++;
            countStreak++;
            textViewMap.get("score").setText("" + countScore);
            textViewMap.get("streak").setText("" + countStreak);
        } else {
            countStreak = 0;
            countLife--;

            if (countLife > 0) {
                textViewMap.get("streak").setText("" + countStreak);
                textViewMap.get("life").setText("" + countLife);
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
