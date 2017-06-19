package com.MoneyPal.Inventory;

/**
 * Created by manan on 6/11/2017.
 */

import java.util.ArrayList;
import java.util.List;

public class User {
    public String email;
    public String name;
    public int phoneNo;
    public String password;
    public String uniqueID;
    public List<Account> accounts;

    Object profilePic;

    public User(){
        email = "";
        name = "";
        phoneNo = 0;
        password = "";
        uniqueID = "";
        accounts = new ArrayList<>();
    }
}

