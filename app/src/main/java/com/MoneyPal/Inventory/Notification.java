package com.MoneyPal.Inventory;

import org.json.JSONObject;

/**
 * Created by manan on 6/15/2017.
 */

public class Notification {
    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String TO = "to";
    private static final String DATA = "data";
    private static final String NOTIFICATION = "notification";


    public String title;
    public String body;
    public String to;
    public String data;

    public JSONObject getMyJSON(){
        JSONObject json = new JSONObject();

        try {
            JSONObject notification = new JSONObject();
            notification.put(TITLE, TITLE);
            notification.put(BODY, BODY);

            json.put(NOTIFICATION, notification);
            json.put(TO, "dQHWS45qxwg:APA91bGl6qsizsGBvCUJ4l3cYk7rTcG51q4ka73qBPvJL0jkji11-Ozy7kVBgR8qFmcfS2Q5BSk31C1-phdbgParpxrKb9ArC9SjPAeXtXAcGH4ATBA4WAtu6aIWFaFFW4QrjCePfddN");
        } catch (Exception ex){

        }

        return json;
    }
}

/*

Content-Type:application/json
Authorization:key=AIzaSyZ-1u...0GBYzPu7Udno5aA
    { "notification": {
        "title": "Portugal vs. Denmark",
                "body": "5 to 1"
    },
        "to" : "bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1..."
    }
 */
