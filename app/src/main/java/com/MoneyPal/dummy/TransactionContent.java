package com.MoneyPal.dummy;

import com.MoneyPal.Inventory.Storage;
import com.MoneyPal.Inventory.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class TransactionContent {

    /**
     * An array of sample (dummy) items.
     */
    public List<TransationItem> ITEMS = new ArrayList<TransationItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public Map<String, TransationItem> ITEM_MAP = new HashMap<String, TransationItem>();

    private static TransactionContent instance;

    private TransactionContent() {

        HashMap userMap = Storage.getInstance().getUserMap();
        List<Transaction> transactionList = Storage.getInstance().getTransactionList();

        for (Object userObj : userMap.keySet()) {
            TransationItem item = createTransactionItem(userObj.toString(), transactionList);
            if(item != null) {
                addItem(item);
            }
        }
    }

    public static TransactionContent getInstance(){
        //if(instance == null){
            instance = new TransactionContent();
        //}

        return instance;
    }

    private  void addItem(TransationItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private  TransationItem createTransactionItem(String friend, List<Transaction> transactionList) {


//        Random r = new Random();
//        int number = r.nextInt();
//
//        String sign = "+";
//        if (number % 2 == 0){
//            sign = "-";
//        }

        if (!Storage.getInstance().balanceMap.containsKey(friend)){
            return null;
        }

        return new TransationItem(String.valueOf(friend), Storage.getInstance().balanceMap.get(friend).toString(), makeDetails(friend, transactionList));
    }

    private  String makeDetails(String friend, List<Transaction> transactionList) {


        StringBuilder builder = new StringBuilder();
        builder.append("All transactions for: ").append(friend);


//        Random r = new Random();
//        for (int i = 0; i < r.nextInt(7); i++) {
//            builder.append("\nMore details information here.");
//        }

        for (Transaction trans : transactionList){
            if(trans.participants.contains(friend)){
                builder.append("\nTransaction: \n");
                builder.append(trans.toString());
            }
        }

        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public  class TransationItem {
        public final String id;
        public final String content;
        public final String details;

        public TransationItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
