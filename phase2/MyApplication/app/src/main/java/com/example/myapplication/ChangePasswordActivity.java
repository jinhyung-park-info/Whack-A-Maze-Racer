package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.UserInfo.IUser;
import com.example.myapplication.UserInfo.IUserManager;
import com.example.myapplication.UserInfo.UserManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePasswordActivity extends AppCompatActivity {
    IUser user;
    IUserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Intent intent = getIntent();
        UserManager user_1 = (UserManager) intent.getSerializableExtra(GameConstants.USERMANAGER);
        if (user_1 != null) {
            setUserManager(user_1);
            user = userManager.getUser();
        }
        TextView gemDisplay = findViewById(R.id.gemDisplay);
        String text = "Gem: " + userManager.getUser().getCurrency();
        gemDisplay.setText(text);
    }

    private void setUserManager(UserManager newManager){
        userManager = newManager;
    }

    public void done(View view){
        EditText newp =  findViewById(R.id.newp);
        String newPass = newp.getText().toString();

        EditText confirm =  findViewById(R.id.confirm);
        String confirmPass = confirm.getText().toString();

        Pattern newPattern = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher newMatcher = newPattern.matcher(confirmPass);

        if(newPass.equals(confirmPass) && newMatcher.matches()){
            if(user.getCurrency() >= 100) {
                user.setPassword(newPass);
                user.setCurrency(user.getCurrency() - 100);
                back(view);
                userManager.setOrUpdateStatistics(this, user, GameConstants.update);
                UserManager newUserManager = new UserManager();
                newUserManager.getOrChangePassword(this, user, newPass, GameConstants.changePassword);
            }else{
                Toast.makeText(getApplicationContext(), "Insufficient Gems"
                        , Toast.LENGTH_LONG).show();
            }
        }else newp.setError("Invalid Password");
    }

    public void back(View view){
        Intent intent = new Intent(this, AccountInformationActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    public void buyGem(View view){
        Intent intent = new Intent(this, InGamePurchaseActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, AccountInformationActivity.class);
        intent.putExtra(GameConstants.USERMANAGER, userManager);
        startActivity(intent);
    }

}
