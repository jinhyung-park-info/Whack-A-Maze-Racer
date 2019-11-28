package com.example.myapplication.TypeRacer;

import java.util.concurrent.ThreadLocalRandom;

public class Question {

    protected String questionContent;
    protected int point;

    public void setQuestionContent(int d) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < d; i++) {
            sb.append((char) ThreadLocalRandom.current().nextInt(32, 126+1));
        }
        String q = sb.toString();
        this.questionContent = q;
    }

    public String getQuestionContent() {
        return this.questionContent;
    }

    public int getPoint() {
        return this.point;
    }
}
