package com.example.myapplication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.example.myapplication.GameConstants;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class UserManager implements Serializable {


    //ArrayList will store the user or users if 2 or more users want to login
    private User user;
    //the first index will represent if username is in the file and the second index will represent
    // the password


    UserManager(){

    }
    //UserManager(User User){
        //user = User;
    //}
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    ArrayList<Boolean> check_username_and_password(Context context, String username, String password){
        InputStream fis = null;
        ArrayList<Boolean> arr = new ArrayList<Boolean>();
        try {
            fis = context.openFileInput(MainActivity.FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            //StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                int index_of_comma = text.indexOf(",");
                String other_username = text.substring(0, index_of_comma);
                if (username.equals(other_username)){
                    arr.add(true);
                    int index_of_space = text.indexOf(" ");
                    String other_password = text.substring(index_of_space + 1);
                    if(other_password.equals(password)){
                        arr.add(true);
                        break;
                    }else{
                        arr.add(false);
                        break;
                    }
                }
            }

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
        arr.add(false);
        arr.add(false);
        return arr;
    }



    //assuming alphanumeric characters only
    void write_username_and_pass(Context context, FileOutputStream fos, String username, String password) {
        File file = new File(context.getFilesDir(),MainActivity.FILE_NAME);
        if(file.exists()){
            try {
                fos = context.openFileOutput(MainActivity.FILE_NAME, MODE_APPEND);
                try {
                    fos.write(username.getBytes());
                    fos.write(", ".getBytes());
                    fos.write(password.getBytes());
                    fos.write("\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else { //file does not exist make one and write to it
            try {
                fos = context.openFileOutput(MainActivity.FILE_NAME, MODE_PRIVATE);
                try {
                    fos.write(username.getBytes());
                    fos.write(", ".getBytes());
                    fos.write(password.getBytes());
                    fos.write("\n".getBytes());
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
    }

    void write_username_and_statistics(Context context, FileOutputStream fos, String username) {
        File file = new File(context.getFilesDir(),MainActivity.Stats_file);
        if(file.exists()){
            try {
                fos = context.openFileOutput(MainActivity.Stats_file, MODE_APPEND);
                try {
                    fos.write(username.getBytes());
                    String s = new String(new char[GameConstants.TotalNumOfStastics]).replace(
                            "\0", ", 0");
                    fos.write(s.getBytes());
                    fos.write("\n".getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else { //file does not exist make one and write to it
            try {
                fos = context.openFileOutput(MainActivity.Stats_file, MODE_PRIVATE);
                try {
                    fos.write(username.getBytes());
                    String s = new String(new char[GameConstants.TotalNumOfStastics]).replace(
                            "\0", ", 0");
                    fos.write(s.getBytes());
                    fos.write("\n".getBytes());
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
    }

    void set_statistics(Context context, User user){
        FileInputStream fis = null;

        try {
            fis = context.openFileInput(MainActivity.Stats_file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            //StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                int index_of_first_comma = text.indexOf(",");
                String other_username = text.substring(0, index_of_first_comma);
                if (user.getEmail().equals(other_username)) {
                    int index_of_second_comma = text.indexOf(",", index_of_first_comma + 1);
                    int index_of_third_comma = text.indexOf(",", index_of_second_comma + 1);
                    int index_of_forth_comma = text.indexOf(",", index_of_third_comma + 1);
                    int index_of_fifth_comma = text.indexOf(",", index_of_forth_comma + 1);
                    int LastPlayedLevel = Integer.parseInt(text.substring(index_of_first_comma + 2, index_of_second_comma));
                    int streaks = Integer.parseInt(text.substring(index_of_second_comma + 2, index_of_third_comma));
                    int NumMazeGame = Integer.parseInt(text.substring(index_of_third_comma + 2, index_of_forth_comma));
                    int MoleHit = Integer.parseInt(text.substring(index_of_forth_comma + 2, index_of_fifth_comma));
                    String load_moles_stats = text.substring(index_of_fifth_comma + 2);
                    /*System.out.println(score);
                    System.out.println("this is" + (streaks));
                    System.out.println("this is" + streak + "\n");
                    System.out.println("break");*/
                    Object[] TypeRacer = new Object[]{GameConstants.NameGame2, GameConstants.TypeRacerStreak, streaks};
                    Object[] WhackAMole = new Object[]{GameConstants.NameGame1, GameConstants.MoleStats, load_moles_stats, GameConstants.MoleHit, MoleHit};
                    Object[] Maze = new Object[]{GameConstants.NameGame3, GameConstants.NumMazeGamesPlayed, NumMazeGame};
                    ArrayList<Object[]> ArrayOfGameStats = new ArrayList<>();
                    ArrayOfGameStats.add(TypeRacer);
                    ArrayOfGameStats.add(Maze);
                    ArrayOfGameStats.add(WhackAMole);
                    user.SetStasticsInMap(ArrayOfGameStats);
                    user.setLast_played_level(LastPlayedLevel);
                    /*user.setScore(MoleHit);
                    user.setStreaks(streaks);
                    user.setNum_maze_games_played(NumMazeGame);
                    user.setLoad_moles_stats(load_moles_stats);*/
                }
            }

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

    /**
    Return an ArrayList of user information with the first index being the last played level,the
     second index being the savescore boolean value, then the maze statistics depending on how many
     there are, then typeracer then whackAMole Stats and if a new game is added its stats as well.

     */

    private ArrayList<Object> updateStaisticsHelper(User user){
        ArrayList<Object> arr = new ArrayList<>();
        //List<Integer> list = Arrays.asList(user.getOverallScore(), user.getLast_played_level(), user.getScore());
        return arr;
    }


    public  void update_statistics(Context context, User user) {
        FileInputStream fis = null;
        StringBuilder sb = new StringBuilder();

        try {
            fis = context.openFileInput(MainActivity.Stats_file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            //StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                int index_of_first_comma = text.indexOf(",");
                String other_username = text.substring(0, index_of_first_comma);
                if (user.getEmail().equals(other_username)){
                    String new_text = other_username  + ", " + user.getLast_played_level() + ", " + user.getStatistic(GameConstants.NameGame2, GameConstants.TypeRacerStreak)
                            + ", " + user.getStatistic(GameConstants.NameGame3, GameConstants.NumMazeGamesPlayed)
                            + ", "  +  user.getStatistic(GameConstants.NameGame1, GameConstants.MoleHit) + ", " + user.getStatistic(GameConstants.NameGame1, GameConstants.MoleStats) + "\n";
                    sb.append(new_text);
                } else {
                    sb.append(text).append("\n");
                }
            }

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

        // write the new string with the replaced line OVER the same file
        FileOutputStream fileOut = null;
        try {
            fileOut = context.openFileOutput(MainActivity.Stats_file, MODE_PRIVATE);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        try {
            if (fileOut != null)
                fileOut.write(sb.toString().getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            if (fileOut != null)
                fileOut.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }



}
