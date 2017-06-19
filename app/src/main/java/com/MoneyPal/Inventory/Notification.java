package com.MoneyPal.Inventory;

import android.content.SharedPreferences;

import org.json.JSONObject;

import static com.MoneyPal.Common.Utility.GLOBAL_SEND;

/**
 * Created by manan on 6/15/2017.
 */

public class Notification {
    private static final String TITLE = "title";
    private static final String BODY = "body";
    private static final String TO = "to";
    private static final String FROM = "from";
    private static final String FROMUser = "from_user";
    private static final String TOUSER = "to_user";
    private static final String DATA = "data";
    private static final String NOTIFICATION = "notification";
    private static final String MESSAGE = "message";

    public String title;
    public String body;
    public String to;
    public String data;
    public String fromUniqueID;
    public String toUniqueID;

    public Notification(){
        title = "A new message from MoneyPal";
        body = "";
        to = "";
        data = "";
        fromUniqueID = "";
        toUniqueID = "";
    }

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

    public JSONObject getMyJSON2(){
        JSONObject json = new JSONObject();

        try {
            JSONObject notification = new JSONObject();
            notification.put(TITLE, title);
            notification.put(BODY, body);
            //notification.put (MESSAGE, "Hey hey good looking!");
            json.put(NOTIFICATION, notification);
            //json.put(DATA, data);
            json.put(TO, GLOBAL_SEND);
        } catch (Exception ex){

        }

        return json;
    }

    public JSONObject getJSONData(){
        JSONObject json = new JSONObject();

        try {
            JSONObject notification = new JSONObject();
            notification.put(TITLE, TITLE);
            notification.put(BODY, BODY);
            //notification.put (MESSAGE, "Hey hey good looking!");
            //json.put(NOTIFICATION, notification);

            JSONObject data = new JSONObject();
            data.put(MESSAGE, "");
            data.put(TOUSER, toUniqueID);
            data.put(FROMUser, fromUniqueID);
            data.put("not_message_type", "mytype"); //TODO : handle message accordingly on the receiving end
            json.put(DATA, data);
            json.put(TO, GLOBAL_SEND);
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
