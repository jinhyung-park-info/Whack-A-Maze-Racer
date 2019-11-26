package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewScoreBoardActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private UserManager userManager;
    private String[] arr = new String[]{"Overall Score" ,"Moles Hit", "Num MazeGames Played",
            "Num MazeItems Collected", "TypeRacerStreak", "Mole All Time High"};
    private List<User> ArrayOfUsers;
    private  TextView[] ArrayOfTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score_board);
        Intent intent = getIntent();
        UserManager userManager1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (userManager1 != null){
            setUserManager(userManager1);
            ArrayOfUsers = userManager.getListOfAllUsers(getApplicationContext(), userManager.getUser());
            ArrayOfUsers.add(userManager.getUser());
        }

        TextView FirstUser = findViewById(R.id.FirstUser);
        TextView SecondUser = findViewById(R.id.SecondUser);
        TextView ThirdUser = findViewById(R.id.ThirdUser);
        TextView FourthUser = findViewById(R.id.FourthUser);
        ArrayOfTextView = new TextView[]{FirstUser, SecondUser, ThirdUser, FourthUser};


        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Statistics, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //setupScoreBoard(ArrayOfUsers, "Overall Score");
    }
    private void setUserManager(UserManager usermanager){
        userManager = usermanager;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_LONG).show();
        TextView Header = findViewById(R.id.HeaderScoreBoard);
        String header = "     Username" + "          " + text;
        Header.setText(header);
        setupScoreBoard(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setupScoreBoard(String SortingChooser){
        if(SortingChooser.equals("Overall Score")){
            Collections.sort(ArrayOfUsers);
            List<User> ArrayUser;
            if(GameConstants.NumPeopleOnScoreBoard >= ArrayOfUsers.size()){
                ArrayUser = ArrayOfUsers;
            }else{
                ArrayUser = ArrayOfUsers.subList(0, GameConstants.NumPeopleOnScoreBoard);
            }
            setupTextViews(ArrayUser, ArrayOfTextView, null, null);
        }else{
            String[] Attributes = getAttributesForSorting(SortingChooser);
            String GameName = Attributes[0];
            String Statistic = Attributes[1];
            List<User> ArrayUser;
            Collections.sort(ArrayOfUsers, new SortingUser(GameName, Statistic));
            if(GameConstants.NumPeopleOnScoreBoard >= ArrayOfUsers.size()){
                ArrayUser = ArrayOfUsers;
            }else{
                ArrayUser = ArrayOfUsers.subList(0, GameConstants.NumPeopleOnScoreBoard);
            }
            setupTextViews(ArrayUser, ArrayOfTextView, GameName, Statistic);

        }
    }

    private void setupTextViews(List<User> arrayUsers, TextView[] arrayOfTextView, String GameName,
                                String Statistic) {
        int i = 0;
        for(User user: arrayUsers){
            TextView textViewObject = arrayOfTextView[i];
            if (GameName == null) {
                String text = "   " + (i + 1) + ". " + user.getEmail() + "                " + user.getOverallScore();
                textViewObject.setText(text);
            }else{
                String text = "   " + (i + 1) + ". " + user.getEmail() + "                " +
                        user.getStatistic(GameName, Statistic);
                textViewObject.setText(text);
            }
            i ++;
        }
    }

    private String[] getAttributesForSorting(String sortingChooser) {
        switch (sortingChooser){
            case "Moles Hit":
                return new String[]{GameConstants.NameGame1, GameConstants.MoleHit};
            case "Num MazeGames Played":
                return new String[]{GameConstants.NameGame3, GameConstants.NumMazeGamesPlayed};
            case "Num MazeItems Collected":
                return new String[]{GameConstants.NameGame3, GameConstants.NumCollectiblesCollectedMaze};
            case "TypeRacerStreak":
                return new String[]{GameConstants.NameGame2, GameConstants.TypeRacerStreak};
            case "Mole All Time High":
                return new String[]{GameConstants.NameGame1, GameConstants.MoleAllTimeHigh};
        }
        return new String[]{};
    }

    public void GoBackToMainMenu(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }
}
