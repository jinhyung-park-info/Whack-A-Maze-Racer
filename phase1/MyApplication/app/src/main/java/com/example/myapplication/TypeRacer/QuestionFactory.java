package com.example.myapplication.TypeRacer;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuestionFactory {

    int difficulty;

    public QuestionFactory(int d) {
        this.difficulty = d;
    }

    public ArrayList<Question> createQuestionSet() {
        ArrayList<Question> questionSet = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            double x = Math.random();
            if (x > 0.4) {
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
