package com.example.myapplication.TypeRacer;

import com.example.myapplication.GameConstants;

import java.lang.reflect.Array;
import java.util.ArrayList;

class QuestionFactory {

    private int difficulty;

    QuestionFactory(int d) {
        this.difficulty = d;
    }

    ArrayList<Question> createQuestionSet() {
        ArrayList<Question> questionSet = new ArrayList<>();
        for (int i = 0; i < GameConstants.numOfQuestions; i++) {
            double x = Math.random();
            if (x > GameConstants.goldenQuestionFrequency) {
                RegularQuestion rq = new RegularQuestion();
                rq.setQuestionContent(this.difficulty);
                questionSet.add(rq);
            } else {
                GoldenQuestion gq = new GoldenQuestion();
                gq.setQuestionContent(this.difficulty);
                questionSet.add(gq);
            }
        }
        return questionSet;
    }

}
