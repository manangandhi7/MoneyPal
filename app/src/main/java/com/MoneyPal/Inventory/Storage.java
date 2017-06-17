package com.MoneyPal.Inventory;

/**
 * Created by manan on 6/11/2017.
 */

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Storage {

    private static final String TAG = "Storage";
    public static final String[] POSSIBLE_FRIENDS = { "Salman", "Ranbir", "Deepika", "Katrina", "Dhoni", "Virat", "Mohit", "Arijit",  "Deepika", "Katrina" };

    private HashMap<String, User> users;
    private List<Transaction> transactions;
    private List<Balance> balance;
    private static Storage instance;

    private Storage(){
        users = new HashMap<String, User>();
        transactions = new ArrayList<>();
        balance = new ArrayList<Balance>();

        //add random users
        addRandomUsers(7);

        //create random transactions
        addRandomTransactions(5);

        //create random settlements from the transactions
        //show them in the adapter
        //clear adapter if needed

    }

    public static Storage getInstance(){
        if (instance == null){
            instance = new Storage();
            //TODO instance.loadData();
        }

        return instance;
    }

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

    private void loadData() {
        //TODO implement method here
    }

    private void addRandomUsers(int n) {
        for (int i = 0; i < 3; ++i){
            AddUser(POSSIBLE_FRIENDS[i], 1212121212, "");
        }

        Random r = new Random();
        for (int i = 0; i < n - 3; i++) {
            int random = r.nextInt(POSSIBLE_FRIENDS.length - 1);

            if(!validUser(POSSIBLE_FRIENDS[random], 567)){ //too expensive operation!
                i--;
                continue;
            } else {
                AddUser(POSSIBLE_FRIENDS[random], 1212121212, "");
            }
        }
    }

    private void addRandomTransactions(int n) {
//        for (int i = 0; i < 3; ++i) {
//            AddUser(POSSIBLE_FRIENDS[i], 1212121212, "");
//        }
//
//        Random r = new Random();
//        for (int i = 0; i < n - 3; i++) {
//            int random = r.nextInt(POSSIBLE_FRIENDS.length - 1);
//
//            if (!validUser(POSSIBLE_FRIENDS[i], 567)) { //too expensive operation!
//                i--;
//                continue;
//            } else {
//                AddUser(POSSIBLE_FRIENDS[i], 1212121212, "");
//            }
//        }
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
            if(user.email == email){
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

    public HashMap<String, User> getUsers(){
        return users;
    }

    public String[] getUsersArray(){
        Set<String> s = users.keySet();
        return s.toArray(new String[s.size()]);

    }

}

