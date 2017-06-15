package com.MoneyPal.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.MoneyPal.Common.Utility;
import com.MoneyPal.Inventory.Notification;
import com.MoneyPal.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import static com.MoneyPal.Common.Utility.SendToFCM;
import static com.MoneyPal.Common.Utility.SendToSBI;

public class SendMoneyActivity extends AppCompatActivity {

    private static final String TAG = "SendMoneyActivity";

    //public final String url = "http://52.172.213.166:8080/sbi/Account_List/api/EnqBancsAccountsList/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, Utility.FRIENDS);
        //TODO drop down menu will be better, slect karo etle list popup thay, wither select existing one or create new one
        Spinner textView = (Spinner)
                findViewById(R.id.spinner);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)
                findViewById(R.id.receiver);
        textView.setAdapter(adapter);
        autoCompleteTextView.setAdapter(adapter);

        final Button button = (Button) findViewById(R.id.transfer_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("hell", "how r");
                try {

                    JSONObject json = new JSONObject();
                    json.put("AccountNumber", "30001512992");
                    String s = new com.MoneyPal.ServerConnection().execute(Utility.Account_List, json.toString(), SendToSBI).get();
                    //String s = new com.MoneyPal.ServerConnection().execute(Utility.Account_List, "{\"AccountNumber\": \"30001512992\" }").get();
                    Log.d("tag", s);

                    Notification notification = new Notification();


                    String s2 = new com.MoneyPal.ServerConnection().execute(Utility.FCM_URL, notification.getMyJSON().toString(), SendToFCM).get();

                    Log.d("tag", s2);
                } catch (Exception e) {
                    Log.e("error", e.toString());
                }
            }
        });
    }

    private class TransferNEFT{
        public String REMTACCTNO;
        public String REMNAME;
        public String MOBNUMBER;
        public String BENFACCTNO;
        public String BENFNAME;
        public String RECBNKIFSC;
        public String SNDIFSC;
        public String TXNAMT;
    }

    private class TransferRTGS{
        public String REMTACCTNO;
        public String REMNAME;
        public String MOBNUMBER;
        public String BENFACCTNO;
        public String BENFNAME;
        public String RECBNKIFSC;
        public String SNDIFSC;
        public String TXNAMT;
    }
    private boolean sendMoney(String from, String to, double amount) {
        Gson gson = new Gson();
        return true;
    }

    private boolean requestMoney(String from, String to, double amount) {
        return true;
    }
}