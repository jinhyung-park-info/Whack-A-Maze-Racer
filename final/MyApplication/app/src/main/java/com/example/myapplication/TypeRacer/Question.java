package com.example.myapplication.TypeRacer;

import java.util.concurrent.ThreadLocalRandom;

class Question {

    private String questionContent;
    int point;

    void setQuestionContent(int d) {
        StringBuilder sb = new StringBuilder();

        // Generate Random String using ASCII code Values from 32 to 126

        for (int i = 0; i < d; i++) {
            sb.append((char) ThreadLocalRandom.current().nextInt(32, 126 + 1));
        }
        this.questionContent = sb.toString();
    }

    String getQuestionContent() {
        return this.questionContent;
    }

    int getPoint() {
        return this.point;
    }
}
