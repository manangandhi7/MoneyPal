package com.MoneyPal.Inventory;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.MoneyPal.Common.Utility;
import com.MoneyPal.ServerConnection;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.MoneyPal.Common.Utility.*;

/**
 * Created by manan on 6/15/2017.
 */

public class Notification {


    public String title;
    public String body;
    public String to;
    public String data;
    public String fromUniqueID;
    public String toUniqueID;
    public String amount;
    public String MessageType;
    public String userInQue;

    public Notification() {
        title = "A new message from MoneyPal";
        body = "";
        to = "";
        data = "";
        fromUniqueID = "";
        toUniqueID = "";
    }

    public JSONObject getMyJSON() {
        JSONObject json = new JSONObject();

        try {
            JSONObject notification = new JSONObject();
            notification.put(NOTIFICATION_TITLE, NOTIFICATION_TITLE);
            notification.put(NOTIFICATION_BODY, NOTIFICATION_BODY);

            json.put(NOTIFICATION_NOTIFICATION, notification);
            json.put(NOTIFICATION_TO, "dQHWS45qxwg:APA91bGl6qsizsGBvCUJ4l3cYk7rTcG51q4ka73qBPvJL0jkji11-Ozy7kVBgR8qFmcfS2Q5BSk31C1-phdbgParpxrKb9ArC9SjPAeXtXAcGH4ATBA4WAtu6aIWFaFFW4QrjCePfddN");
        } catch (Exception ex) {

        }

        return json;
    }

    public JSONObject getMyJSON2() {
        JSONObject json = new JSONObject();

        try {
            JSONObject notification = new JSONObject();
            notification.put(NOTIFICATION_TITLE, title);
            notification.put(NOTIFICATION_BODY, body);
            //notification.put (MESSAGE, "Hey hey good looking!");
            json.put(NOTIFICATION_NOTIFICATION, notification);
            //json.put(DATA, data);
            json.put(NOTIFICATION_TO, GLOBAL_SEND);
        } catch (Exception ex) {

        }

        return json;
    }

    public JSONObject getJSONData(Context context) {
        JSONObject json = new JSONObject();

        try {
            JSONObject notification = new JSONObject();
            notification.put(NOTIFICATION_TITLE, "");
            notification.put(NOTIFICATION_BODY, "");
            //notification.put (MESSAGE, "Hey hey good looking!");
            //json.put(NOTIFICATION, notification);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String uniqueID = preferences.getString(UNIQUE_ID, "");
            String userName = preferences.getString(USERNAME, "");

            JSONObject data = new JSONObject();
            data.put(NOTIFICATION_MESSAGE, "");
            data.put(NOTIFICATION_TOUSER, toUniqueID);
            data.put(NOTIFICATION_FROMName, userName);
            data.put(NOTIFICATION_FROMID,uniqueID);
            data.put("not_message_type", "mytype"); //TODO : handle message accordingly on the receiving end
            json.put(NOTIFICATION_DATA, data);
            json.put(NOTIFICATION_TO, GLOBAL_SEND);


        } catch (Exception ex){

        }

        return json;
    }

    public JSONObject getSettleRequestJSON(Context context) {
        //TODO get unique ID, amount, request type

        JSONObject json = new JSONObject();

        try {
            JSONObject notification = new JSONObject();
            notification.put(NOTIFICATION_TITLE, NOTIFICATION_TITLE);
            notification.put(NOTIFICATION_BODY, NOTIFICATION_BODY);


            //Notification.NotificationFCMMessage msg = new Notification.NotificationFCMMessage();
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String uniqueID = preferences.getString(UNIQUE_ID, "");
            String userName = preferences.getString(USERNAME, "");

            JSONObject data = new JSONObject();
            data.put(NOTIFICATION_MSG_TYPE, MessageType);
            data.put(SETTLEMENT_USER_IN_QUE, userInQue);
            data.put(NOTIFICATION_FROMID,uniqueID);
            data.put(NOTIFICATION_ToID, toUniqueID);
            data.put(NOTIFICATION_FROMName, userName);
            data.put(NOTIFICATION_AMOUNT, amount);
            json.put(NOTIFICATION_DATA, data);
            json.put(NOTIFICATION_TO, GLOBAL_SEND);
        } catch (Exception ex) {

        }

        return json;
    }

    public boolean doStuff(Context context) {
        try {

            Notification notification = new Notification();

            String s2 = new ServerConnection().execute(Utility.FCM_URL, notification.getSettleRequestJSON(context).toString(), SendToFCM).get();

            Log.d("FCM", s2);
            Toast.makeText(context, "Money Transferred", Toast.LENGTH_LONG).show();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static class NotificationFCMMessage{
        public double amount;
        public int refereceNumber;
        public String data;
        public String fromUniqueID;
        public String toUniqueID;
        public String fromName;

    };
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
