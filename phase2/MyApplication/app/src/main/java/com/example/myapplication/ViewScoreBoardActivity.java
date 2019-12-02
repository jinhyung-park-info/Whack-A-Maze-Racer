package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.UserInfo.IUser;
import com.example.myapplication.UserInfo.IUserManager;
import com.example.myapplication.UserInfo.SortingUser;
import com.example.myapplication.UserInfo.UserManagerFacade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewScoreBoardActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private UserManagerFacadeBuilder umfb;
    private IUserManager userManager;
    private List<IUser> ArrayOfUsers;
    private TextView[] ArrayOfTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score_board);
        Intent intent = getIntent();
        IUserManager userManager1 = (IUserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        umfb = new UserManagerFacadeBuilder();
        buildUserManagerFacade();
        if (userManager1 != null) {
            UserManagerFacade newUserManagerFacade = umfb.getUmf();
            setUserManager(userManager1);
            ArrayOfUsers = newUserManagerFacade.getListOfAllUsers(getApplicationContext(), userManager.getUser());
            ArrayOfUsers.add(userManager.getUser());
        }


        TextView FirstUserName = findViewById(R.id.FirstUserName);
        TextView SecondUserName = findViewById(R.id.SecondUserName);
        TextView ThirdUserName = findViewById(R.id.ThirdUserName);
        TextView FourthUserName = findViewById(R.id.FourthUserName);
        TextView FifthUserName = findViewById(R.id.FifthUserName);
        TextView SixthUserName = findViewById(R.id.SixthUserName);
        TextView SeventhUserName = findViewById(R.id.SeventhUserName);
        TextView EighthUserName = findViewById(R.id.EighthUserName);
        TextView NinthUserName = findViewById(R.id.NinthUserName);
        TextView TenthUserName = findViewById(R.id.TenthUserName);
        TextView EleventhUserName = findViewById(R.id.EleventhUserName);
        TextView TwelfthUserName = findViewById(R.id.TwelfthUserName);
        TextView ThirteenUserName = findViewById(R.id.ThirteenUserName);
        TextView FourteenUserName = findViewById(R.id.FourteenUserName);
        TextView FifteenUserName = findViewById(R.id.FifteenUserName);
        TextView FirstUserStat = findViewById(R.id.FirstUserStat);
        TextView SecondUserStat = findViewById(R.id.SecondUserStat);
        TextView ThirdUserStat = findViewById(R.id.ThirdUserStat);
        TextView FourthUserStat = findViewById(R.id.FourthUserStat);
        TextView FifthUserStat = findViewById(R.id.FifthUserStat);
        TextView SixthUserStat = findViewById(R.id.SixthUserStat);
        TextView SeventhUserStat = findViewById(R.id.SeventhUserStat);
        TextView EighthUserStat = findViewById(R.id.EighthUserStat);
        TextView NinthUserStat = findViewById(R.id.NinthUserStat);
        TextView TenthUserStat = findViewById(R.id.TenthUserStat);
        TextView EleventhUserStat = findViewById(R.id.EleventhUserStat);
        TextView TwelfthUserStat = findViewById(R.id.TwelfthUserStat);
        TextView ThirteenUserStat = findViewById(R.id.ThirteenUserStat);
        TextView FourteenUserStat = findViewById(R.id.FourteenUserStat);
        TextView FifteenUserStat = findViewById(R.id.FifteenUserStat);
        ArrayOfTextView = new TextView[]{FirstUserName, FirstUserStat, SecondUserName,
                SecondUserStat, ThirdUserName, ThirdUserStat, FourthUserName, FourthUserStat,
                FifthUserName, FifthUserStat, SixthUserName, SixthUserStat, SeventhUserName,
                SeventhUserStat, EighthUserName, EighthUserStat, NinthUserName, NinthUserStat,
                TenthUserName, TenthUserStat, EleventhUserName, EleventhUserStat, TwelfthUserName,
                TwelfthUserStat, ThirteenUserName, ThirteenUserStat, FourteenUserName, FourteenUserStat,
                FifteenUserName, FifteenUserStat};

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Statistics, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void setUserManager(IUserManager usermanager) {
        userManager = usermanager;
    }

    private void buildUserManagerFacade() {
        umfb.buildWAC();
        umfb.buildRAU();
        umfb.buildHAC();
        umfb.buildUMF();
        userManager = umfb.getUmf();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        TextView Statistic = findViewById(R.id.StatisticName);
        //pass a clone into the setupScoreBoard function so as to not modify the original ArrayOfUsers
        // and pass the same list of all users every time a new option is selected
        List<IUser> cloneOfUsers = new ArrayList<>(ArrayOfUsers);
        Statistic.setText(text);
        setupScoreBoard(text, cloneOfUsers);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setupScoreBoard(String SortingChooser, List<IUser> clone) {
        if (SortingChooser.equals("Overall Score")) {
            Collections.sort(clone);
            List<IUser> ArrayUser;
            if (GameConstants.NumPeopleOnScoreBoard >= clone.size()) {
                ArrayUser = clone;
            } else {
                ArrayUser = clone.subList(0, GameConstants.NumPeopleOnScoreBoard);
            }
            setupTextViews(ArrayUser, ArrayOfTextView, null, null);
        } else {
            String[] Attributes = getAttributesForSorting(SortingChooser);
            String GameName = Attributes[0];
            String Statistic = Attributes[1];
            List<IUser> ArrayUser;
            Collections.sort(clone, new SortingUser(GameName, Statistic));
            if (GameConstants.NumPeopleOnScoreBoard >= clone.size()) {
                ArrayUser = clone;
            } else {
                ArrayUser = clone.subList(0, GameConstants.NumPeopleOnScoreBoard);
            }
            setupTextViews(ArrayUser, ArrayOfTextView, GameName, Statistic);

        }
    }

    private void setupTextViews(List<IUser> arrayUsers, TextView[] arrayOfTextView, String GameName,
                                String Statistic) {
        int i = 0;
        int numbering = 1;
        for (IUser user : arrayUsers) {
            if (GameName == null) {
                String text = numbering + "." + user.getEmail();
                arrayOfTextView[i].setText(text);
                arrayOfTextView[i + 1].setText(String.valueOf(user.getOverallScore()));
            } else {
                String text = numbering + "." + user.getEmail();
                arrayOfTextView[i].setText(text);
                arrayOfTextView[i + 1].setText(String.valueOf(user.getStatistic(GameName, Statistic)));
            }
            numbering += 1;
            i += 2;
        }

        setupRemainingTextViews(i, numbering);
    }

    private void setupRemainingTextViews(int i, int numbering) {
        int remainingNumbers = numbering;
        for (int n = i; n < ArrayOfTextView.length; n += 2) {
            String text = remainingNumbers + ".";
            ArrayOfTextView[n].setText(text);
            remainingNumbers += 1;
        }
    }

    private String[] getAttributesForSorting(String sortingChooser) {
        switch (sortingChooser) {
            case "Moles Hit":
                return new String[]{GameConstants.WHACK_A_MOLE, GameConstants.MoleHit};
            case "Num MazeGames Played":
                return new String[]{GameConstants.MAZE, GameConstants.NumMazeGamesPlayed};
            case "Num MazeItems Collected":
                return new String[]{GameConstants.MAZE, GameConstants.NumCollectiblesCollectedMaze};
            case "TypeRacerStreak":
                return new String[]{GameConstants.TYPE_RACER, GameConstants.TypeRacerStreak};
            case "Mole All Time High":
                return new String[]{GameConstants.WHACK_A_MOLE, GameConstants.MoleAllTimeHigh};
        }
        return new String[]{};
    }

    public void GoBackToMainMenu(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }
}

