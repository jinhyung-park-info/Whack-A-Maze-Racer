package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.UserInfo.IUser;
import com.example.myapplication.UserInfo.UserManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InGamePurchaseActivity extends AppCompatActivity {

    UserManager userManager;
    IUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_game_purchase);

        Intent intent = getIntent();
        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null) {
            setUserManager(user_1);
            user = userManager.getUser();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    private void setUserManager(UserManager newManager){
        userManager = newManager;
    }

    public void buyCurrency(View view){

        EditText ccText =  findViewById(R.id.cc);
        String ccNum = ccText.getText().toString();

        EditText cvvText =  findViewById(R.id.cvv);
        String cvvNum = cvvText.getText().toString();

        EditText expText =  findViewById(R.id.exp);
        String expNum = expText.getText().toString();


        Pattern ccPattern = Pattern.compile("^[0-9]{16}$");
        Matcher ccMatcher = ccPattern.matcher(ccNum);

        Pattern cvvPattern = Pattern.compile("^[0-9]{3}$");
        Matcher cvvMatcher = cvvPattern.matcher(cvvNum);

        Pattern expPattern = Pattern.compile("^(0[1-9]|1[0-2])/[0-9]{2}$");
        Matcher expMatcher = expPattern.matcher(expNum);

        if (ccMatcher.matches() && cvvMatcher.matches() && expMatcher.matches()) {
            user.setCurrency(user.getCurrency() + 50);
            userManager.setOrUpdateStatistics(this, user, GameConstants.update);
            ccText.setError(null);
        }else{
            ccText.setError("Invalid Credit Card/Expiration Date/Cvv !");
        }
        TextView currencyText =  findViewById(R.id.numGems);
        String currency = "Gems Remaining: " + user.getCurrency();
        currencyText.setText(currency);
    }

    public void close(View view){

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }
}
