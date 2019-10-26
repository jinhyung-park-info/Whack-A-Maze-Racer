package com.example.myapplication.Maze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.myapplication.Maze.MazeGame;
import com.example.myapplication.R;

public class MazeCustomizationActivity extends AppCompatActivity {

    /**
     * background colour of the screen. by default this is green
     */
    private int bgColour = Color.GREEN;

    /**
     * difficulty of the maze. by default this is normal
     */
    private String difficulty = "Normal";

    /**
     * the player's colour. by default this is black
     */
    private int playerColour = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze_customization);
    }

    /**
     * Updates the customization according to the given view.
     * Code borrowed from https://developer.android.com/guide/topics/ui/controls/radiobutton
     *
     * @param view the radio button
     */
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButtonGreen:
                if (checked)
                    bgColour = Color.GREEN;
                break;
            case R.id.radioButtonBlue:
                if (checked)
                    bgColour = Color.BLUE;
                break;
            case R.id.radioButtonRed:
                if (checked)
                    bgColour = Color.RED;
                break;
            case R.id.radioButtonYellow:
                if (checked)
                    bgColour = Color.YELLOW;
                break;
            case R.id.radioButtonWhite:
                if (checked)
                    bgColour = Color.WHITE;
                break;
            case R.id.radioButtonHard:
                if (checked)
                    difficulty = "Hard";
                break;
            case R.id.radioButtonNormal:
                if (checked)
                    difficulty = "Normal";
                break;
            case R.id.radioButtonEasy:
                if (checked)
                    difficulty = "Easy";
                break;
            case R.id.radioButtonBlack:
                if (checked)
                    playerColour = Color.BLACK;
                break;
            case R.id.radioButtonMagenta:
                if (checked)
                    playerColour = Color.MAGENTA;
                break;
            case R.id.radioButtonCyan:
                if (checked)
                    playerColour = Color.CYAN;
        }

    }

    public void startMazeGame(View view) {
        Intent intent = new Intent(this, MazeGame.class);
        intent.putExtra("bgColour", bgColour);
        intent.putExtra("difficulty", difficulty);
        intent.putExtra("playerColour", playerColour);
        startActivity(intent);
    }

}
