package com.example.myapplication.TypeRacer;

import java.util.concurrent.ThreadLocalRandom;

public class QuestionCreator {
    // from ! to ~ in Ascii
    private static final int from = 33, to = 126;

    public static String createQ(int d){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < d; i++){
            sb.append((char) ThreadLocalRandom.current().nextInt(from, to+1));
        }
        return sb.toString();
    }
}
