package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class InGamePurchaseActivity extends AppCompatActivity {

    UserManager userManager;
    User user;

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
        user.setCurrency(user.getCurrency() + 50);

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
