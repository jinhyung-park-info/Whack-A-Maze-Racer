package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewScoreBoardActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private UserManager userManager;
    private String[] arr = new String[]{"Overall Score" ,"Moles Hit", "Num MazeGames Played",
            "Num MazeItems Collected","TypeRacerStreak"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score_board);
        Intent intent = getIntent();
        UserManager userManager1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (userManager1 != null){
            setUserManager(userManager1);
        }

        //TextView textView = findViewById(R.id.textViewChoose);
        //textView.setText("Choose a Statistic");

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Statistics, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
    private void setUserManager(UserManager usermanage){
        userManager = usermanage;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_LONG).show();
        TextView Header = findViewById(R.id.HeaderScoreBoard);
        String header = "     Username" +"          " + text;
        Header.setText(header);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void GoBackToMainMenu(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }
}
