package com.MoneyPal.dummy;

import com.MoneyPal.Common.IDGenerator;
import com.MoneyPal.Inventory.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DostarContent {

    /**
     * An array of sample (dummy) items.
     */
    public List<DostarItem> ITEMS = new ArrayList<DostarItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DostarItem> ITEM_MAP = new HashMap<String, DostarItem>();


    public DostarContent() {

        HashMap userMap = Storage.getInstance().getUsers();

        int i = 1;
        for (Object userObj : userMap.keySet()) {
            addItem(createDostarItem(i++, userObj.toString()));
        }
    }


    private void addItem(DostarItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private DostarItem createDostarItem(int position, String name) {
        return new DostarItem(String.valueOf(position), name, makeDetails(position));
    }

    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();

        return IDGenerator.generateUniqueID();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public class DostarItem {
        public final String id;
        public final String content;
        public final String details;

        public DostarItem(String id, String content, String details) {
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
