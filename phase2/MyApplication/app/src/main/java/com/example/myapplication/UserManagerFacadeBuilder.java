package com.example.myapplication;

import com.example.myapplication.UserInfo.HandleAllAccounts;
import com.example.myapplication.UserInfo.ReadAndUpdate;
import com.example.myapplication.UserInfo.UserManagerFacade;
import com.example.myapplication.UserInfo.WriteAndCheck;

public class UserManagerFacadeBuilder {
    private ReadAndUpdate rau;
    private WriteAndCheck wac;
    private HandleAllAccounts hac;
    private UserManagerFacade umf;

    public void buildRAU(){
        this.rau = new ReadAndUpdate();
    }

    public void buildWAC(){
        this.wac = new WriteAndCheck();
    }

    public void buildHAC(){
        this.hac = new HandleAllAccounts();
    }

    public void buildUMF(){
        this.umf = new UserManagerFacade(this.rau, this.wac, this.hac);
    }

    public UserManagerFacade getUmf(){
        return this.umf;
    }
}
