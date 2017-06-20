package com.MoneyPal;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.MoneyPal.Activity.MainActivity;
import com.MoneyPal.Common.Utility;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static com.MoneyPal.Common.Utility.*;


/**
 * Helper class for showing and canceling message
 * notifications.
 * <p>
 * This class makes heavy use of the {@link NotificationCompat.Builder} helper
 * class to create notifications in a backward-compatible way.
 */
public class MessageNotification {
    /**
     * The unique identifier for this type of notification.
     */
    private static final String NOTIFICATION_TAG = "Message";

    /**
     * Shows the notification, or updates a previously shown notification of
     * this type, with the given parameters.
     * <p>
     * TODO: Customize this method's arguments to present relevant content in
     * the notification.
     * <p>
     * TODO: Customize the contents of this method to tweak the behavior and
     * presentation of message notifications. Make
     * sure to follow the
     * <a href="https://developer.android.com/design/patterns/notifications.html">
     * Notification design guidelines</a> when doing so.
     *
     * @see #cancel(Context)
     */
    public static void notify(final Context context,
                              String textBody, String textTitle, final RemoteMessage remoteMessage) {
        final Resources res = context.getResources();

        // This image is used as the notification's large icon (thumbnail).
        // TODO: Remove this if your notification has no relevant thumbnail.
        final Bitmap picture = BitmapFactory.decodeResource(res, R.drawable.example_picture2);

        if (textBody == null){
            textBody = "Money Pal update";
        }

        if(textTitle == null){
            textTitle = "Update";
        }

        final String ticker = textBody;
//        final String title = res.getString(
    //            R.string.message_notification_title_template, textTitle);
  //      final String text = res.getString(
      //          R.string.message_notification_placeholder_text_template, textBody);

        String title = textTitle;
        String text = textBody;
        String settleButtonText = "Ok";
        String replyButtonText = "Details";
        Intent okIntent =new Intent(context, MainActivity.class);

        if (remoteMessage != null){
            Map<String, String> map = remoteMessage.getData();

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String uniqueID = preferences.getString(UNIQUE_ID, "");

            String msgtype = "";
            //String title2 = null;
            String body = null;

            String fromUser = "";
            String  amount = "";
            String  userinQue = "";
            if(map.containsKey(NOTIFICATION_FROMName)) {
                fromUser = map.get(NOTIFICATION_FROMName).toString();
            }

            if(map.containsKey(NOTIFICATION_AMOUNT)) {

                amount = map.get(NOTIFICATION_AMOUNT).toString();
            }

            if(map.get(NOTIFICATION_MSG_TYPE) != null){
                msgtype = map.get(NOTIFICATION_MSG_TYPE).toString();
            }

            if(map.get(SETTLEMENT_USER_IN_QUE) != null){
                userinQue =map.get(SETTLEMENT_USER_IN_QUE).toString();
            }

            okIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            if(msgtype.compareToIgnoreCase(SETTLEMENT_REQ) == 0){
                title = "Settlement request : " + fromUser;
                text = "Settlement amount : " + amount;
                settleButtonText = "Settle";
                replyButtonText = "Details";

                if (map.get(NOTIFICATION_FROMID) != null){
                    okIntent.putExtra(Utility.NOTIFICATION_ToID, map.get(NOTIFICATION_FROMID));
                    okIntent.putExtra(Utility.NOTIFICATION_MSG_TYPE, SETTLEMENT_REQ);
                    okIntent.putExtra(Utility.SETTLEMENT_USER_IN_QUE, userinQue);
                    okIntent.putExtra(Utility.NOTIFICATION_AMOUNT, amount);


                }


            } else if(msgtype.compareToIgnoreCase(SETTLEMENT_RES_OK) == 0){
                title = "Settlement response Ok" + userinQue;
                text = "Settlement amount : " + amount;
                settleButtonText = "Ok";
                replyButtonText = "Details";

                if (map.get(NOTIFICATION_FROMID) != null){
                    okIntent.putExtra(Utility.NOTIFICATION_MSG_TYPE, SETTLEMENT_RES_OK);
                    okIntent.putExtra(Utility.SETTLEMENT_USER_IN_QUE, userinQue);

                }
            }


            //get the message type and details
            // create a title and body
            // set up action button to send notification back
        }

         NotificationCompat.Builder builder = new NotificationCompat.Builder(context)

                // Set appropriate defaults for the notification light, sound,
                // and vibration.
                .setDefaults(Notification.DEFAULT_ALL)

                // Set required fields, including the small icon, the
                // notification title, and text.
                .setSmallIcon(R.drawable.ic_stat_message)
                .setContentTitle(title)
                .setContentText(text)

                // All fields below this line are optional.

                // Use a default priority (recognized on devices running Android
                // 4.1 or later)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                // Provide a large icon, shown with the notification in the
                // notification drawer on devices running Android 3.0 or later.
                .setLargeIcon(picture)

                // Set ticker text (preview) information for this notification.
                .setTicker(ticker)

                // Show a number. This is useful when stacking notifications of
                // a single type.
                //.setNumber(number)

                // If this notification relates to a past or upcoming event, you
                // should set the relevant time information using the setWhen
                // method below. If this call is omitted, the notification's
                // timestamp will by set to the time at which it was shown.
                // TODO: Call setWhen if this notification relates to a past or
                // upcoming event. The sole argument to this method should be
                // the notification timestamp in milliseconds.
                //.setWhen(...)

                // Set the pending intent to be initiated when the user touches
                // the notification.
                .setContentIntent(
                        PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0))

                // Show expanded text content on devices running Android 4.1 or
                // later.
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(text)
                        .setBigContentTitle(title)
                        .setSummaryText("Developed by Manan Gandhi"))

                // Example additional actions for this notification. These will
                // only show on devices running Android 4.1 or later, so you
                // should ensure that the activity in this notification's
                // content intent provides access to the same actions in
                // another way.
                .addAction(
                        R.drawable.checkmark_medium_ff,
                        settleButtonText,
                        PendingIntent.getActivity(context, 0, okIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(
                        R.drawable.ic_action_stat_reply,
                        replyButtonText,
                        PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0))

                // Automatically dismiss the notification when it is touched.
                .setAutoCancel(true);

        notify(context, builder.build());


    }

    private void addButton(){

    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG, 0, notification);
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification);
        }
    }


    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static void cancel(final Context context) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0);
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode());
        }
    }
}
