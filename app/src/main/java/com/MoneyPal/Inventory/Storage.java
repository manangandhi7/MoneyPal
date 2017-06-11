package com.MoneyPal.Inventory;

/**
 * Created by manan on 6/11/2017.
 */

import java.util.HashMap;

public class Storage {

    HashMap<String, User> users;

    public boolean AddUser(String email, int phoneNo, String password) {
        if (validUser(email,  phoneNo)){
            User user = new User();
            user.email = email;
            user.phoneNo = phoneNo;
            user.password = password;

            users.put(email, user);

            return true;
        }

        return false;
    }

    public boolean AddAccount(String userID, String accountNo, String IFSC, String uniqueID) {
        if (validAccount(accountNo, IFSC)){
            Account account = new Account();
            account.accountNo = accountNo;
            account.IFSC = IFSC;
            account.uniqueID = uniqueID;

            User user = users.get(userID);
            user.accounts.add(account);

            return true;
        }

        return false;
    }

    public boolean validUser(String email, int phoneNo){
        boolean found = false;
        for (User user : users.values()){
            if(user.email == email || user.phoneNo == phoneNo){
                found = true;
                break;
            }
        }

        return !found;
    }

    public boolean validAccount(String accountNo, String IFSC){
        return true;
    }

    public boolean userExists(String id){
        return users.containsKey(id);
    }
}

