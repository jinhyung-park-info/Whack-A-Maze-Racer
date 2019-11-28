package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.UserInfo.IUser;
import com.example.myapplication.UserInfo.UserManager;

import java.util.Arrays;
import java.util.List;

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

        EditText codeText =  findViewById(R.id.code);
        String code = codeText.getText().toString();

        List<String> list = Arrays.asList(GameConstants.validGiftCodes);
        if (list.contains(code)) {
            user.setCurrency(user.getCurrency() + 50);
            userManager.updateStatistics(this, user);
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
