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
import java.util.ArrayList;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

public class UserManager {
    //the first index will represent if username is in the file and the second index will represent
    // the password
    static ArrayList<Boolean> check_username_and_password(Context context, String username, String password){
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
    static void write_username_and_pass(Context context, FileOutputStream fos, String username, String password) {
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

    static void write_username_and_statistics(Context context, FileOutputStream fos, String username) {
        File file = new File(context.getFilesDir(),MainActivity.Stats_file);
        if(file.exists()){
            try {
                fos = context.openFileOutput(MainActivity.Stats_file, MODE_APPEND);
                try {
                    fos.write(username.getBytes());
                    fos.write(", 0, 0, 0, 0, 0".getBytes());
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
                    fos.write(", 0, 0, 0, 0, 0".getBytes());
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

    static void set_statistics(Context context, String username, User user){
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
                if (username.equals(other_username)) {
                    int index_of_second_comma = text.indexOf(",", index_of_first_comma + 1);
                    int index_of_third_comma = text.indexOf(",", index_of_second_comma + 1);
                    int index_of_forth_comma = text.indexOf(",", index_of_third_comma + 1);
                    int index_of_fifth_comma = text.indexOf(",", index_of_forth_comma + 1);
                    int score = Integer.parseInt(text.substring(index_of_first_comma + 2, index_of_second_comma));
                    int streaks = Integer.parseInt(text.substring(index_of_second_comma + 2, index_of_third_comma));
                    int streak = Integer.parseInt(text.substring(index_of_third_comma + 2, index_of_forth_comma));
                    int last_played_level = Integer.parseInt(text.substring(index_of_forth_comma + 2, index_of_fifth_comma));
                    String load_moles_stats = text.substring(index_of_fifth_comma + 1);
                    /*System.out.println(score);
                    System.out.println("this is" + (streaks));
                    System.out.println("this is" + streak + "\n");
                    System.out.println("break");*/
                    user.setScore(score);
                    user.setStreaks(streaks);
                    user.setNum_maze_games_played(streak);
                    user.setLast_played_level(last_played_level);
                    user.setLoad_moles_stats(load_moles_stats);
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


    public static void update_statistics(Context context, User user) {
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
                    String new_text = other_username + ", " + user.getScore() + ", " + user.getStreaks() + ", " + user.getNum_maze_games_played() + ", " + user.getLast_played_level() + ", " + user.getLoad_moles_stats() + "\n";
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
