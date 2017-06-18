package com.MoneyPal.Inventory;

/**
 * Created by manan on 6/11/2017.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Storage {

    private static final String TAG = "Storage";
    public static final String[] POSSIBLE_FRIENDS = {"Salman", "Deepika", "Katrina", "Dhoni", "Virat", "Mohit", "Arijit", "Deepika", "Katrina", "Ranbir", "Mark", "Hansa", "Praful", "Mota bhai"};

    private HashMap<String, User> userMap;
    private List<Transaction> transactionList;
    private List<Balance> balance;
    private static Storage instance;

    private Storage() {
        userMap = new HashMap<String, User>();
        transactionList = new ArrayList<>();
        balance = new ArrayList<Balance>();

        //add random userMap
        addRandomUsers(7);

        //create random transactionList
        addRandomTransactions(10);

        //create random settlements from the transactionList
        //show them in the adapter
        //clear adapter if needed
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
            //TODO instance.loadData();
        }

        return instance;
    }

    public boolean AddUser(String email, int phoneNo, String password) {
        if (validUser(email, phoneNo)) {
            User user = new User();
            user.email = email;
            user.phoneNo = phoneNo;
            user.password = password;

            userMap.put(email, user);

            return true;
        }

        return false;
    }

    private void loadData() {
        //TODO implement method here
    }

    private void addRandomUsers(int n) {
        for (int i = 0; i < 3; ++i) {
            AddUser(POSSIBLE_FRIENDS[i], 1212121212, "");
        }

        Random r = new Random();
        for (int i = 0; i < n - 3; i++) {
            int random = r.nextInt(POSSIBLE_FRIENDS.length - 1);

            if (!validUser(POSSIBLE_FRIENDS[random], 567)) { //too expensive operation!
                i--;
                continue;
            } else {
                AddUser(POSSIBLE_FRIENDS[random], 1212121212, "");
            }
        }
    }

    private void addRandomTransactions(int n) {

        if (userMap.size() < 2) {
            return;
        }
        int numUsers = userMap.size();
        String[] users = userMap.keySet().toArray(new String[numUsers]);
        ArrayList<String> usersList = new ArrayList<>(Arrays.asList(users));

        Random r = new Random();
        for (int trans = 0; trans < n; trans++) {
            Transaction transaction = new Transaction();

            int random = r.nextInt(1) + 1;
            int totalAmount = 0;
            for (int i = 0; i < random; i++) {
                int random2 = r.nextInt(usersList.size());
                String participant = usersList.get(random2);
                usersList.remove(random2);

                int amount = r.nextInt(200) + 1;
                totalAmount += amount;
                transaction.addPayer(participant, amount);
            }

            random = r.nextInt(userMap.size() - 3) + 1;
            usersList = new ArrayList<>(Arrays.asList(users));

            for (int i = 0; i < random - 1; i++) {
                int random2 = r.nextInt(usersList.size());
                String participant = usersList.get(random2);
                usersList.remove(random2);

                int amount = r.nextInt(totalAmount - random + i + 1) + 1;
                totalAmount -= amount;
                transaction.addParticipant(participant, amount);
                //transaction.addParticipant(participant);
            }
            transaction.addParticipant(usersList.get(0), totalAmount);

            transaction.calculateEverything();
            transactionList.add(transaction);
        }
    }

    public boolean AddAccount(String userID, String accountNo, String IFSC, String uniqueID) {
        if (validAccount(accountNo, IFSC)) {
            Account account = new Account();
            account.accountNo = accountNo;
            account.IFSC = IFSC;
            account.uniqueID = uniqueID;

            User user = userMap.get(userID);
            user.accounts.add(account);

            return true;
        }

        return false;
    }

    public boolean validUser(String email, int phoneNo) {
        boolean found = false;
        for (User user : userMap.values()) {
            if (user.email == email) {
                found = true;
                break;
            }
        }

        return !found;
    }

    public boolean validAccount(String accountNo, String IFSC) {
        return true;
    }

    public boolean userExists(String id) {
        return userMap.containsKey(id);
    }

    public HashMap<String, User> getUserMap() {
        return userMap;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public String[] getUsersArray() {
        Set<String> s = userMap.keySet();
        return s.toArray(new String[s.size()]);
    }

}

