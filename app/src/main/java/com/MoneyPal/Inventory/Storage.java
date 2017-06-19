package com.MoneyPal.Inventory;

/**
 * Created by manan on 6/11/2017.
 */

import android.util.Log;

import com.MoneyPal.Common.IDGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.MoneyPal.Common.Utility.YOU;

public class Storage {

    private static final String TAG = "Storage";
    public static final String[] POSSIBLE_FRIENDS = {"Salman", "Deepika", "Katrina", "Dhoni", "Virat", "Mohit", "Arijit", "Deepika", "Katrina", "Ranbir", "Mark", "Hansa", "Praful", "Mota bhai"};

    private HashMap<String, User> userMap;
    private List<Transaction> transactionList;
    public HashMap<String, Double> balanceMap;
    private static Storage instance;

    private Storage() {
        userMap = new HashMap<String, User>();
        transactionList = new ArrayList<>();
        balanceMap = new HashMap<>();

        //add random userMap
        addRandomUsers(7);

        try {
            //create random transactionList
            //addRandomTransactions(10);
        }catch (Exception ex){
            Log.e(TAG, "fuck up : " + ex.toString());
        }

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

    public boolean AddUser(String name, String uniqueID) {
        if (validUser(name, uniqueID)) {
            User user = new User();
            user.email = "";
            user.phoneNo = 0;
            user.password = "";
            user.uniqueID= uniqueID;

            userMap.put(name, user);

            return true;
        }

        return false;
    }

    private void loadData() {
        //TODO implement method here
    }

    private void addRandomUsers(int n) {
        for (int i = 0; i < 3; ++i) {
            AddUser(POSSIBLE_FRIENDS[i], IDGenerator.generateUniqueID());
        }

        Random r = new Random();
        for (int i = 0; i < n - 3; i++) {
            int random = r.nextInt(POSSIBLE_FRIENDS.length - 1);

            if (!AddUser(POSSIBLE_FRIENDS[random], IDGenerator.generateUniqueID())) {
                i--;
                continue;
            }
        }
    }

    private void addRandomTransactions(int n) {
        AddUser(YOU, IDGenerator.generateUniqueID());

        if (userMap.size() < 2) {
            return;
        }
        int numUsers = userMap.size();
        String[] users = userMap.keySet().toArray(new String[numUsers]);
        //ArrayList<String> usersList = new ArrayList<>(Arrays.asList(users));

        Random r = new Random();
        for (int trans = 0; trans < n; trans++) {
            Transaction transaction = new Transaction();
            ArrayList<String> usersList = new ArrayList<>(Arrays.asList(users));


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

            addTransaction(transaction);
        }
        deleteUser(YOU);
    }

    private void deleteUser(String user){
        if(userMap.containsKey(user)) {
            userMap.remove(YOU);
        }
    }

    public boolean addTransaction(Transaction transaction) {
        HashMap<String, HashMap<String, Double>> settlement = new HashMap<>();

        try {
            if (transaction.calculateEverything(settlement)) {
                transactionList.add(transaction);

                for (String payer : settlement.keySet()) {
                    for (String participant : settlement.get(payer).keySet()) {

                        if (payer.compareTo(YOU) == 0) {
                            addBalance(participant, (settlement.get(payer).get(participant) * (-1.0)));
                        } else if (participant.compareTo(YOU) == 0) {
                            addBalance(payer, settlement.get(payer).get(participant));
                        }
                    }
                }

                return true;
            } else {
                return false;
            }
        }catch (Exception ex){
            Log.e(TAG, "fuck up : " + ex.toString());
            return false;
        }
    }

    private void addBalance(String friend, double amount) {
        if (!balanceMap.containsKey(friend)) {
            balanceMap.put(friend, 0.0);
        }
        balanceMap.put(friend, balanceMap.get(friend) + amount);
        if (balanceMap.get(friend) == 0.0) {
            balanceMap.remove(friend);
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

    public boolean validUser(String name, String UniqueID) {
        boolean found = false;
        for (User user : userMap.values()) {
            if (user.name.compareToIgnoreCase(name) == 0 || user.uniqueID.compareToIgnoreCase(UniqueID) == 0) {
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

