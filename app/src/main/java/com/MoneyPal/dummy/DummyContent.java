package com.MoneyPal.dummy;

import com.MoneyPal.Common.Utility;
import com.MoneyPal.Inventory.Storage;

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
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static DummyContent instance;

    private DummyContent() {

        HashMap userMap = Storage.getInstance().getUsers();

        int i = 1;

        for (Object userObj : userMap.keySet()) {
            addItem(createDummyItem(userObj.toString()));
        }
    }

    public static DummyContent getInstance(){
        if(instance == null){
            instance = new DummyContent();
        }

        return instance;
    }

    private  void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private  DummyItem createDummyItem(String friend) {

        Random r = new Random();
        int number = r.nextInt();

        String sign = "+";
        if (number % 2 == 0){
            sign = "-";
        }

        return new DummyItem(String.valueOf(friend), sign + new Random().nextInt(500), makeDetails(friend));
    }

    private  String makeDetails(String friend) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(friend);
        Random r = new Random();
        for (int i = 0; i < r.nextInt(7); i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public  class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
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
