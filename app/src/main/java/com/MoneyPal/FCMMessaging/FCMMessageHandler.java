package com.MoneyPal.FCMMessaging;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.test.espresso.core.deps.dagger.internal.DoubleCheckLazy;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.MoneyPal.Activity.ShareMoneyActivity;
import com.MoneyPal.Common.Utility;
import com.MoneyPal.MessageNotification;
import com.MoneyPal.R;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

import static com.MoneyPal.Common.Utility.NOTIFICATION_AMOUNT;
import static com.MoneyPal.Common.Utility.NOTIFICATION_FROMID;
import static com.MoneyPal.Common.Utility.NOTIFICATION_FROMName;
import static com.MoneyPal.Common.Utility.NOTIFICATION_MSG_TYPE;
import static com.MoneyPal.Common.Utility.SETTLEMENT_REQ;
import static com.MoneyPal.Common.Utility.UNIQUE_ID;

/**
 * Created by manan on 6/15/2017.
 */


public class FCMMessageHandler extends FirebaseMessagingService {

    private static final String TAG = "FCMMessageHandler";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle SendToFCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());




        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
                handleNow(remoteMessage);
            //}

        } else
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), null);

        }

        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        //String uniqueID = preferences.getString(UNIQUE_ID, "");
        //sendNotification(uniqueID, uniqueID);

        // Also if you intend on generating your own notifications as a result of a received SendToFCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Schedule a job using FirebaseJobDispatcher.
     */
    private void scheduleJob() {

        Log.d(TAG, "message with payload");


        // [START dispatch_job]
//        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
//        Job myJob = dispatcher.newJobBuilder()
//                .setService(JobServiceFCM.class)
//                .setTag("my-job-tag")
//                .build();
//        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow(RemoteMessage remoteMessage) {
        Map<String, String> map = remoteMessage.getData();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String uniqueID = preferences.getString(UNIQUE_ID, "");


        if(map.containsKey(NOTIFICATION_FROMID) &&  uniqueID.compareToIgnoreCase(map.get(NOTIFICATION_FROMID).toString()) == 0) {
            return;
        }



        String msgtype = "";
        String title = null;
        String body = null;
        String settleButtonText = null;
        String replyButtonText = null;
        String fromUser = "";
        String  amount = "";
        if(map.containsKey(NOTIFICATION_FROMName)) {
            fromUser = map.get(NOTIFICATION_FROMName).toString();
        }

        if(map.containsKey(NOTIFICATION_AMOUNT)) {

               amount = map.get(NOTIFICATION_AMOUNT).toString();
                    }

        if(map.get(NOTIFICATION_MSG_TYPE) != null){
             msgtype = map.get(NOTIFICATION_MSG_TYPE).toString();
        }

        if(msgtype.compareToIgnoreCase(SETTLEMENT_REQ) == 0){
            title = "Settlement request : " + fromUser;
            body = "Settlement amount : " + amount;
            settleButtonText = "Settle";
            replyButtonText = "Details";
        } else if(msgtype.compareToIgnoreCase(SETTLEMENT_REQ) == 0){

        }


        //get the message type and details
        // create a title and body
        // set up action button to send notification back


        sendNotification(title, body, remoteMessage);
    }

    /**
     * Create and show a simple notification containing the received SendToFCM message.
     *
     * @param messageBody SendToFCM message body received.
     */
    private void sendNotification(String messageTitle, String messageBody, final RemoteMessage remoteMessage) {

        MessageNotification.notify(this, messageBody, messageTitle, remoteMessage);

        /*Intent intent = new Intent(this, ShareMoneyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code * /, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_menu_manage)
                .setContentTitle("SendToFCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification * /, notificationBuilder.build());
        */
    }


}
